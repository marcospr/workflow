package diagram;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

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

		ApplicationView view = new ApplicationView(stage, scene, rootContainer, createPath());

		// create rectangle

		ProcessoBuilder processoBuilder = new ProcessoBuilder();
		Processo p1 = processoBuilder.comTranslateX(1).comTranslateY(90).builder();
		Processo p2 = processoBuilder.comTranslateX(1).comTranslateY(180).builder();

		Connection conn = new Connection(p1, p2);
		view.addAllPane(view.getPath(), p1, p2, conn);

		return view;
	}

	private Path createPath() {
		Path path = new Path();
		path.setStrokeWidth(1);
		path.setStroke(Color.BLACK);
		return path;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}
}
