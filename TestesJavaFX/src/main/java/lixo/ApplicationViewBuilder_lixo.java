package lixo;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class ApplicationViewBuilder_lixo {

	private Stage stage;
	private RootPane rootPane;

	public ApplicationViewBuilder_lixo(final Stage stage) {
		super();
		this.stage = stage;
	}

	public ApplicationView builder() {

		rootPane = new RootPane();

		Scene scene = new Scene(rootPane, 800, 600);

		stage.setScene(scene);

		stage.setTitle("MERO - Workflow Técnico");

		BackgroundImage myBI = new BackgroundImage(
				new Image(getClass().getResourceAsStream("/resources/images/grid_back.jpg"), 0, 0, false,
						true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		rootPane.setBackground(new Background(myBI));

		ApplicationView view = new ApplicationView(stage, scene);

		return view;
	}

	// private Path createPath() {
	// Path path = new Path();
	// path.setStrokeWidth(1);
	// path.setStroke(Color.BLACK);
	// return path;
	// }

	public Stage getStage() {
		return stage;
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}

}
