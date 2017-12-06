package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import br.unicamp.cepetro.unisim.mero.gui.app.model.ConstantsSystem;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesXY;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

public class AbstractLine extends CubicCurve implements Selectable, ConnectionPoints {
	protected Boolean select;

	public AbstractLine() {}

	public AbstractLine(final double startX, final double startY, final double endX,
			final double endY) {

		super(startX, startY, startX + ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE, startY,
				endX - ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE, endY, endX, endY);
		setStroke(Color.FORESTGREEN);
		setStrokeWidth(2);
		setStrokeLineCap(StrokeLineCap.ROUND);
		setFill(Color.TRANSPARENT);
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

	@Override
	public CoordinatesXY getPoint0() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMinX(), bounds.getMinY() + bounds.getHeight() / 2,
				INDEX_POINT_ZERO);
	}

	@Override
	public CoordinatesXY getPoint1() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMinX() + bounds.getWidth() / 2, bounds.getMaxY(),
				INDEX_POINT_ONE);

	}

	@Override
	public CoordinatesXY getPoint2() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMaxX(), bounds.getMinY() + bounds.getHeight() / 2,
				INDEX_POINT_TWO);
	}

	@Override
	public CoordinatesXY getPoint3() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMinX() + bounds.getWidth() / 2, bounds.getMinY(),
				INDEX_POINT_THREE);
	}
}
