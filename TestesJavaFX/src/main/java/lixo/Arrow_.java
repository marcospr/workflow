package lixo;

import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Connection;
import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Selectable;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesXYInitialFinal;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Arrow_ extends Polygon implements Selectable {
	private Boolean select;

	public Arrow_(final Connection connection) {
		setStroke(Color.GREY);
		select = Boolean.FALSE;
		CoordinatesXYInitialFinal coordinates = new CoordinatesXYInitialFinal();
		double startX = 0;
		double startY = 0;
		double endx = 0;
		double endy = 0;

		startX = connection.getStartX();
		startY = connection.getStartY();
		endx = connection.getEndX();
		endy = connection.getEndY();
		coordinates.setInitialX(startX);
		coordinates.setInitialY(startY);
		coordinates.setFinalX(endx);
		coordinates.setFinalY(endy);

		setTranslateX(endx);
		setTranslateY(endy);

		setRotate(getCalculatedRotation(coordinates));

	}

	private double getCalculatedRotation(final CoordinatesXYInitialFinal coordinates) {
		return getCalculatedAngle(coordinates) - 90;

	}

	private double getCalculatedAngle(final CoordinatesXYInitialFinal coordinates) {
		double startX = coordinates.getInitialX();
		double startY = coordinates.getInitialY();
		double endx = coordinates.getFinalX();
		double endy = coordinates.getFinalY();

		getPoints().addAll(new Double[] {0.0, 5.0, -5.0, -5.0, 5.0, -5.0});

		return Math.atan2(endy - startY, endx - startX) * 180 / Math.PI;

	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	@Override
	public void select() {
		setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		setSelect(Boolean.TRUE);
	}

	@Override
	public void unSelect() {
		setEffect(null);
		setSelect(Boolean.FALSE);
	}

}
