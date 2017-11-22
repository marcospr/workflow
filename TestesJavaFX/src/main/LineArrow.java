package main;

import diagram.ApplicationView;
import diagram.ApplicationViewBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class LineArrow extends Application {
	ApplicationView applicationView;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) {
		ApplicationViewBuilder appViewbuilder = new ApplicationViewBuilder(stage);
		applicationView = appViewbuilder.builder();

		// Image image = new Image(getClass().getResourceAsStream("../diagram/rounded.png"));
		// Rectangle rekt = new Rectangle(100, 100);
		// ImagePattern imagePattern = new ImagePattern(image);
		// rekt.setFill(imagePattern);
		// applicationView.getPane().getChildren().add(rekt);

		stage.show();
	}

}
