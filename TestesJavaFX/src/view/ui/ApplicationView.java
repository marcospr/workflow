package view.ui;

import diagram.Connection;
import diagram.RootContainer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ApplicationView {
	// private Path path;
	private RootContainer pane;
	private Line connection;
	private Scene scene;
	private Stage stage;

	public ApplicationView(final Stage stage, final Scene scene, final RootContainer pane) {
		super();
		this.stage = stage;
		this.scene = scene;
		this.pane = pane;
		// this.path = path;
	}

	public ApplicationView() {}

	public void addAllPane(final Node... elements) {
		for (Node node : elements) {
			if (node instanceof Connection) {
				pane.getChildren().add(0, node);
			} else {
				pane.getChildren().add(node);
			}
		}
	}

	public RootContainer getPane() {
		return pane;
	}

	public void setPane(final RootContainer pane) {
		this.pane = pane;
	}

	public Line getConnection() {
		return connection;
	}

	public void setConnection(final Line connection) {
		this.connection = connection;
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
}
