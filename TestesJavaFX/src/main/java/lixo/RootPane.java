package lixo;

import java.util.List;

import org.springframework.stereotype.Component;

import br.unicamp.cepetro.unisim.mero.ui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandAQNS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandDS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandGAS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandGU;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandHLDG;
import br.unicamp.cepetro.unisim.mero.ui.app.helper.PaneHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

@Component
public class RootPane extends BorderPane {
	private ProgressBar barra;
	private Label status;
	private Command[] commands =
			{new CommandHLDG(), new CommandGAS(), new CommandDS(), new CommandGU(), new CommandAQNS()};

	public RootPane() {
		VBox topContainer = new VBox();
		// buildMenu(topContainer);
		buildToolbar(topContainer);
		setTop(topContainer);
		setBottom(createBarrasStatus());

	}

	private void buildToolbar(final VBox topContainer) {
		Image imageStart = new Image(getClass().getResourceAsStream("/images/start.png"));
		Image imageEnd = new Image(getClass().getResourceAsStream("/images/end.png"));
		Image imageArrow = new Image(getClass().getResourceAsStream("/images/arrow.png"));
		Image imageCheck = new Image(getClass().getResourceAsStream("/images/check.png"));

		ToolBar toolBar = new ToolBar();
		toolBar.getItems().add(new Separator());

		for (Command command : commands) {
			StackPane buttonStackPane = createButtonStackPane(command);
			Button btn = new Button(null, buttonStackPane);
			btn.setContentDisplay(ContentDisplay.BOTTOM);
			toolBar.getItems().add(btn);
		}

		Button btnStart = new Button("Start", new ImageView(imageStart));
		btnStart.setId("#btnStart");
		btnStart.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnEnd = new Button("End", new ImageView(imageEnd));
		btnEnd.setId("#btnEnd");
		btnEnd.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnArrow = new Button("SHIT + mouse drag/drop", new ImageView(imageArrow));
		btnArrow.setContentDisplay(ContentDisplay.BOTTOM);

		Button btnRun = new Button("Run", new ImageView(imageCheck));
		btnRun.setId("#btnRun");
		toolBar.getItems().addAll(new Separator(), btnStart, btnEnd, new Separator(), btnRun);
		topContainer.getChildren().add(toolBar);
	}

	private void buildMenu(final VBox topContainer) {
		MenuBar mainMenu = new MenuBar();

		topContainer.getChildren().add(mainMenu);

		// Menu file = new Menu("File");
		// Menu edit = new Menu("Edit");
		// Menu help = new Menu("Help");
		//
		// // Create and add the "File" sub-menu options.
		// MenuItem openFile = new MenuItem("Open File");
		// MenuItem exitApp = new MenuItem("Exit");
		// file.getItems().addAll(openFile, exitApp);
		//
		// // Create and add the "Edit" sub-menu options.
		// MenuItem preferences = new MenuItem("Preferences");
		// edit.getItems().add(preferences);
		//
		// // Create and add the "Help" sub-menu options.
		// MenuItem about = new MenuItem("About");
		// help.getItems().add(about);
		//
		// mainMenu.getMenus().addAll(file, edit, help);

	}

	private HBox createBarrasStatus() {
		barra = new ProgressBar();
		barra.setVisible(false);
		status = new Label();
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(status, barra);
		return null;
	}

	private StackPane createButtonStackPane(final Command command) {
		Rectangle rectangle = new Rectangle(60, 40, Color.WHITE);
		rectangle.setStroke(Color.GREY);
		rectangle.setStrokeWidth(1.0);
		rectangle.setArcWidth(20);
		rectangle.setArcHeight(20);
		StackPane.setMargin(rectangle, new Insets(3));

		StackPane stackPane = new StackPane();
		Text label = new Text(command.getName());
		label.setStyle("-fx-font-weight: bold;");
		label.fillProperty().set(Color.INDIANRED);
		stackPane.getChildren().addAll(rectangle, label);

		return stackPane;
	}

	public ToolBar getToolbar() {
		List<ToolBar> toolBars = null;
		List<VBox> vboxes = PaneHelper.getSpecificNodesFromPane(VBox.class, this);
		if (vboxes != null) {
			toolBars = PaneHelper.getSpecificNodesFromPane(ToolBar.class, vboxes.get(0));
		}

		return toolBars != null ? toolBars.get(0) : null;
	}

}
