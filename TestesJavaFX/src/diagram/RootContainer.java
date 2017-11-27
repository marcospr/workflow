package diagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import diagram.builder.ProcessoBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApplication;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;
import view.ui.FormController;

public class RootContainer extends BorderPane {
	private CoordinatesMouseXY coordinatesMouse;
	// private Processo processoHelper;
	private DiagramContainer startDiagramContainer;
	private DiagramContainer endDiagramContainer;
	// private Start start;
	private Set<Selectable> selectDiagram = new HashSet<>();
	private Button buttonForm = new Button("...");

	public RootContainer() {
		buildToolbar();
		coordinatesMouse = new CoordinatesMouseXY();

	}

	private void buildToolbar() {
		Image imageProcess = new Image(getClass().getResourceAsStream("rounded.png"));
		Image imageStart = new Image(getClass().getResourceAsStream("start.png"));
		Image imageEnd = new Image(getClass().getResourceAsStream("end.png"));
		Image imageArrow = new Image(getClass().getResourceAsStream("arrow.png"));

		Rectangle rectangleGAS = new Rectangle(60, 40, Color.WHITE);
		rectangleGAS.setStroke(Color.BLACK);
		rectangleGAS.setStrokeWidth(1.0);
		rectangleGAS.setArcWidth(20);
		rectangleGAS.setArcHeight(20);
		StackPane.setMargin(rectangleGAS, new Insets(3));

		StackPane gas = new StackPane();
		Text gasText = new Text("GAS");
		gas.getChildren().addAll(rectangleGAS, gasText);
		//
		Rectangle rectangleGS = new Rectangle(60, 40, Color.WHITE);
		rectangleGS.setStroke(Color.BLACK);
		rectangleGS.setStrokeWidth(1.0);
		rectangleGS.setArcWidth(20);
		rectangleGS.setArcHeight(20);
		StackPane.setMargin(rectangleGS, new Insets(3));

		StackPane gs = new StackPane();
		Text gsText = new Text("GS");
		gs.getChildren().addAll(rectangleGS, gsText);

		Button btnProcessGAS = new Button("Execution", gas);
		btnProcessGAS.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnProcessGS = new Button("Execution", gs);
		btnProcessGS.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnStart = new Button("Start", new ImageView(imageStart));
		btnStart.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnEnd = new Button("End", new ImageView(imageEnd));
		btnEnd.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnArrow = new Button("SHIT + mouse drag/drop", new ImageView(imageArrow));
		btnArrow.setContentDisplay(ContentDisplay.BOTTOM);

		ToolBar toolBar1 = new ToolBar();
		toolBar1.getItems().addAll(new Separator(), btnProcessGAS, btnProcessGS, btnStart, btnEnd,
				btnArrow, new Separator());

		setTop(toolBar1);

		// handlers Pane

		setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			Diagram diagram = getDiagramFromCoordinatesXY(mouseEvent);
			if (isContainer(diagram)) {
				startDiagramContainer = (DiagramContainer) diagram;
				endDiagramContainer = null;
			}
			if (!mouseEvent.isControlDown()) {
				unselectDiagram();
				selectDiagram(diagram);
			}

		});

		setOnMouseDragged(mouseEvent -> {
			DiagramContainer lastProcessHandled = getDiagramFromCoordinatesXY(mouseEvent);
			changeConnection(lastProcessHandled);
		});

		setOnMouseReleased(mouseEvent -> {
			DiagramContainer lastDiagramHandle = getDiagramFromCoordinatesXY(mouseEvent);
			if (mouseEvent.isShiftDown()) {
				endDiagramContainer = lastDiagramHandle;
				if (startDiagramContainer != null && endDiagramContainer != null
						&& !startDiagramContainer.equals(endDiagramContainer)
						&& !startDiagramContainer.equals(endDiagramContainer.getNext())) {
					if (startDiagramContainer.getNext() == null) {
						// Cria Arrow
						if (!connectionExists(startDiagramContainer, endDiagramContainer)) {
							createConnection(startDiagramContainer, endDiagramContainer);
						}
					}
				}
			} else {
				endDiagramContainer = lastDiagramHandle;
				if (startDiagramContainer != null && endDiagramContainer != null) {
					changeConnection(endDiagramContainer);
				}
			}

			setCursor(Cursor.DEFAULT);
		});

		setOnMouseClicked(mouseEvent -> {
			if (!mouseEvent.isControlDown()) {
				// Limpo qualquer seleção anterior
				unselectDiagram();
			}

			// Seleciono o Node atual das coordenadas XY
			Diagram diagram = getDiagramFromCoordinatesXY(mouseEvent);
			if (diagram == null) {
				diagram = getConnectionsFromCoordinatesXY(mouseEvent);
			}
			selectDiagram(diagram);

			// other rule
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				if (mouseEvent.getClickCount() == 2) {
					try {
						// Carrega a person overview.
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(MainApplication.class.getResource("../view/ui/form.fxml"));

						// Dá ao controlador acesso à the main app.
						FormController controller = loader.getController();
						AnchorPane anchorPane;
						anchorPane = (AnchorPane) loader.load();

						// Cria o palco dialogStage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Register Input Parameters");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(getScene().getWindow());
						Scene scene = new Scene(anchorPane);
						dialogStage.setScene(scene);

						// Mostra a janela e espera até o usuário fechar.
						dialogStage.showAndWait();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

		// Keys Pane
		setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				if (selectDiagram != null) {
					for (Selectable diagramSelect : selectDiagram) {
						getChildren().remove(diagramSelect);
						if (!(diagramSelect instanceof Connection)) {
							for (Connection connection : getConnectionsFromContainer()) {
								if (connection.getStartDiagramContainer().equals(diagramSelect)
										|| connection.getEndDiagramContainer().equals(diagramSelect)) {
									removeConnection(connection);
								}
							}
						} else {
							// Connection
							Connection connection = (Connection) diagramSelect;
							removeConnection(connection);
						}
					}
					selectDiagram = new HashSet<>();
				}
			}
		});

		// Handlers Button Process

		btnProcessGAS.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(mouseEvent.getSceneX());
			coordinatesMouse.setStartTranslateY(mouseEvent.getSceneY());

			ProcessoBuilder processoBuilder = new ProcessoBuilder();
			Processo process = processoBuilder.comTranslateX(coordinatesMouse.getStartTranslateX())
					.comTranslateY(coordinatesMouse.getStartTranslateY()).builder();

			Text texto = new Text("GAS");

			startDiagramContainer =
					new DiagramContainer(process, mouseEvent.getSceneX(), mouseEvent.getSceneY(), texto);

			getChildren().add(startDiagramContainer);

		});

		btnProcessGAS.setOnMouseDragged(mouseEvent -> {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			((Node) startDiagramContainer).setTranslateX(endTranslateX);
			((Node) startDiagramContainer).setTranslateY(endTranslateY);
		});

		btnProcessGAS.setOnMouseReleased(mouseEvent -> {
			correctBorders(mouseEvent, startDiagramContainer);
			setCursor(Cursor.DEFAULT);
		});

		// button GS
		btnProcessGS.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(mouseEvent.getSceneX());
			coordinatesMouse.setStartTranslateY(mouseEvent.getSceneY());

			ProcessoBuilder processoBuilder = new ProcessoBuilder();
			Processo process = processoBuilder.comTranslateX(coordinatesMouse.getStartTranslateX())
					.comTranslateY(coordinatesMouse.getStartTranslateY()).builder();

			Text texto = new Text("GS");

			startDiagramContainer =
					new DiagramContainer(process, mouseEvent.getSceneX(), mouseEvent.getSceneY(), texto);

			getChildren().add(startDiagramContainer);

		});

		btnProcessGS.setOnMouseDragged(mouseEvent -> {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			((Node) startDiagramContainer).setTranslateX(endTranslateX);
			((Node) startDiagramContainer).setTranslateY(endTranslateY);
		});

		btnProcessGS.setOnMouseReleased(mouseEvent -> {
			correctBorders(mouseEvent, startDiagramContainer);
			setCursor(Cursor.DEFAULT);
		});

		// Handlers Button Start

		btnStart.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			if (!startOrEndExists(Start.class)) {
				coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
				coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

				coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
				coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());
				Start start =
						new Start(mouseEvent.getSceneX(), mouseEvent.getSceneY(), ConstantsSystem.RADIUS);

				Text texto = new Text("I");
				texto.setTextAlignment(TextAlignment.CENTER);
				startDiagramContainer =
						new DiagramContainer(start, mouseEvent.getSceneX(), mouseEvent.getSceneY(), texto);
				getChildren().add(startDiagramContainer);
			} else {
				startDiagramContainer = null;
			}
		});

		btnStart.setOnMouseDragged(mouseEvent -> {
			// double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			// double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			// double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			// double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;
			if (startDiagramContainer != null) {
				((Node) startDiagramContainer).setTranslateX(mouseEvent.getSceneX());
				((Node) startDiagramContainer).setTranslateY(mouseEvent.getSceneY());
			}
		});

		btnStart.setOnMouseReleased(mouseEvent -> {
			correctBorders(mouseEvent, startDiagramContainer);
			setCursor(Cursor.DEFAULT);
		});

		// Handlers Button End

		btnEnd.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			if (!startOrEndExists(End.class)) {
				coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
				coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

				coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
				coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());

				End end = new End(mouseEvent.getSceneX(), mouseEvent.getSceneY(), ConstantsSystem.RADIUS);
				Text texto = new Text("F");
				texto.setTextAlignment(TextAlignment.CENTER);

				startDiagramContainer =
						new DiagramContainer(end, mouseEvent.getSceneX(), mouseEvent.getSceneY(), texto);
				getChildren().add(startDiagramContainer);
			} else {
				startDiagramContainer = null;
			}

		});

		btnEnd.setOnMouseDragged(mouseEvent -> {
			// double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			// double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			// double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			// double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;
			if (startDiagramContainer != null) {
				((Node) startDiagramContainer).setTranslateX(mouseEvent.getSceneX());
				((Node) startDiagramContainer).setTranslateY(mouseEvent.getSceneY());
			}
		});

		btnEnd.setOnMouseReleased(mouseEvent -> {
			correctBorders(mouseEvent, startDiagramContainer);
			setCursor(Cursor.DEFAULT);
		});

	}

	private void removeConnection(Connection connection) {
		getChildren().remove(connection);
		getChildren().remove(connection.getArrow());
		connection.getStartDiagramContainer().setNext(null);
		connection.setStartDiagramContainer(null);
		connection.setEndDiagramContainer(null);
		connection.setArrow(null);
		connection = null;
	}

	private void unselectDiagram() {
		for (Selectable selectable : selectDiagram) {
			((Node) selectable).setEffect(null);
			selectable.setSelect(Boolean.FALSE);
		}
		selectDiagram = new HashSet<>();
	}

	private void selectDiagram(final Diagram diagram) {
		if (diagram != null) {
			if (diagram instanceof Selectable) {
				((Node) diagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
				diagram.setSelect(Boolean.TRUE);
				selectDiagram.add(diagram);
			}
		}
	}

	private void createConnection(final DiagramContainer startDiagramContainer,
			final DiagramContainer endDiagramContainer) {
		Connection conn = new Connection(startDiagramContainer, endDiagramContainer);

		getChildren().add(0, conn);
		getChildren().add(conn.getArrow());
	}

	private void changeConnection(final DiagramContainer lastDiagramHandled) {

		if (lastDiagramHandled != null) {

			List<Connection> allConnections = getConnectionsFromContainer();

			for (Connection connection : allConnections) {
				if (connection.getStartDiagramContainer().equals(lastDiagramHandled)
						|| connection.getEndDiagramContainer().equals(lastDiagramHandled)) {

					connection.recalculateXY();

					getChildren().remove(connection.getArrow());
					Arrow arrow = new Arrow(connection);
					connection.setArrow(arrow);
					getChildren().add(connection.getArrow());
				}
			}
		}

	}

	private boolean startOrEndExists(final Class clazz) {
		boolean result = false;
		ObservableList<Node> nodesContainerMain = getChildren();
		for (Node node : nodesContainerMain) {
			if (node instanceof DiagramContainer) {
				DiagramContainer diagramContainer = (DiagramContainer) node;

				ObservableList<Node> childrens = diagramContainer.getChildren();
				for (Node children : childrens) {
					if (clazz.isAssignableFrom(children.getClass())) {
						result = true;
						break;
					} else if (clazz.isAssignableFrom(children.getClass())) {
						result = true;
						break;
					}

				}
			}
		}
		return result;
	}

	private boolean connectionExists(final DiagramContainer startDiagramContainer,
			final DiagramContainer endDiagramContainer) {
		boolean result = false;
		List<Connection> connections = getConnectionsFromContainer();
		for (Connection connection : connections) {
			result = connection.getStartDiagramContainer().equals(startDiagramContainer)
					&& connection.getEndDiagramContainer().equals(endDiagramContainer);
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

	public Connection getConnectionsFromCoordinatesXY(final MouseEvent mouseEvent) {
		ObservableList<Node> nodes = getChildren();
		Connection connectionResult = null;

		for (Node node : nodes) {
			if (node instanceof Connection) {
				Bounds bounds = node.getBoundsInParent();
				// verifica coordenadas do mouse
				if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
						&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
						&& mouseEvent.getY() >= bounds.getMinY()) {

					connectionResult = (Connection) node;
					break;
				}
			}
		}
		return connectionResult;

	}

	public DiagramContainer getDiagramFromCoordinatesXY(final MouseEvent mouseEvent) {
		DiagramContainer diagramResult = null;
		ObservableList<Node> nodes = getChildren();
		// List<Node> nodesFound = new ArrayList<>();

		for (Node node : nodes) {
			if (node instanceof DiagramContainer) {
				Bounds bounds = node.getBoundsInParent();
				// verifica coordenadas do mouse
				if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
						&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
						&& mouseEvent.getY() >= bounds.getMinY()) {

					diagramResult = (DiagramContainer) node;
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

	private Boolean isContainer(final Diagram diagram) {
		return diagram instanceof DiagramContainer;
	}

	private void correctBorders(final MouseEvent mouseEvent, final DiagramContainer diagram) {
		if (!mouseEvent.isShiftDown()) {
			if (diagram != null) {
				double mouseSceneX = mouseEvent.getSceneX();
				double mouseSceneY = mouseEvent.getSceneY();
				if (mouseSceneX < 0) {
					diagram.setTranslateX(1);
				}

				if (mouseSceneY < 0) {
					diagram.setTranslateY(1);
				}

				if (mouseSceneX > ConstantsSystem.WIDTH_SCENE) {
					diagram.setTranslateX(ConstantsSystem.WIDTH_SCENE - ConstantsSystem.WIDTH_RECTANGLE);
				}

				if (mouseSceneY > ConstantsSystem.HEIGHT_SCENE) {
					diagram.setTranslateY(ConstantsSystem.HEIGHT_SCENE - ConstantsSystem.HEIGHT_RECTANGLE);
				}

				setCursor(Cursor.DEFAULT);
			}
		}
	}

}
