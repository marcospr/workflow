package view.ui;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Parameter;

public class FormController {
	@FXML
	public Button closeButton;
	@FXML
	private TableView<Parameter> tableViewParameters;
	@FXML
	private TableColumn<String, String> keyTableColumn;
	@FXML
	private TableColumn<String, String> valueTableColumn;

	@FXML
	private void initialize() {
		List<Parameter> parameters = Arrays.asList(new Parameter("PARAM1", "123"), new Parameter("PARAM2", "0.8"));

		keyTableColumn = new TableColumn<>("key");
		valueTableColumn = new TableColumn<>("value");

		keyTableColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
		valueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

		tableViewParameters.setItems(FXCollections.observableArrayList(parameters));
		// tableViewParameters.getColumns().add(keyTableColumn, valueTableColumn);

	}

	@FXML
	public void handleCloseButtonAction(final ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

}
