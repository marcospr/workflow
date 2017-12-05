package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import javafx.scene.paint.Color;

public class LineTemporary extends AbstractLine {

	public LineTemporary(final double startX, final double startY, final double endX,
			final double endY) {
		super(startX, startY, endX, endY);
		setStroke(Color.CORNFLOWERBLUE);
		getStrokeDashArray().addAll(2d, 5d);
	}
}
