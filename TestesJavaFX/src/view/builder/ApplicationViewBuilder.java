package view.builder;

import diagram.RootContainer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import view.ui.ApplicationView;

public class ApplicationViewBuilder {

	private Stage stage;
	private RootContainer rootContainer;

	public ApplicationViewBuilder(final Stage stage) {
		super();
		this.stage = stage;
	}

	public ApplicationView builder() {

		rootContainer = new RootContainer();

		Scene scene = new Scene(rootContainer, 800, 600);

		stage.setScene(scene);

		stage.setTitle("MERO - Workflow Técnico");

		BackgroundImage myBI =
				new BackgroundImage(
						new Image(getClass().getResourceAsStream("../../diagram/grid_back.jpg"), 0, 0, false,
								true),
						BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
		rootContainer.setBackground(new Background(myBI));

		ApplicationView view = new ApplicationView(stage, scene, rootContainer);

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
