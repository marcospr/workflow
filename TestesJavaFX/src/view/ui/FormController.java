package view.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Parameter;

public class FormController {

	@FXML
	public Button closeButton;
	@FXML
	private TableView<Parameter> tableViewParameters;
	@FXML
	private TableColumn<Parameter, String> nameTableColumn;
	@FXML
	private TableColumn<Parameter, String> valueTableColumn;

	@FXML
	private TableView<File> tableViewFiles;

	@FXML
	private TableColumn<File, String> tableColumnFileName;

	@FXML
	private TextField tfValueParamater;

	@FXML
	private Button buttonAddParameter;

	@FXML
	private TextField tfParameter;

	@FXML
	private Button buttonChooseFile;

	@FXML
	private Button buttonRemoveFile;

	@FXML
	private void initialize() {
		List<Parameter> parameters =
				Arrays.asList(new Parameter("PARAM1", "123"), new Parameter("PARAM2", "0.8"));

		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Parameter, String>("name"));
		valueTableColumn.setCellValueFactory(new PropertyValueFactory<Parameter, String>("value"));

		tableViewParameters.setItems(FXCollections.observableArrayList(parameters));

		// tableViewParameters.getColumns().addAll(nameTableColumn, valueTableColumn);

		tableColumnFileName.setCellValueFactory(new PropertyValueFactory<File, String>("name"));

	}

	@FXML
	public void handleChooseFileAction(final ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();

		final FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		List<File> list = fileChooser.showOpenMultipleDialog(stage);
		if (list != null) {
			List<File> files = new ArrayList<>();
			for (File file : list) {
				files.add(file);
			}
			tableViewFiles.setItems(FXCollections.observableArrayList(files));

		}

	}

	@FXML
	public void handleRemoveFileButtonAction(final ActionEvent event) {
		if (tableViewFiles.getItems() != null && !tableViewFiles.getItems().isEmpty()) {
			File selectedItem = tableViewFiles.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				tableViewFiles.getItems().remove(selectedItem);
			}
		}

	}

	@FXML
	public void handleCloseButtonAction(final ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View Pictures");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("DAT", "*.dat"),
				new FileChooser.ExtensionFilter("INC", "*.inc"),
				new FileChooser.ExtensionFilter("MERO", "*.mero"));
	}

}
