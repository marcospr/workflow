package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class End extends Circle {

	public End(final double x, final double y, final double radius) {
		super();
		setCenterX(x);
		setCenterY(y);
		setRadius(radius);
		// borda
		setStrokeWidth(5.0);

		setStroke(Color.GREY);
		setFill(Color.ANTIQUEWHITE);

	}

}
