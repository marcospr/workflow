package diagram;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class ApplicationViewController {
	private Path path;
	private RootContainer pane;
	private Scene scene;
	private Stage stage;
	private ApplicationView view;

	public ApplicationViewController(final ApplicationView view) {
		this.view = view;
		path = view.getPath();
		pane = view.getPane();
		scene = view.getScene();
		initialize();
	}

	private void initialize() {
		// Handlers Scene
		scene.setOnMouseReleased(sceneOnMouseHandler);
		scene.setOnMousePressed(sceneOnMouseHandler);

	}

	EventHandler<MouseEvent> sceneOnMouseHandler = mouseEvent -> {
		if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
			((Scene) mouseEvent.getSource()).setCursor(Cursor.CROSSHAIR);

		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {

			// // Seta
			// // Polygon arrow = new Polygon();
			// // arrow.getPoints().addAll(new Double[] {0.0, 5.0, -5.0, -5.0, 5.0, -5.0});
			// //
			// // double angle =
			// // Math.atan2(endy.get() - startY.get(), endx.get() - startX.get()) * 180 / 3.14;
			// //
			// // arrow.setRotate(angle - 90);
			// //
			// // arrow.setTranslateX(startX.get());
			// // arrow.setTranslateY(startY.get());
			// //
			// // arrow.setTranslateX(endx.get());
			// // arrow.setTranslateY(endy.get());
			// // pane.getChildren().add(arrow);

			((Scene) mouseEvent.getSource()).setCursor(Cursor.DEFAULT);
		}
	};

	public Path getPath() {
		return path;
	}

	public void setPath(final Path path) {
		this.path = path;
	}

	public RootContainer getPane() {
		return pane;
	}

	public void setPane(final RootContainer pane) {
		this.pane = pane;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(final Scene scene) {
		this.scene = scene;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}
}
