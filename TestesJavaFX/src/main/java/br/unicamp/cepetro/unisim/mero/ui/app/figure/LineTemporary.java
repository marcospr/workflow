package br.unicamp.cepetro.unisim.mero.ui.app.figure;

import org.springframework.stereotype.Component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

@Component
public class LineTemporary extends Line {

	public LineTemporary(final double startX, final double startY, final double endX,
			final double endY) {
		super(startX, startY, endX, endY);
		setStroke(Color.CORNFLOWERBLUE);
		getStrokeDashArray().addAll(2d, 5d);
	}

	public LineTemporary() {}
}
