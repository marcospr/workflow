package br.unicamp.cepetro.unisim.mero.ui.app.figure;

import org.springframework.stereotype.Component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

@Component
public class End extends Circle {
	public End(final double x, final double y, final double radius) {
		super(x, y, radius);
		defineBorder();
	}

	public End() {
		defineBorder();
	}

	private void defineBorder() {
		setStrokeWidth(5.0);
		setStroke(Color.GREY);
		setFill(Color.ANTIQUEWHITE);
	}
}
