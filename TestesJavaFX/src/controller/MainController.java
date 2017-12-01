package controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import diagram.AbstractDiagram;
import diagram.Arrow;
import diagram.Connection;
import diagram.EndDiagram;
import diagram.LineTemporary;
import diagram.ProcessDiagram;
import diagram.Processo;
import diagram.RootContainer;
import diagram.Selectable;
import diagram.Start;
import diagram.StartDiagram;
import factory.CommandFactory;
import helper.PaneHelper;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApplication;
import model.CommandType;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;
import view.ui.FormController;

public class MainController {
	private static final String ID_BTN_RUN = "#btnRun";
	private static final String ID_BTN_END = "#btnEnd";
	private static final String ID_BTN_START = "#btnStart";
	private CoordinatesMouseXY coordinatesMouse;
	private RootContainer root;
	private AbstractDiagram startDiagramContainer;
	private AbstractDiagram endDiagramContainer;
	private CommandFactory commandFactory;
	private PaneHelper paneHelper;
	private Set<Selectable> selectDiagrams = new HashSet<>();
	//
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

	public MainController(final RootContainer root, final CommandFactory commandFactory,
			final PaneHelper paneHelper) {
		this.root = root;
		this.commandFactory = commandFactory;
		this.paneHelper = paneHelper;

		createToolBarHandlers();
		createRootHandlers();
		coordinatesMouse = new CoordinatesMouseXY();
	}

	private void createToolBarHandlers() {

		// Handlers Process Button
		handleProcessDiagramOnMouseClicked = mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				if (mouseEvent.getClickCount() == 2) {
					openForm();
				}
			}
		};

		// Handlers Toolbar buttons

		handleButtonToolbarOnMousePressed = mouseEvent -> {
			root.setCursor(Cursor.HAND);

			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(mouseEvent.getSceneX());
			coordinatesMouse.setStartTranslateY(mouseEvent.getSceneY());

			Processo process = new Processo();

			// index 0-> Rectangle, 1-> Text
			Text text =
					(Text) ((StackPane) ((Button) mouseEvent.getSource()).getGraphic()).getChildren().get(1);

			Text texto = new Text(text.getText());
			texto.fillProperty().set(Color.INDIANRED);
			texto.setStyle("-fx-font-weight: bold;");

			CommandType commandType = Enum.valueOf(CommandType.class, text.getText());

			startDiagramContainer = new ProcessDiagram(process, mouseEvent.getSceneX(),
					mouseEvent.getSceneY(), commandFactory.createCommand(commandType));

			Image imageGear = new Image(getClass().getResourceAsStream("../resources/gear.png"));
			ImageView imageViewGear = new ImageView(imageGear);

			texto.setTranslateX(startDiagramContainer.getLayoutX() + 20);
			imageViewGear.setTranslateX(startDiagramContainer.getLayoutX() - 20);

			startDiagramContainer.getChildren().addAll(texto, imageViewGear);

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
			correctBordersIfNecessary(mouseEvent, startDiagramContainer);
			root.setCursor(Cursor.DEFAULT);
		};

		// Handlers Button Start
		handleButtonStartOnMousePressed = mouseEvent -> {
			root.setCursor(Cursor.HAND);
			if (!startOrEndExists(Start.class)) {
				coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
				coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

				coordinatesMouse.setStartTranslateX(((Button) mouseEvent.getSource()).getTranslateX());
				coordinatesMouse.setStartTranslateY(((Button) mouseEvent.getSource()).getTranslateY());
				Start start =
						new Start(mouseEvent.getSceneX(), mouseEvent.getSceneY(), ConstantsSystem.RADIUS);

				Text texto = new Text("Start");
				texto.fillProperty().set(Color.INDIANRED);
				texto.setTextAlignment(TextAlignment.CENTER);
				texto.setStyle("-fx-font-weight: bold;");

				startDiagramContainer = new StartDiagram(start, mouseEvent.getSceneX(),
						mouseEvent.getSceneY(), commandFactory.createCommand(CommandType.Start));

				// texto.setTranslateY(startDiagramContainer.getLayoutY() + 25);

				startDiagramContainer.getChildren().add(texto);
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
			correctBordersIfNecessary(mouseEvent, startDiagramContainer);
			root.setCursor(Cursor.DEFAULT);
		};

		ToolBar toolBar = root.getToolbar();
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
					if (btn.getId().equals(ID_BTN_START)) {
						btn.setOnMousePressed(handleButtonStartOnMousePressed);
						btn.setOnMouseDragged(handleButtonStartOnMouseDragged);
						btn.setOnMouseReleased(handleButtonStartOnMouseReleased);

					} else if (btn.getId().equals(ID_BTN_END)) {
						btn.setOnMousePressed(handleButtonEndOnMousePressed);
						btn.setOnMouseDragged(handleButtonEndOnMouseDragged);
						btn.setOnMouseReleased(handleButtonEndOnMouseReleased);

					} else if (btn.getId().equals(ID_BTN_RUN)) {
						btn.setOnMouseClicked(handleButtonRunOnMouseClicked);

					}
				}
			}
		}

	}

	private void createRootHandlers() {
		// handlers Root
		root.setOnMousePressed(mouseEvent -> {
			root.setCursor(Cursor.HAND);
			AbstractDiagram diagram = paneHelper.getSpecificNodeFromCoordinatesXY(mouseEvent.getX(),
					mouseEvent.getY(), root, AbstractDiagram.class);

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
			AbstractDiagram lastProcessHandled = paneHelper.getSpecificNodeFromCoordinatesXY(
					mouseEvent.getX(), mouseEvent.getY(), root, AbstractDiagram.class);
			if (lastProcessHandled != null) {
				changeConnection(lastProcessHandled);
			} else {
				// Trata-se de nova conex�o
				createLineTemporary(mouseEvent);
			}
		});

		root.setOnMouseReleased(mouseEvent -> {
			AbstractDiagram lastDiagramHandle = paneHelper.getSpecificNodeFromCoordinatesXY(
					mouseEvent.getX(), mouseEvent.getY(), root, AbstractDiagram.class);
			if (mouseEvent.isShiftDown()) {
				endDiagramContainer = lastDiagramHandle;
				if (startDiagramContainer != null && endDiagramContainer != null
						&& !startDiagramContainer.equals(endDiagramContainer)
						&& !startDiagramContainer.equals(endDiagramContainer.getNext())
						&& !(startDiagramContainer instanceof EndDiagram)) {
					if (startDiagramContainer.getNext() == null) {
						// Cria Arrow
						if (!existsConnection(startDiagramContainer, endDiagramContainer)) {
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
			removeLineTemporary();

			root.setCursor(Cursor.DEFAULT);
		});

		root.setOnMouseClicked(mouseEvent -> {
			if (!mouseEvent.isControlDown()) {
				// Limpo qualquer sele��o anterior
				unSelectElement();
			}

			// Seleciono o Elemento atual das coordenadas XY
			Selectable element = paneHelper.getSpecificNodeFromCoordinatesXY(mouseEvent.getX(),
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
							for (Connection connection : paneHelper.getConnectionFromPane(root)) {
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

	private void correctBordersIfNecessary(final MouseEvent mouseEvent,
			final AbstractDiagram diagram) {
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

				root.setCursor(Cursor.DEFAULT);
			}
		}
	}

	private void openForm() {
		try {
			// Carrega
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("../view/ui/form.fxml"));

			// D� ao controlador acesso � the main app.
			FormController controller = loader.getController();
			AnchorPane anchorPane;
			anchorPane = (AnchorPane) loader.load();

			// Cria o palco dialogStage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Register Input Parameters");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(root.getScene().getWindow());
			Scene scene = new Scene(anchorPane);
			dialogStage.setScene(scene);

			// Mostra a janela e espera at� o usu�rio fechar.
			dialogStage.showAndWait();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean startOrEndExists(final Class clazz) {
		boolean result = false;
		ObservableList<Node> nodesContainerMain = root.getChildren();
		for (Node node : nodesContainerMain) {
			if (node instanceof AbstractDiagram) {
				AbstractDiagram diagramContainer = (AbstractDiagram) node;

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

	private void changeConnection(final AbstractDiagram lastDiagramHandled) {

		if (lastDiagramHandled != null) {

			List<Connection> allConnections = paneHelper.getConnectionFromPane(root);

			for (Connection connection : allConnections) {
				if (connection.getStartDiagramContainer().equals(lastDiagramHandled)
						|| connection.getEndDiagramContainer().equals(lastDiagramHandled)) {

					connection.recalculateXY();

					root.getChildren().remove(connection.getArrow());
					Arrow arrow = new Arrow(connection);
					connection.setArrow(arrow);
					root.getChildren().add(connection.getArrow());
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

	private boolean existsConnection(final AbstractDiagram startDiagramContainer,
			final AbstractDiagram endDiagramContainer) {
		boolean result = false;
		List<Connection> connections = paneHelper.getConnectionFromPane(root);
		for (Connection connection : connections) {
			result = connection.getStartDiagramContainer().equals(startDiagramContainer)
					&& connection.getEndDiagramContainer().equals(endDiagramContainer);
			break;

		}
		return result;
	}

	private void createConnection(final AbstractDiagram startDiagramContainer,
			final AbstractDiagram endDiagramContainer) {
		Connection conn = new Connection(startDiagramContainer, endDiagramContainer);

		root.getChildren().add(conn);
		root.getChildren().add(conn.getArrow());
	}

}