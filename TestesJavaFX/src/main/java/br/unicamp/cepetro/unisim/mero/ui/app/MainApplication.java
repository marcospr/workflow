package br.unicamp.cepetro.unisim.mero.ui.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MainApplication extends Application {
	private Parent root;
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		root = (Parent) loader.load("view/FormMain.fxml");

		// Grid de fundo
		BackgroundImage myBI = new BackgroundImage(
				new Image(getClass().getResourceAsStream("/images/grid_back.jpg"), 0, 0, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		((Region) root).setBackground(new Background(myBI));
	}

	@Override
	public void start(final Stage primaryStage) throws IOException {
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MERO - Technical Workflow");
		primaryStage.show();
	}

}
