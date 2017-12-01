package view.builder;

import controller.MainController;
import diagram.RootContainer;
import factory.CommandFactory;
import helper.PaneHelper;
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
	private PaneHelper paneHelper;

	public ApplicationViewBuilder(final Stage stage, final PaneHelper paneHelper) {
		super();
		this.stage = stage;
		this.paneHelper = paneHelper;
	}

	public ApplicationView builder() {

		rootContainer = new RootContainer(paneHelper);

		MainController mainController =
				new MainController(rootContainer, new CommandFactory(), paneHelper);

		Scene scene = new Scene(rootContainer, 800, 600);

		stage.setScene(scene);

		stage.setTitle("MERO - Workflow Técnico");

		BackgroundImage myBI = new BackgroundImage(
				new Image(getClass().getResourceAsStream("../../resources/grid_back.jpg"), 0, 0, false,
						true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		rootContainer.setBackground(new Background(myBI));

		ApplicationView view = new ApplicationView(stage, scene, rootContainer, mainController);

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

	public void setPaneHelper(final PaneHelper paneHelper) {
		this.paneHelper = paneHelper;
	}
}
