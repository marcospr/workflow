package view.ui;

import controller.MainController;
import diagram.RootContainer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationView {
	// private Path path;
	private RootContainer pane;
	private Scene scene;
	private Stage stage;
	private MainController mainController;

	public ApplicationView(final Stage stage, final Scene scene, final RootContainer pane) {
		super();
		this.stage = stage;
		this.scene = scene;
		this.pane = pane;
	}

	public ApplicationView(final Stage stage, final Scene scene, final RootContainer pane,
			final MainController mainController) {
		this.stage = stage;
		this.scene = scene;
		this.pane = pane;
		this.mainController = mainController;
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

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(final MainController mainController) {
		this.mainController = mainController;
	}
}
