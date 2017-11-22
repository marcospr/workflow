package diagram;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationViewController {
	// private Path path;
	private RootContainer pane;
	private Scene scene;
	private Stage stage;
	private ApplicationView view;

	public ApplicationViewController(final ApplicationView view) {
		this.view = view;
		// path = view.getPath();
		pane = view.getPane();
		scene = view.getScene();
		initialize();
	}

	private void initialize() {

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
