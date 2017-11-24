package diagram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import diagram.builder.ProcessoBuilder;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;

public class RootContainer extends BorderPane {
	private CoordinatesMouseXY coordinatesMouse;
	// private Processo processoHelper;
	private Processo startProcess;
	private Processo endProcess;
	private Start start;
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

		Button btnArrow = new Button("SHIT + drag/drop", new ImageView(imageArrow));
		btnArrow.setContentDisplay(ContentDisplay.BOTTOM);

		ToolBar toolBar1 = new ToolBar();
		toolBar1.getItems().addAll(new Separator(), btnProcess, btnStart, btnEnd, btnArrow,
				new Separator());

		setTop(toolBar1);

		// handlers Pane

		setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			startProcess = getProcessoFromCoordinatesXY(mouseEvent);
			endProcess = null;
			unselectNode();
			selectNode(startProcess);

		});

		setOnMouseDragged(mouseEvent -> {
			Processo lastProcessClicked = getProcessoFromCoordinatesXY(mouseEvent);
			changeConnection(lastProcessClicked);
		});

		setOnMouseReleased(mouseEvent -> {
			Processo lastProcessClicked = getProcessoFromCoordinatesXY(mouseEvent);
			if (mouseEvent.isShiftDown()) {
				endProcess = lastProcessClicked;
				if (startProcess != null && endProcess != null && !startProcess.equals(endProcess)
						&& !startProcess.equals(endProcess.getNext())) {
					if (startProcess.getNext() == null) {
						// Cria Arrow
						if (!connectionExists(startProcess, endProcess)) {
							createConnection();
						}
					}
				}
			} else {
				endProcess = lastProcessClicked;
				if (startProcess != null && endProcess != null) {
					changeConnection(endProcess);
				}
			}

			setCursor(Cursor.DEFAULT);
		});

		setOnMouseClicked(mouseEvent -> {
			if (!mouseEvent.isControlDown()) {
				// Limpo qualquer seleção anterior
				unselectNode();
			}

			// Seleciono o Node atual das coordenadas XY
			Node node = getNodeFromCoordinatesXY(mouseEvent);
			selectNode(node);
		});

		// Keys Pane
		setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				if (selectNodes != null) {
					for (Node nodeSelect : selectNodes) {
						getChildren().remove(nodeSelect);
						if (nodeSelect instanceof Processo) {
							for (Connection connection : getConnectionsFromContainer()) {
								if (connection.getStartProcess().equals(nodeSelect)
										|| connection.getEndProcess().equals(nodeSelect)) {
									removeConnection(connection);
								}
							}
						} else if (nodeSelect instanceof Connection) {
							Connection connection = (Connection) nodeSelect;
							removeConnection(connection);
						} else if (nodeSelect instanceof Circle) {// start workflow

						} else if (nodeSelect instanceof Circle) {// end workflow

						}
					}
					selectNodes = new HashSet<>();
				}
			}
		});

		// Handlers

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
			setCursor(Cursor.DEFAULT);
		});

		// Handlers Button Start

		btnStart.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

			start = new Start(mouseEvent.getSceneX(), mouseEvent.getSceneY(), ConstantsSystem.RADIUS);
			getChildren().add(start);

		});

		btnStart.setOnMouseDragged(mouseEvent -> {
			double offsetX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double offsetY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();

			start.setCenterX(start.getCenterX() + offsetX);
			start.setCenterY(start.getCenterY() + offsetY);
		});

		btnStart.setOnMouseReleased(mouseEvent -> {
			setCursor(Cursor.DEFAULT);
		});

	}

	private void removeConnection(Connection connection) {
		getChildren().remove(connection);
		getChildren().remove(connection.getArrow());
		connection.getStartProcess().setNext(null);
		connection.setStartProcess(null);
		connection.setEndProcess(null);
		connection.setArrow(null);
		connection = null;
	}

	private void unselectNode() {
		for (Node node : selectNodes) {
			if (node instanceof Selectable) {
				node.setEffect(null);
				((Selectable) node).setSelect(Boolean.FALSE);
			}
		}
		selectNodes = new HashSet<>();
	}

	private void selectNode(final Node node) {
		if (node != null) {
			if (node instanceof Selectable) {
				node.setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
				((Selectable) node).setSelect(Boolean.TRUE);
				selectNodes.add(node);
			}
		}
	}

	private void createConnection() {
		Connection conn = new Connection(startProcess, endProcess);

		getChildren().add(0, conn);
		getChildren().add(conn.getArrow());
	}

	private void changeConnection(final Processo lastProcessClicked) {

		if (lastProcessClicked != null) {

			List<Connection> allConnections = getConnectionsFromContainer();

			for (Connection connection : allConnections) {
				if (connection.getStartProcess().equals(lastProcessClicked)
						|| connection.getEndProcess().equals(lastProcessClicked)) {

					// startProcess = connection.getStartProcess();
					// endProcess = connection.getEndProcess();

					// getChildren().remove(connection);

					connection.recalculateXY();

					// getChildren().add(connection);

					getChildren().remove(connection.getArrow());
					Arrow arrow = new Arrow(connection);
					connection.setArrow(arrow);
					getChildren().add(connection.getArrow());
				}
			}
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
			result = connection.getStartProcess().equals(startProcess)
					&& connection.getEndProcess().equals(endProcess);
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
