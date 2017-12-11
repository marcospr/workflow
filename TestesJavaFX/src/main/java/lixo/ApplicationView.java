package lixo;

import org.springframework.beans.factory.annotation.Autowired;

import br.unicamp.cepetro.unisim.mero.ui.app.controller.MainController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationView {
	// private Path path;
	private Scene scene;
	private Stage stage;
	@Autowired
	private MainController mainController;

	public ApplicationView(final Stage stage, final Scene scene) {
		super();
		this.stage = stage;
		this.scene = scene;
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
}
