package view.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FormController {
	@FXML
	public Button closeButton;
	@FXML
	private TableView<String> tableViewParameters;
	@FXML
	private TableColumn<String, String> keyTableColumn;
	@FXML
	private TableColumn<String, String> valueTableColumn;

	@FXML
	private void initialize() {

	}

	@FXML
	public void handleCloseButtonAction(final ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

}
