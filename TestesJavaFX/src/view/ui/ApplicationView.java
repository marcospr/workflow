package view.ui;

import diagram.RootContainer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationView {
	// private Path path;
	private RootContainer pane;
	private Scene scene;
	private Stage stage;

	public ApplicationView(final Stage stage, final Scene scene, final RootContainer pane) {
		super();
		this.stage = stage;
		this.scene = scene;
		this.pane = pane;
		// this.path = path;
	}

	public ApplicationView() {}

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
