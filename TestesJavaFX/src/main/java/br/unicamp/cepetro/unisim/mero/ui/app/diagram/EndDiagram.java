package br.unicamp.cepetro.unisim.mero.ui.app.diagram;

import br.unicamp.cepetro.unisim.mero.ui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.ui.app.figure.End;
import br.unicamp.cepetro.unisim.mero.ui.app.model.ConstantsSystem;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class EndDiagram extends Diagram {
	private End end;
	private Text texto;

	public EndDiagram(final double x, final double y, final Command command) {
		super(x, y, command);
		createText();
		createEnd(x, y);
		getChildren().addAll(end, texto);
	}

	public EndDiagram() {}

	private void createText() {
		texto = new Text("End");
		texto.fillProperty().set(Color.INDIANRED);
		texto.setTextAlignment(TextAlignment.CENTER);
		texto.setStyle("-fx-font-weight: bold;");
	}

	private void createEnd(final double x, final double y) {
		end = new End();
		end.setCenterX(x);
		end.setCenterY(y);
		end.setRadius(ConstantsSystem.RADIUS);
	}

}
