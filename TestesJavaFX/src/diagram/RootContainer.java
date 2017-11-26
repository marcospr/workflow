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
import model.ConstantsSystem;
import model.CoordinatesMouseXY;

public class RootContainer extends BorderPane {
	private CoordinatesMouseXY coordinatesMouse;
	// private Processo processoHelper;
	private Diagram startDiagram;
	private Diagram endDiagram;
	// private Start start;
	private Set<Diagram> selectDiagram = new HashSet<>();

	public RootContainer() {
		buildToolbar();
		coordinatesMouse = new CoordinatesMouseXY();
	}

	private void buildToolbar() {
		Image imageProcess = new Image(getClass().getResourceAsStream("rounded.png"));
		Image imageStart = new Image(getClass().getResourceAsStream("start.png"));
		Image imageEnd = new Image(getClass().getResourceAsStream("end.png"));
		Image imageArrow = new Image(getClass().getResourceAsStream("arrow.png"));

		Button btnProcess = new Button("Process", new ImageView(imageProcess));
		btnProcess.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnStart = new Button("Start", new ImageView(imageStart));
		btnStart.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnEnd = new Button("End", new ImageView(imageEnd));
		btnEnd.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnArrow = new Button("SHIT + mouse drag/drop", new ImageView(imageArrow));
		btnArrow.setContentDisplay(ContentDisplay.BOTTOM);

		ToolBar toolBar1 = new ToolBar();
		toolBar1.getItems().addAll(new Separator(), btnProcess, btnStart, btnEnd, btnArrow, new Separator());

		setTop(toolBar1);

		// handlers Pane

		setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			startDiagram = getDiagramFromCoordinatesXY(mouseEvent);// getProcessoFromCoordinatesXY(mouseEvent);
			endDiagram = null;
			if (!mouseEvent.isControlDown()) {
				unselectNode();
				selectDiagram(startDiagram);
			}

		});

		setOnMouseDragged(mouseEvent -> {
			Diagram lastProcessHandled = getDiagramFromCoordinatesXY(mouseEvent);// getProcessoFromCoordinatesXY(mouseEvent);
			changeConnection(lastProcessHandled);
		});

		setOnMouseReleased(mouseEvent -> {
			Diagram lastDiagramHandle = getDiagramFromCoordinatesXY(mouseEvent);// getProcessoFromCoordinatesXY(mouseEvent);
			if (mouseEvent.isShiftDown()) {
				endDiagram = lastDiagramHandle;
				if (startDiagram != null && endDiagram != null && !startDiagram.equals(endDiagram)
						&& !startDiagram.equals(endDiagram.getNext())) {
					if (startDiagram.getNext() == null) {
						// Cria Arrow
						if (!connectionExists(startDiagram, endDiagram)) {
							createConnection();
						}
					}
				}
			} else {
				endDiagram = lastDiagramHandle;
				if (startDiagram != null && endDiagram != null) {
					changeConnection(endDiagram);
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
			Diagram diagram = getDiagramFromCoordinatesXY(mouseEvent);
			selectDiagram(diagram);
		});

		// Keys Pane
		setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				if (selectDiagram != null) {
					for (Diagram diagramSelect : selectDiagram) {
						getChildren().remove(diagramSelect);
						if (diagramSelect instanceof Processo || diagramSelect instanceof Start) {
							for (Connection connection : getConnectionsFromContainer()) {
								if (connection.getStartDiagram().equals(diagramSelect)
										|| connection.getEndDiagram().equals(diagramSelect)) {
									removeConnection(connection);
								}
							}
						} else if (diagramSelect instanceof Connection) {
							Connection connection = (Connection) diagramSelect;
							removeConnection(connection);
						} else if (diagramSelect instanceof Circle) {
						} else if (diagramSelect instanceof Circle) {
							// end workflow

						}
					}
					selectDiagram = new HashSet<>();
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
			startDiagram = processoBuilder.comTranslateX(coordinatesMouse.getStartTranslateX())
					.comTranslateY(coordinatesMouse.getStartTranslateY()).builder();

			getChildren().add((Node) startDiagram);

		});

		btnProcess.setOnMouseDragged(mouseEvent -> {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			((Node) startDiagram).setTranslateX(endTranslateX);
			((Node) startDiagram).setTranslateY(endTranslateY);
		});

		btnProcess.setOnMouseReleased(mouseEvent -> {
			setCursor(Cursor.DEFAULT);
		});

		// Handlers Button Start

		btnStart.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

			coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
			coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());

			startDiagram = new Start(mouseEvent.getSceneX(), mouseEvent.getSceneY(), ConstantsSystem.RADIUS);
			getChildren().add((Node) startDiagram);

		});

		btnStart.setOnMouseDragged(t -> {
			double offsetX = t.getSceneX() - coordinatesMouse.getStartSceneX();
			double offsetY = t.getSceneY() - coordinatesMouse.getStartSceneY();
			double newTranslateX = coordinatesMouse.getStartTranslateX() + offsetX;
			double newTranslateY = coordinatesMouse.getStartTranslateY() + offsetY;

			((Node) startDiagram).setTranslateX(newTranslateX);
			((Node) startDiagram).setTranslateY(newTranslateY);
		});

		btnStart.setOnMouseReleased(mouseEvent -> {
			setCursor(Cursor.DEFAULT);
		});

		// Handlers Button End

		btnEnd.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

			coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
			coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());

			startDiagram = new Start(mouseEvent.getSceneX(), mouseEvent.getSceneY(), ConstantsSystem.RADIUS);
			getChildren().add((Node) startDiagram);

		});

		btnEnd.setOnMouseDragged(t -> {
			double offsetX = t.getSceneX() - coordinatesMouse.getStartSceneX();
			double offsetY = t.getSceneY() - coordinatesMouse.getStartSceneY();
			double newTranslateX = coordinatesMouse.getStartTranslateX() + offsetX;
			double newTranslateY = coordinatesMouse.getStartTranslateY() + offsetY;

			((Node) startDiagram).setTranslateX(newTranslateX);
			((Node) startDiagram).setTranslateY(newTranslateY);
		});

		btnEnd.setOnMouseReleased(mouseEvent -> {
			setCursor(Cursor.DEFAULT);
		});

	}

	private void removeConnection(Connection connection) {
		getChildren().remove(connection);
		getChildren().remove(connection.getArrow());
		connection.getStartDiagram().setNext(null);
		connection.setStartDiagram(null);
		connection.setEndNode(null);
		connection.setArrow(null);
		connection = null;
	}

	private void unselectNode() {
		for (Diagram diagram : selectDiagram) {
			if (diagram instanceof Selectable) {
				((Node) diagram).setEffect(null);
				((Selectable) diagram).setSelect(Boolean.FALSE);
			}
		}
		selectDiagram = new HashSet<>();
	}

	private void selectDiagram(final Diagram diagram) {
		if (diagram != null) {
			if (diagram instanceof Selectable) {
				((Node) diagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
				((Selectable) diagram).setSelect(Boolean.TRUE);
				selectDiagram.add(diagram);
			}
		}
	}

	private void createConnection() {
		Connection conn = new Connection(startDiagram, endDiagram);

		getChildren().add(0, conn);
		getChildren().add(conn.getArrow());
	}

	private void changeConnection(final Diagram lastDiagramHandled) {

		if (lastDiagramHandled != null) {

			List<Connection> allConnections = getConnectionsFromContainer();

			for (Connection connection : allConnections) {
				if (connection.getStartDiagram().equals(lastDiagramHandled)
						|| connection.getEndDiagram().equals(lastDiagramHandled)) {

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

	private boolean connectionExists(final Diagram startDiagram, final Diagram endDiagram) {
		boolean result = false;
		List<Connection> connections = getConnectionsFromContainer();
		for (Connection connection : connections) {
			result = connection.getStartDiagram().equals(startDiagram) && connection.getEndDiagram().equals(endDiagram);
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

	public Diagram getDiagramFromCoordinatesXY(final MouseEvent mouseEvent) {
		Diagram diagramResult = null;
		ObservableList<Node> nodes = getChildren();
		// List<Node> nodesFound = new ArrayList<>();

		for (Node node : nodes) {
			if (node instanceof Diagram) {
				Bounds bounds = node.getBoundsInParent();
				// Adicona Nodes das coordenadas do mouse
				if (bounds != null && mouseEvent.getX() <= bounds.getMaxX() && mouseEvent.getY() <= bounds.getMaxY()
						&& mouseEvent.getX() >= bounds.getMinX() && mouseEvent.getY() >= bounds.getMinY()) {

					// nodesFound.add(node);
					diagramResult = (Diagram) node;
					break;
				}
			}
		}
		return diagramResult;
	}

	public Bounds getBoundsFrom(final Node node) {
		Bounds bounds = node.getBoundsInParent();
		return bounds;
	}

}
