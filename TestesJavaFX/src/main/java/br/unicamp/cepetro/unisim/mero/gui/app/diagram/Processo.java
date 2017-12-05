package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import java.util.UUID;

import br.unicamp.cepetro.unisim.mero.gui.app.model.ConstantsSystem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Processo extends Rectangle {

	public Processo() {
		super(ConstantsSystem.WIDTH_RECTANGLE, ConstantsSystem.HEIGHT_RECTANGLE);
		setId(UUID.randomUUID().toString());
		// preenchimento
		setFill(Color.WHITE);
		// borda
		setStrokeWidth(2.0);

		setArcWidth(20);
		setArcHeight(20);

		setStroke(Color.GREY);
		setFill(Color.ANTIQUEWHITE);
	}

}
