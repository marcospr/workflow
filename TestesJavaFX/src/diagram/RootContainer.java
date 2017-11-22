package diagram;

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
	private Processo processoHelper;
	private Processo startProcess;
	private Processo endProcess;
	private Node nodeSelect;

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
		// Handlers Button Arrow

		// Handlers Button Process
		btnProcess.setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(mouseEvent.getSceneX());
			coordinatesMouse.setStartTranslateY(mouseEvent.getSceneY());

			ProcessoBuilder processoBuilder = new ProcessoBuilder();
			processoHelper = processoBuilder.comTranslateX(coordinatesMouse.getStartTranslateX())
					.comTranslateY(coordinatesMouse.getStartTranslateY()).builder();
			getChildren().add(processoHelper);

		});

		btnProcess.setOnMouseDragged(mouseEvent -> {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			processoHelper.setTranslateX(endTranslateX);
			processoHelper.setTranslateY(endTranslateY);
		});

		btnProcess.setOnMouseReleased(mouseEvent -> {
			double mouseSceneX = mouseEvent.getSceneX();
			double mouseSceneY = mouseEvent.getSceneY();

			if (mouseSceneX < 0) {
				mouseSceneX = 1;
				processoHelper.setTranslateX(mouseSceneX);
			}

			if (mouseSceneY < 0) {
				mouseSceneY = 1;
				processoHelper.setTranslateY(mouseSceneY);
			}

			if (mouseSceneX > ConstantsSystem.WIDTH_SCENE) {
				mouseSceneX = ConstantsSystem.WIDTH_SCENE - ConstantsSystem.WIDTH_RECTANGLE;
				processoHelper.setTranslateX(mouseSceneX);
			}

			if (mouseSceneY > ConstantsSystem.HEIGHT_SCENE) {
				mouseSceneY = ConstantsSystem.HEIGHT_SCENE - ConstantsSystem.HEIGHT_RECTANGLE;
				processoHelper.setTranslateY(mouseSceneY);
			}
			setCursor(Cursor.DEFAULT);

		});

		// handlers Pane
		setOnMousePressed(mouseEvent -> {
			setCursor(Cursor.HAND);
			startProcess = getProcessoFromCoordinatesXY(mouseEvent);
			endProcess = null;
		});

		setOnMouseReleased(mouseEvent -> {
			endProcess = getProcessoFromCoordinatesXY(mouseEvent);

			if (startProcess != null && endProcess != null) {
				Connection conn = new Connection(startProcess, endProcess);
				getChildren().add(0, conn);
			}
			setCursor(Cursor.DEFAULT);
		});

		setOnMouseClicked(mouseEvent -> {
			ObservableList<Node> nodes = getChildren();
			for (Node node : nodes) {
				if (node instanceof Selectable) {
					Selectable s = (Selectable) node;
					if (s.isSelect()) {
						nodeSelect = null;
						node.setEffect(null);
						s.setSelect(Boolean.FALSE);
						break;
					}
				}
			}

			Node node = getNodeFromCoordinatesXY(mouseEvent);
			if (node != null) {
				if (node instanceof Selectable) {
					node.setEffect(new DropShadow(20, Color.DEEPSKYBLUE));
					((Selectable) node).setSelect(Boolean.TRUE);
					nodeSelect = node;
				}
			}
		});

		setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				getChildren().remove(nodeSelect);
			}
		});
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

	public Node getNodeFromCoordinatesXY(final MouseEvent mouseEvent) {

		ObservableList<Node> nodes = getChildren();

		for (Node node : nodes) {
			Bounds bounds = node.getBoundsInParent();
			if (bounds != null && mouseEvent.getX() <= bounds.getMaxX()
					&& mouseEvent.getY() <= bounds.getMaxY() && mouseEvent.getX() >= bounds.getMinX()
					&& mouseEvent.getY() >= bounds.getMinY()) {

				return node;
			}
		}
		return null;
	}

	public Bounds getBoundsFrom(final Rectangle rectangle) {
		Bounds bounds = rectangle.getBoundsInParent();
		return bounds;
	}
}
