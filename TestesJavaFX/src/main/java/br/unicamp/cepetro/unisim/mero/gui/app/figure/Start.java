package br.unicamp.cepetro.unisim.mero.gui.app.figure;

import org.springframework.stereotype.Component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

@Component
public class Start extends Circle {

	public Start(final double x, final double y, final double radius) {
		super(x, y, radius);
		defineBorda();
	}

	public Start() {
		defineBorda();
	}

	private void defineBorda() {
		// borda
		setStroke(Color.GREY);
		setFill(Color.ANTIQUEWHITE);
		setStrokeWidth(2.0);
	}

}
