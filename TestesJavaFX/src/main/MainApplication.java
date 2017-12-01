package main;

import helper.PaneHelper;
import helper.TypeHelper;
import javafx.application.Application;
import javafx.stage.Stage;
import view.builder.ApplicationViewBuilder;
import view.ui.ApplicationView;

public class MainApplication extends Application {
	ApplicationView applicationView;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) {
		ApplicationViewBuilder appViewbuilder =
				new ApplicationViewBuilder(stage, new PaneHelper(new TypeHelper()));
		applicationView = appViewbuilder.builder();

		stage.show();
	}

}
