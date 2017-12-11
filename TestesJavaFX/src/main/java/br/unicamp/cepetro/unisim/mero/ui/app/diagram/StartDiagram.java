package br.unicamp.cepetro.unisim.mero.ui.app.diagram;

import br.unicamp.cepetro.unisim.mero.ui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.ui.app.figure.Start;
import br.unicamp.cepetro.unisim.mero.ui.app.model.ConstantsSystem;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class StartDiagram extends Diagram {
	private Start start;
	private Text texto;

	public StartDiagram() {}

	public StartDiagram(final double x, final double y, final Command command) {
		super(x, y, command);
		createStart(x, y);
		createText();
		getChildren().addAll(start, texto);
	}

	private void createText() {
		texto = new Text("Start");
		texto.fillProperty().set(Color.INDIANRED);
		texto.setTextAlignment(TextAlignment.CENTER);
		texto.setStyle("-fx-font-weight: bold;");
	}

	private void createStart(final double x, final double y) {
		start = new Start();
		start.setCenterX(x);
		start.setCenterY(y);
		start.setRadius(ConstantsSystem.RADIUS);
	}

}
