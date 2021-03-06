package br.unicamp.cepetro.unisim.mero.ui.app.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.unicamp.cepetro.unisim.mero.ui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandAQNS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandDS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandGAS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandGU;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandHLDG;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.Arrow;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.Connection;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.Diagram;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.EndDiagram;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.ProcessDiagram;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.Selectable;
import br.unicamp.cepetro.unisim.mero.ui.app.diagram.StartDiagram;
import br.unicamp.cepetro.unisim.mero.ui.app.factory.CommandFactory;
import br.unicamp.cepetro.unisim.mero.ui.app.figure.End;
import br.unicamp.cepetro.unisim.mero.ui.app.figure.LineTemporary;
import br.unicamp.cepetro.unisim.mero.ui.app.figure.Processo;
import br.unicamp.cepetro.unisim.mero.ui.app.figure.Start;
import br.unicamp.cepetro.unisim.mero.ui.app.helper.PaneHelper;
import br.unicamp.cepetro.unisim.mero.ui.app.model.CommandType;
import br.unicamp.cepetro.unisim.mero.ui.app.model.ConstantsSystem;
import br.unicamp.cepetro.unisim.mero.ui.app.model.CoordinatesMouseXY;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class MainController {
	private List<Command> commands;
	private static final String ID_BTN_RUN = "#btnRun";
	private static final String ID_BTN_END = "#btnEnd";
	private static final String ID_BTN_START = "#btnStart";

	@Autowired
	private CoordinatesMouseXY coordinatesMouse;

	@Autowired
	private CommandFactory commandFactory;

	private Diagram startDiagramContainer;
	private Diagram endDiagramContainer;
	private Set<Selectable> selectDiagrams;
	//

	@FXML
	private ToolBar toolBar;

	@FXML
	private HBox topContainer;

	@FXML
	private BorderPane root;

	private EventHandler<? super MouseEvent> handleButtonToolbarOnMousePressed;
	private EventHandler<? super MouseEvent> handleButtonToolbarOnMouseDragged;
	private EventHandler<? super MouseEvent> handleButtonToolbarOnMouseReleased;

	private EventHandler<? super MouseEvent> handleProcessDiagramOnMouseClicked;

	private EventHandler<? super MouseEvent> handleButtonStartOnMousePressed;
	private EventHandler<? super MouseEvent> handleButtonStartOnMouseDragged;
	private EventHandler<? super MouseEvent> handleButtonStartOnMouseReleased;

	private EventHandler<? super MouseEvent> handleButtonEndOnMousePressed;
	private EventHandler<? super MouseEvent> handleButtonEndOnMouseDragged;
	private EventHandler<? super MouseEvent> handleButtonEndOnMouseReleased;

	private EventHandler<? super MouseEvent> handleButtonRunOnMouseClicked;

	public MainController() {

		commands = Arrays.asList(new CommandHLDG(), new CommandGAS(), new CommandDS(), new CommandGU(),
				new CommandAQNS());
	}

	@FXML
	private void initialize() {
		buildToolbar();
		selectDiagrams = new HashSet<>();
		createToolBarHandlers();
		createRootHandlers();
	}

	private void buildToolbar() {
		Image imageStart =
				new Image(getClass().getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("start.png")));
		Image imageEnd =
				new Image(getClass().getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("end.png")));
		Image imageArrow =
				new Image(getClass().getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("arrow.png")));
		Image imageCheck =
				new Image(getClass().getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("check.png")));

		toolBar.getItems().add(new Separator());

		for (Command command : commands) {
			StackPane buttonStackPane = createButtonStackPane(command);
			Button btn = new Button(null, buttonStackPane);
			btn.setContentDisplay(ContentDisplay.BOTTOM);
			toolBar.getItems().add(btn);
		}

		Button btnStart = new Button("Start", new ImageView(imageStart));
		btnStart.setId("#btnStart");
		btnStart.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnEnd = new Button("End", new ImageView(imageEnd));
		btnEnd.setId("#btnEnd");
		btnEnd.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnArrow = new Button("SHIT + mouse drag/drop", new ImageView(imageArrow));
		btnArrow.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnRun = new Button("Run", new ImageView(imageCheck));
		btnRun.setId("#btnRun");
		toolBar.getItems().addAll(new Separator(), btnStart, btnEnd, new Separator(), btnRun);
	}

	private StackPane createButtonStackPane(final Command command) {
		Processo processo = new Processo(60, 40);
		// Rectangle rectangle = new Rectangle(60, 40, Color.WHITE);
		// rectangle.setStroke(Color.GREY);
		// rectangle.setStrokeWidth(1.0);
		// rectangle.setArcWidth(20);
		// rectangle.setArcHeight(20);
		StackPane.setMargin(processo, new Insets(3));

		StackPane stackPane = new StackPane();
		Text label = new Text(command.getName());
		label.setStyle("-fx-font-weight: bold;");
		label.fillProperty().set(Color.INDIANRED);
		stackPane.getChildren().addAll(processo, label);

		return stackPane;
	}

	private void createToolBarHandlers() {

		createHandleOnMouseDoubleClickedProccess();

		createGeneralButtonsHandlers();

		createButtonHandleRun();

		createButtonHandleStart();

		createButtonHandleEnd();

		associateHandlesWithButtons();

	}

	private void associateHandlesWithButtons() {
		ObservableList<Node> toolBarItems = toolBar.getItems();
		for (Node node : toolBarItems) {
			if (node instanceof Button) {
				Button btn = (Button) node;
				if (btn.getGraphic() instanceof StackPane) {
					btn.setOnMousePressed(handleButtonToolbarOnMousePressed);
					btn.setOnMouseDragged(handleButtonToolbarOnMouseDragged);
					btn.setOnMouseReleased(handleButtonToolbarOnMouseReleased);
				} else {
					// Node lookup = root.lookup(btn.getId());
					if (ID_BTN_START.equals(btn.getId())) {
						btn.setOnMousePressed(handleButtonStartOnMousePressed);
						btn.setOnMouseDragged(handleButtonStartOnMouseDragged);
						btn.setOnMouseReleased(handleButtonStartOnMouseReleased);

					} else if (ID_BTN_END.equals(btn.getId())) {
						btn.setOnMousePressed(handleButtonEndOnMousePressed);
						btn.setOnMouseDragged(handleButtonEndOnMouseDragged);
						btn.setOnMouseReleased(handleButtonEndOnMouseReleased);

					} else if (ID_BTN_RUN.equals(btn.getId())) {
						btn.setOnMouseClicked(handleButtonRunOnMouseClicked);

					}
				}
			}
		}
	}

	private void createHandleOnMouseDoubleClickedProccess() {
		// Handlers Process Button
		handleProcessDiagramOnMouseClicked = mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				if (mouseEvent.getClickCount() == 2) {
					openForm();
				}
			}
		};
	}

	private void createButtonHandleEnd() {
		// Handlers Button End
		handleButtonEndOnMousePressed = mouseEvent -> {
			root.setCursor(Cursor.HAND);
			if (!startOrEndExists(End.class)) {
				coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
				coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

				coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
				coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());

				startDiagramContainer = new EndDiagram(mouseEvent.getSceneX(), mouseEvent.getSceneY(),
						commandFactory.createCommand(CommandType.End));

				root.getChildren().add(startDiagramContainer);
			} else {
				startDiagramContainer = null;
			}
		};

		handleButtonEndOnMouseDragged = mouseEvent -> {
			if (startDiagramContainer != null) {
				((Node) startDiagramContainer).setTranslateX(mouseEvent.getSceneX());
				((Node) startDiagramContainer).setTranslateY(mouseEvent.getSceneY());
			}
		};

		handleButtonEndOnMouseReleased = mouseEvent -> {
			adjustBordersIfNecessary(mouseEvent, startDiagramContainer);
			root.setCursor(Cursor.DEFAULT);
		};
	}

	private void createButtonHandleStart() {
		// Handlers Button Start
		handleButtonStartOnMousePressed = mouseEvent -> {
			root.setCursor(Cursor.HAND);
			if (!startOrEndExists(Start.class)) {
				coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
				coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

				coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
				coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());

				startDiagramContainer = new StartDiagram(mouseEvent.getSceneX(), mouseEvent.getSceneY(),
						commandFactory.createCommand(CommandType.Start));

				root.getChildren().add(startDiagramContainer);
			} else {
				startDiagramContainer = null;
			}
		};

		handleButtonStartOnMouseDragged = mouseEvent -> {
			if (startDiagramContainer != null) {
				((Node) startDiagramContainer).setTranslateX(mouseEvent.getSceneX());
				((Node) startDiagramContainer).setTranslateY(mouseEvent.getSceneY());
			}
		};

		handleButtonStartOnMouseReleased = mouseEvent -> {
			adjustBordersIfNecessary(mouseEvent, startDiagramContainer);
			root.setCursor(Cursor.DEFAULT);
		};
	}

	private void createButtonHandleRun() {
		// Handlers Button Execute
		handleButtonRunOnMouseClicked = mouseEvent -> {
			List<StartDiagram> starts = PaneHelper.getSpecificNodesFromPane(StartDiagram.class, root);
			if (starts != null && starts.size() == 1) {
				StartDiagram start = starts.get(0);
				unSelectElement();
				start.execute(null);
			}
		};
	}

	private void createGeneralButtonsHandlers() {
		// general toolbar button handlers
		handleButtonToolbarOnMousePressed = mouseEvent -> {
			root.setCursor(Cursor.HAND);

			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(mouseEvent.getSceneX());
			coordinatesMouse.setStartTranslateY(mouseEvent.getSceneY());

			// index 0-> Rectangle, 1-> Text
			Text text =
					(Text) ((StackPane) ((Button) mouseEvent.getSource()).getGraphic()).getChildren().get(1);

			CommandType commandType = Enum.valueOf(CommandType.class, text.getText());

			startDiagramContainer = new ProcessDiagram(mouseEvent.getSceneX(), mouseEvent.getSceneY(),
					commandFactory.createCommand(commandType), text.getText());

			startDiagramContainer.setOnMouseClicked(handleProcessDiagramOnMouseClicked);

			root.getChildren().add(startDiagramContainer);
		};

		handleButtonToolbarOnMouseDragged = mouseEvent -> {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			((Node) startDiagramContainer).setTranslateX(endTranslateX);
			((Node) startDiagramContainer).setTranslateY(endTranslateY);
		};

		handleButtonToolbarOnMouseReleased = mouseEvent -> {
			adjustBordersIfNecessary(mouseEvent, startDiagramContainer);
			root.setCursor(Cursor.DEFAULT);
		};
	}

	private void createRootHandlers() {
		// handlers Root
		root.setOnMousePressed(mouseEvent -> {
			root.setCursor(Cursor.HAND);
			Diagram diagram = PaneHelper.getSpecificNodeFromCoordinatesXY(mouseEvent.getX(),
					mouseEvent.getY(), root, Diagram.class);

			if (diagram != null) {
				startDiagramContainer = diagram;
				endDiagramContainer = null;
			}
			if (!mouseEvent.isControlDown()) {
				unSelectElement();
				selectElement(diagram);
			}

		});

		root.setOnMouseDragged(mouseEvent -> {
			Diagram lastProcessHandled = PaneHelper.getSpecificNodeFromCoordinatesXY(mouseEvent.getX(),
					mouseEvent.getY(), root, Diagram.class);
			if (lastProcessHandled != null) {
				changeConnection(lastProcessHandled);
			} else {
				// Trata-se de nova conex�o
				createLineTemporary(mouseEvent);
			}
		});

		root.setOnMouseReleased(mouseEvent -> {
			Diagram lastDiagramHandle = PaneHelper.getSpecificNodeFromCoordinatesXY(mouseEvent.getX(),
					mouseEvent.getY(), root, Diagram.class);
			if (mouseEvent.isShiftDown()) {
				endDiagramContainer = lastDiagramHandle;
				if (shouldCreateConnection()) {
					if (!existsConnection(startDiagramContainer, endDiagramContainer)) {
						createConnection(startDiagramContainer, endDiagramContainer);
					}
				}
			} else {
				endDiagramContainer = lastDiagramHandle;
				if (startDiagramContainer != null && endDiagramContainer != null) {
					changeConnection(endDiagramContainer);
				}
			}
			removeLineTemporary();

			root.setCursor(Cursor.DEFAULT);
		});

		root.setOnMouseClicked(mouseEvent -> {
			if (!mouseEvent.isControlDown()) {
				// Limpo qualquer sele��o anterior
				unSelectElement();
			}

			// Seleciono o Elemento atual das coordenadas XY
			Selectable element = PaneHelper.getSpecificNodeFromCoordinatesXY(mouseEvent.getX(),
					mouseEvent.getY(), root, Selectable.class);
			if (element != null) {
				selectElement(element);
			}
		});

		// Keys Pane
		root.setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				if (selectDiagrams != null) {
					for (Selectable diagramSelect : selectDiagrams) {
						root.getChildren().remove(diagramSelect);
						if (!(diagramSelect instanceof Connection)) {
							for (Connection connection : PaneHelper.getConnectionFromPane(root)) {
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
					selectDiagrams = new HashSet<>();
				}
			}
		});

	}

	private boolean shouldCreateConnection() {
		boolean result = false;
		if (startDiagramContainer != null && endDiagramContainer != null) {
			// Rules
			boolean isAutoConnection = startDiagramContainer.equals(endDiagramContainer);
			boolean isCircularConnection = startDiagramContainer.equals(endDiagramContainer.getNext());
			boolean isStartingFromEndDiagram = startDiagramContainer instanceof EndDiagram;
			boolean isConnectingWithStartDiagram = endDiagramContainer instanceof StartDiagram;
			boolean isWithoutConnector = startDiagramContainer.getNext() == null;

			result = !isAutoConnection && !isCircularConnection && !isStartingFromEndDiagram
					&& !isConnectingWithStartDiagram && isWithoutConnector;
		}
		return result;
	}

	private void adjustBordersIfNecessary(final MouseEvent mouseEvent, final Diagram diagram) {
		if (!mouseEvent.isShiftDown()) {
			if (diagram != null) {
				diagram.adjustBorders(mouseEvent.getSceneX(), mouseEvent.getSceneY());
				root.setCursor(Cursor.DEFAULT);
			}
		}
	}

	private void openForm() {
		try {
			// Carrega
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("../view/form.fxml"));

			AnchorPane anchorPane;
			anchorPane = (AnchorPane) loader.load();

			// Cria o palco dialogStage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Register Input Parameters");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(root.getScene().getWindow());
			Scene scene = new Scene(anchorPane);
			dialogStage.setScene(scene);

			// Mostra a janela e espera até o usuário fechar
			dialogStage.showAndWait();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> boolean startOrEndExists(final Class<T> clazz) {
		boolean result = false;
		ObservableList<Node> nodesContainerMain = root.getChildren();
		for (Node node : nodesContainerMain) {
			if (node instanceof Diagram) {
				Diagram diagramContainer = (Diagram) node;

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

	private void unSelectElement() {
		for (Selectable selectable : selectDiagrams) {
			selectable.unSelect();
		}
		selectDiagrams = new HashSet<>();
	}

	private void selectElement(final Selectable selectable) {
		if (selectable != null) {
			selectable.select();
			selectDiagrams.add(selectable);
		}
	}

	private void changeConnection(final Diagram lastDiagramHandled) {

		if (lastDiagramHandled != null) {

			List<Connection> allConnections = PaneHelper.getConnectionFromPane(root);

			for (Connection connection : allConnections) {
				if (connection.getStartDiagramContainer().equals(lastDiagramHandled)
						|| connection.getEndDiagramContainer().equals(lastDiagramHandled)) {

					connection.recalculateXY();

					root.getChildren().remove(connection.getArrow());
					Arrow arrow = new Arrow(connection);
					connection.setArrow(arrow);
					root.getChildren().add(connection.getArrow());
					arrow.toFront();
				}
			}
		}

	}

	private void removeConnection(Connection connection) {
		root.getChildren().remove(connection);
		root.getChildren().remove(connection.getArrow());
		connection.getStartDiagramContainer().setNext(null);
		connection.setStartDiagramContainer(null);
		connection.setEndDiagramContainer(null);
		connection.setArrow(null);
		connection = null;
	}

	private void createLineTemporary(final MouseEvent mouseEvent) {
		removeLineTemporary();
		LineTemporary line = null;
		if (startDiagramContainer != null) {
			line = new LineTemporary(startDiagramContainer.getTranslateX(),
					startDiagramContainer.getTranslateY(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
			root.getChildren().add(0, line);
		}
	}

	private void removeLineTemporary() {
		// Node nodeToRemove = null;
		ObservableList<Node> nodes = root.getChildren();
		// for (Node node : nodes) {
		// if (node instanceof LineTemporary) {
		// nodeToRemove = node;
		// break;
		// }
		// }
		// if (nodeToRemove != null) {
		// nodes.remove(nodeToRemove);
		// }
		if (nodes.get(0) instanceof LineTemporary) {
			nodes.remove(0);
		}
	}

	private boolean existsConnection(final Diagram startDiagramContainer,
			final Diagram endDiagramContainer) {
		boolean result = false;
		List<Connection> connections = PaneHelper.getConnectionFromPane(root);
		for (Connection connection : connections) {
			result = connection.getStartDiagramContainer().equals(startDiagramContainer)
					&& connection.getEndDiagramContainer().equals(endDiagramContainer);
			break;

		}
		return result;
	}

	private void createConnection(final Diagram startDiagramContainer,
			final Diagram endDiagramContainer) {
		Connection conn = new Connection(startDiagramContainer, endDiagramContainer);

		root.getChildren().add(conn);
		root.getChildren().add(conn.getArrow());
		conn.getArrow().toFront();
	}

}
