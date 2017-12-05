package lixo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unicamp.cepetro.unisim.mero.gui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.gui.app.command.CommandAQNS;
import br.unicamp.cepetro.unisim.mero.gui.app.command.CommandDS;
import br.unicamp.cepetro.unisim.mero.gui.app.command.CommandGAS;
import br.unicamp.cepetro.unisim.mero.gui.app.command.CommandGU;
import br.unicamp.cepetro.unisim.mero.gui.app.command.CommandHLDG;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.AbstractDiagram;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Arrow;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Connection;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.End;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.EndDiagram;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.LineTemporary;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.ProcessDiagram;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Processo;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Selectable;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Start;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.StartDiagram;
import br.unicamp.cepetro.unisim.mero.gui.app.factory.CommandFactory;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CommandType;
import br.unicamp.cepetro.unisim.mero.gui.app.model.ConstantsSystem;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesMouseXY;
import br.unicamp.cepetro.unisim.mero.gui.app.view.FormController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootContainerBackup extends BorderPane {
	private CoordinatesMouseXY coordinatesMouse;
	// private Processo processoHelper;
	private AbstractDiagram startDiagramContainer;
	private AbstractDiagram endDiagramContainer;
	private ProgressBar barra;
	private Label status;
	private CommandFactory commandFactory;
	// private Start start;
	private Set<Selectable> selectDiagram = new HashSet<>();
	private EventHandler<? super MouseEvent> handleButtonToolbarOnMousePressed;
	private EventHandler<? super MouseEvent> handleButtonToolbarOnMouseDragged;
	private EventHandler<? super MouseEvent> handleButtonToolbarOnMouseReleased;
	private EventHandler<? super MouseEvent> handleProcessOnMouseClicked;

	public RootContainerBackup() {
		VBox topContainer = new VBox();
		buildMenu(topContainer);
		buildToolbar(topContainer);
		setTop(topContainer);
		coordinatesMouse = new CoordinatesMouseXY();
		commandFactory = new CommandFactory();
	}

	private void buildMenu(final VBox topContainer) {
		MenuBar mainMenu = new MenuBar();

		topContainer.getChildren().add(mainMenu);

		Menu file = new Menu("File");
		Menu edit = new Menu("Edit");
		Menu help = new Menu("Help");

		// Create and add the "File" sub-menu options.
		MenuItem openFile = new MenuItem("Open File");
		MenuItem exitApp = new MenuItem("Exit");
		file.getItems().addAll(openFile, exitApp);

		// Create and add the "Edit" sub-menu options.
		MenuItem preferences = new MenuItem("Preferences");
		edit.getItems().add(preferences);

		// Create and add the "Help" sub-menu options.
		MenuItem about = new MenuItem("About");
		help.getItems().add(about);

		mainMenu.getMenus().addAll(file, edit, help);

	}

	public ToolBar getToolbar() {
		List<ToolBar> toolBars = null;
		List<VBox> vboxes = getNodeGroupFromPane(VBox.class, this);
		if (vboxes != null) {
			toolBars = getNodeGroupFromPane(ToolBar.class, vboxes.get(0));
		}

		return toolBars != null ? toolBars.get(0) : null;

	}

	public <T> List<T> getNodeGroupFromPane(final Class<T> clazz, final Pane pane) {
		List<T> result = null;
		for (Node node : pane.getChildren()) {
			if (node.getClass().isAssignableFrom(clazz)) {
				result.add((T) node);
			}
		}
		return result;
	}

	private void buildToolbar(final VBox topContainer) {
		createToolbarHandlers();
		Image imageProcess = new Image(getClass().getResourceAsStream("/resources/images/rounded.png"));
		Image imageStart = new Image(getClass().getResourceAsStream("/resources/images/start.png"));
		Image imageEnd = new Image(getClass().getResourceAsStream("/resources/images/end.png"));
		Image imageArrow = new Image(getClass().getResourceAsStream("/resources/images/arrow.png"));
		Image imageCheck = new Image(getClass().getResourceAsStream("/resources/images/check.png"));

		ToolBar toolBar1 = new ToolBar();
		toolBar1.getItems().add(new Separator());
		Command[] commands =
				{new CommandHLDG(), new CommandGAS(), new CommandDS(), new CommandGU(), new CommandAQNS()};
		for (Command command : commands) {
			StackPane buttonStackPane = createButtonStackPane(command);
			Button btn = new Button(null, buttonStackPane);
			btn.setContentDisplay(ContentDisplay.BOTTOM);
			// btn.addEventHandler(MouseEvent.MOUSE_PRESSED, handleButtonToolbarOnMousePressed);
			btn.setOnMousePressed(handleButtonToolbarOnMousePressed);
			btn.setOnMouseDragged(handleButtonToolbarOnMouseDragged);
			btn.setOnMouseReleased(handleButtonToolbarOnMouseReleased);
			toolBar1.getItems().add(btn);
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
		toolBar1.getItems().addAll(new Separator(), btnStart, btnEnd, new Separator(), btnRun);
		topContainer.getChildren().add(toolBar1);

		setBottom(createBarrasStatus());

		// Handlers Button Execute
		btnRun.setOnMousePressed(mouseEvent -> {
			AbstractDiagram start = getStartFromContainer();
			if (start != null) {
				unSelectElement();
				start.execute(barra);
			}
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

				Text texto = new Text("Start");
				texto.fillProperty().set(Color.INDIANRED);
				texto.setTextAlignment(TextAlignment.CENTER);
				texto.setStyle("-fx-font-weight: bold;");

				startDiagramContainer = new StartDiagram(start, mouseEvent.getSceneX(),
						mouseEvent.getSceneY(), commandFactory.createCommand(CommandType.Start));

				// texto.setTranslateY(startDiagramContainer.getLayoutY() + 25);

				startDiagramContainer.getChildren().add(texto);
				getChildren().add(startDiagramContainer);
			} else {
				startDiagramContainer = null;
			}
		});

		btnStart.setOnMouseDragged(mouseEvent -> {
			if (startDiagramContainer != null) {
				((Node) startDiagramContainer).setTranslateX(mouseEvent.getSceneX());
				((Node) startDiagramContainer).setTranslateY(mouseEvent.getSceneY());
			}
		});

		btnStart.setOnMouseReleased(mouseEvent -> {
			correctBordersIfNecessary(mouseEvent, startDiagramContainer);
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
				Text texto = new Text("End");
				texto.fillProperty().set(Color.INDIANRED);
				texto.setTextAlignment(TextAlignment.CENTER);
				texto.setStyle("-fx-font-weight: bold;");

				startDiagramContainer = new EndDiagram(end, mouseEvent.getSceneX(), mouseEvent.getSceneY(),
						commandFactory.createCommand(CommandType.End));

				// texto.setTranslateY(startDiagramContainer.getLayoutY() + 25);
				startDiagramContainer.getChildren().add(texto);
				getChildren().add(startDiagramContainer);
			} else {
				startDiagramContainer = null;
			}

		});

		btnEnd.setOnMouseDragged(mouseEvent -> {
			if (startDiagramContainer != null) {
				((Node) startDiagramContainer).setTranslateX(mouseEvent.getSceneX());
				((Node) startDiagramContainer).setTranslateY(mouseEvent.getSceneY());
			}
		});

		btnEnd.setOnMouseReleased(mouseEvent -> {
			correctBordersIfNecessary(mouseEvent, startDiagramContainer);
			setCursor(Cursor.DEFAULT);
		});

		// handlers Pane
		setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			AbstractDiagram diagram = getDiagramFromCoordinatesXY(mouseEvent);
			if (isContainer(diagram)) {
				startDiagramContainer = diagram;
				endDiagramContainer = null;
			}
			if (!mouseEvent.isControlDown()) {
				unSelectElement();
				selectElement(diagram);
			}

		});

		setOnMouseDragged(mouseEvent -> {
			AbstractDiagram lastProcessHandled = getDiagramFromCoordinatesXY(mouseEvent);
			if (lastProcessHandled != null) {
				changeConnection(lastProcessHandled);
			} else {
				// Trata-se de nova conex�o
				createLineTemporary(mouseEvent);
			}
		});

		setOnMouseReleased(mouseEvent -> {
			AbstractDiagram lastDiagramHandle = getDiagramFromCoordinatesXY(mouseEvent);
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

			setCursor(Cursor.DEFAULT);
		});

		setOnMouseClicked(mouseEvent -> {
			if (!mouseEvent.isControlDown()) {
				// Limpo qualquer sele��o anterior
				unSelectElement();
			}

			// Seleciono o Elemento atual das coordenadas XY
			Selectable element = getSelectableFromCoordinatesXY(mouseEvent);
			if (element != null) {
				selectElement(element);
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

	}

	private HBox createBarrasStatus() {
		barra = new ProgressBar();
		barra.setVisible(false);
		status = new Label();
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(status, barra);
		return null;
	}

	private AbstractDiagram getStartFromContainer() {
		ObservableList<Node> nodes = getChildren();
		StartDiagram start = null;
		for (Node node : nodes) {
			if (node instanceof StartDiagram) {
				start = (StartDiagram) node;
			}
		}
		return start;
	}

	private void createLineTemporary(final MouseEvent mouseEvent) {
		removeLineTemporary();
		LineTemporary line = null;
		if (startDiagramContainer != null) {
			line = new LineTemporary(startDiagramContainer.getTranslateX(),
					startDiagramContainer.getTranslateY(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
			getChildren().add(0, line);
		}
	}

	private void removeLineTemporary() {
		// Node nodeToRemove = null;
		ObservableList<Node> nodes = getChildren();
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

	private void openForm() {
		try {
			// Carrega
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("../view/ui/form.fxml"));

			// D� ao controlador acesso � the main app.
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

			// Mostra a janela e espera at� o usu�rio fechar.
			dialogStage.showAndWait();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createToolbarHandlers() {
		// Handlers Button Process

		handleProcessOnMouseClicked = mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				if (mouseEvent.getClickCount() == 2) {
					openForm();
				}
			}
		};

		handleButtonToolbarOnMousePressed = mouseEvent -> {
			setCursor(Cursor.HAND);

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

			// commandFactory.createCommand(CommandType()));
			CommandType commandType = Enum.valueOf(CommandType.class, text.getText());

			startDiagramContainer = new ProcessDiagram(process, mouseEvent.getSceneX(),
					mouseEvent.getSceneY(), commandFactory.createCommand(commandType));

			Image imageGear = new Image(getClass().getResourceAsStream("../resources/gear.png"));
			ImageView imageViewGear = new ImageView(imageGear);

			texto.setTranslateX(startDiagramContainer.getLayoutX() + 20);
			imageViewGear.setTranslateX(startDiagramContainer.getLayoutX() - 20);

			startDiagramContainer.getChildren().addAll(texto, imageViewGear);

			startDiagramContainer.setOnMouseClicked(handleProcessOnMouseClicked);

			getChildren().add(startDiagramContainer);
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
			setCursor(Cursor.DEFAULT);
		};
	}

	private StackPane createButtonStackPane(final Command command) {
		Rectangle rectangle = new Rectangle(60, 40, Color.WHITE);
		rectangle.setStroke(Color.GREY);
		rectangle.setStrokeWidth(1.0);
		rectangle.setArcWidth(20);
		rectangle.setArcHeight(20);
		StackPane.setMargin(rectangle, new Insets(3));

		StackPane stackPane = new StackPane();
		Text label = new Text(command.getName());
		label.setStyle("-fx-font-weight: bold;");
		label.fillProperty().set(Color.INDIANRED);
		stackPane.getChildren().addAll(rectangle, label);

		return stackPane;
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

	private void unSelectElement() {
		for (Selectable selectable : selectDiagram) {
			selectable.unSelect();
		}
		selectDiagram = new HashSet<>();
	}

	private void selectElement(final Selectable selectable) {
		if (selectable != null) {
			selectable.select();
			selectDiagram.add(selectable);
		}
	}

	private void createConnection(final AbstractDiagram startDiagramContainer,
			final AbstractDiagram endDiagramContainer) {
		Connection conn = new Connection(startDiagramContainer, endDiagramContainer);

		getChildren().add(conn);
		getChildren().add(conn.getArrow());
	}

	private void changeConnection(final AbstractDiagram lastDiagramHandled) {

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

	private boolean existsConnection(final AbstractDiagram startDiagramContainer,
			final AbstractDiagram endDiagramContainer) {
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

	public Selectable getSelectableFromCoordinatesXY(final MouseEvent mouseEvent) {
		Selectable result = null;
		ObservableList<Node> nodes = getChildren();

		for (Node node : nodes) {
			if (node instanceof Selectable) {
				Bounds bounds = node.getBoundsInParent();
				// verifica coordenadas do mouse
				if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
						&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
						&& mouseEvent.getY() >= bounds.getMinY()) {

					result = (Selectable) node;
					break;
				}
			}
		}
		return result;
	}

	public AbstractDiagram getDiagramFromCoordinatesXY(final MouseEvent mouseEvent) {
		AbstractDiagram diagramResult = null;
		ObservableList<Node> nodes = getChildren();
		// List<Node> nodesFound = new ArrayList<>();

		for (Node node : nodes) {
			if (node instanceof AbstractDiagram) {
				Bounds bounds = node.getBoundsInParent();
				// verifica coordenadas do mouse
				if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
						&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
						&& mouseEvent.getY() >= bounds.getMinY()) {

					diagramResult = (AbstractDiagram) node;
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

	private Boolean isContainer(final Node node) {
		return node instanceof AbstractDiagram;
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

				setCursor(Cursor.DEFAULT);
			}
		}
	}

}
