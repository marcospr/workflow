package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import br.unicamp.cepetro.unisim.mero.gui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.gui.app.figure.Processo;
import br.unicamp.cepetro.unisim.mero.gui.app.model.ConstantsSystem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProcessDiagram extends Diagram {
	private static final int POSITION_DIFFERENCE = 20;
	private Processo process;

	private ImageView imageView;
	private Text text;

	public ProcessDiagram() {}

	public ProcessDiagram(final double x, final double y, final Command command, final String texto) {
		super(x, y, command);
		text = new Text(texto);

		createImageView();
		createText();
		createProcess();
		getChildren().addAll(process, imageView, text);

	}

	private void createProcess() {
		process = new Processo();
	}

	private void createImageView() {
		Image imageGear =
				new Image(getClass().getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("gear.png")));
		imageView = new ImageView(imageGear);
		imageView.setTranslateX(getLayoutX() - POSITION_DIFFERENCE);
	}

	private void createText() {
		text.setTranslateX(getLayoutX() + POSITION_DIFFERENCE);
		text.fillProperty().set(Color.INDIANRED);
		text.setStyle("-fx-font-weight: bold;");
	}

}
