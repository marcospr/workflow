package view.builder;

import diagram.RootContainer;
import javafx.scene.Scene;
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
