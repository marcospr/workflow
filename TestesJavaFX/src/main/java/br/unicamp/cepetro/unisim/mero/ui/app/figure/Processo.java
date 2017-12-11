package br.unicamp.cepetro.unisim.mero.ui.app.figure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.unicamp.cepetro.unisim.mero.ui.app.model.ConstantsSystem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@Component
public class Processo extends Rectangle {

	public Processo() {
		super(ConstantsSystem.WIDTH_RECTANGLE, ConstantsSystem.HEIGHT_RECTANGLE);
		defineProcess();
	}

	public Processo(final double width, final double height) {
		super(width, height);
		defineProcess();
	}

	private void defineProcess() {
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
