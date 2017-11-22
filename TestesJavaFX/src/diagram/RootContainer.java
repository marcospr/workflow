package diagram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RootContainer extends BorderPane {
	private CoordinatesMouseXY coordinatesMouse;
	// private Processo processoHelper;
	private Processo startProcess;
	private Processo endProcess;
	private Set<Node> selectNodes = new HashSet<>();

	public RootContainer() {
		buildToolbar();
		coordinatesMouse = new CoordinatesMouseXY();
	}

	private void buildToolbar() {
		Image imageProcess = new Image(getClass().getResourceAsStream("rounded.png"));
		Image imageStart = new Image(getClass().getResourceAsStream("ellipse.png"));
		Image imageEnd = new Image(getClass().getResourceAsStream("ellipse.png"));
		Image imageArrow = new Image(getClass().getResourceAsStream("arrow.png"));

		Button btnProcess = new Button("Process", new ImageView(imageProcess));
		btnProcess.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnStart = new Button("Start", new ImageView(imageStart));
		btnStart.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnEnd = new Button("End", new ImageView(imageEnd));
		btnEnd.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnArrow = new Button("Conector", new ImageView(imageArrow));
		btnArrow.setContentDisplay(ContentDisplay.BOTTOM);

		ToolBar toolBar1 = new ToolBar();
		toolBar1.getItems().addAll(new Separator(), btnProcess, btnStart, btnEnd, btnArrow,
				new Separator());

		setTop(toolBar1);

		// handlers Pane

		setOnMousePressed(mouseEvent -> {
			if (mouseEvent.isShiftDown()) {
				setCursor(Cursor.HAND);
				startProcess = getProcessoFromCoordinatesXY(mouseEvent);
				endProcess = null;
			}
		});

		setOnMouseDragged(mouseEvent -> {
			if (mouseEvent.isShiftDown()) {
				// ????
			}
		});

		setOnMouseReleased(mouseEvent -> {
			endProcess = getProcessoFromCoordinatesXY(mouseEvent);
			if (mouseEvent.isShiftDown()) {

				if (startProcess != null && endProcess != null) {
					// Cria Arrow
					if (!connectionExists(startProcess, endProcess)) {
						Connection conn = new Connection(startProcess, endProcess);
						getChildren().add(0, conn);
						getChildren().add(conn.getArrow());
					}
				}
			} else {
				if (endProcess != null) {
					Connection connectionCurrent = null;
					Set<Connection> connections = endProcess.getConnections();
					for (Connection connection : connections) {
						if (connection.getEndRectangle().equals(endProcess)) {
							connectionCurrent = connection;
							getChildren().remove(connectionCurrent.getArrow());
							connectionCurrent.setArrow(new Arrow(startProcess, endProcess));
							getChildren().add(connectionCurrent.getArrow());
							break;
						}
					}
				}
			}
			setCursor(Cursor.DEFAULT);
		});

		setOnMouseClicked(mouseEvent -> {
			if (!mouseEvent.isControlDown()) {
				// Limpo qualquer seleção anterior
				ObservableList<Node> nodes = getChildren();
				for (Node node : nodes) {
					if (node instanceof Selectable) {
						Selectable s = (Selectable) node;
						if (s.isSelect()) {
							selectNodes = new HashSet<>();
							node.setEffect(null);
							s.setSelect(Boolean.FALSE);
						}
					}
				}
			}

			// Seleciono o Node atual das coordenadas XY
			Node node = getNodeFromCoordinatesXY(mouseEvent);
			if (node != null) {
				if (node instanceof Selectable) {
					node.setEffect(new DropShadow(20, Color.DEEPSKYBLUE));
					((Selectable) node).setSelect(Boolean.TRUE);
					selectNodes.add(node);
				}
			}
		});

		// Keys Pane
		setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				for (Node nodeSelect : selectNodes) {
					getChildren().remove(nodeSelect);
				}
			}
		});

		// Handlers Button Arrow

		// Handlers Button Process
		btnProcess.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(mouseEvent.getSceneX());
			coordinatesMouse.setStartTranslateY(mouseEvent.getSceneY());

			ProcessoBuilder processoBuilder = new ProcessoBuilder();
			startProcess = processoBuilder.comTranslateX(coordinatesMouse.getStartTranslateX())
					.comTranslateY(coordinatesMouse.getStartTranslateY()).builder();
			getChildren().add(startProcess);

		});

		btnProcess.setOnMouseDragged(mouseEvent -> {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			startProcess.setTranslateX(endTranslateX);
			startProcess.setTranslateY(endTranslateY);
		});

		btnProcess.setOnMouseReleased(mouseEvent -> {
			verifyBoundsContainer(mouseEvent);
			setCursor(Cursor.DEFAULT);

		});

	}

	private void verifyBoundsContainer(final MouseEvent mouseEvent) {
		double mouseSceneX = mouseEvent.getSceneX();
		double mouseSceneY = mouseEvent.getSceneY();

		if (mouseSceneX < 0) {
			mouseSceneX = 1;
			startProcess.setTranslateX(mouseSceneX);
		}

		if (mouseSceneY < 0) {
			mouseSceneY = 1;
			startProcess.setTranslateY(mouseSceneY);
		}

		if (mouseSceneX > ConstantsSystem.WIDTH_SCENE) {
			mouseSceneX = ConstantsSystem.WIDTH_SCENE - ConstantsSystem.WIDTH_RECTANGLE;
			startProcess.setTranslateX(mouseSceneX);
		}

		if (mouseSceneY > ConstantsSystem.HEIGHT_SCENE) {
			mouseSceneY = ConstantsSystem.HEIGHT_SCENE - ConstantsSystem.HEIGHT_RECTANGLE;
			startProcess.setTranslateY(mouseSceneY);
		}
	}

	public Processo getProcessoFromCoordinatesXY(final MouseEvent mouseEvent) {

		ObservableList<Node> nodes = getChildren();

		for (Node node : nodes) {
			if (node instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) node;
				Bounds bounds = getBoundsFrom(rectangle);
				if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
						&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
						&& mouseEvent.getY() >= bounds.getMinY()) {

					return (Processo) node;
				}
			}
		}
		return null;
	}

	private boolean connectionExists(final Processo startProcess, final Processo endProcess) {
		boolean result = false;
		List<Connection> connections = getConnectionsFromContainer();
		for (Connection connection : connections) {
			result = connection.getStartRectangle().equals(startProcess)
					&& connection.getEndRectangle().equals(endProcess);
			break;

		}
		return result;
	}

	public List<Connection> getConnectionsFromContainer() {
		List<Connection> connections = new ArrayList<>();
		ObservableList<Node> nodes = getChildren();

		for (Node node : nodes) {
			if (node instanceof Connection) {
				connections.add((Connection) node);
			}
		}
		return connections;

	}

	public Node getNodeFromCoordinatesXY(final MouseEvent mouseEvent) {
		Node nodeResult = null;
		ObservableList<Node> nodes = getChildren();
		List<Node> nodesFound = new ArrayList<>();

		for (Node node : nodes) {
			Bounds bounds = node.getBoundsInParent();
			// Adicona Nodes das coordenadas do mouse
			if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
					&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
					&& mouseEvent.getY() >= bounds.getMinY()) {

				nodesFound.add(node);
			}
		}

		// Prioriza os Rectangles

		switch (nodesFound.size()) {
			case 0:
				nodeResult = null;
				break;
			case 1:
				nodeResult = nodesFound.get(0);
				break;
			default:
				for (Node node : nodesFound) {
					if (node instanceof Rectangle) {
						nodeResult = node;
						break;
					}
				}
				break;
		}
		return nodeResult;
	}

	public Bounds getBoundsFrom(final Rectangle rectangle) {
		Bounds bounds = rectangle.getBoundsInParent();
		return bounds;
	}

}
