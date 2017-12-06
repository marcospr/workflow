package br.unicamp.cepetro.unisim.mero.gui.app.model;

public class CoordinatesXY {
	private Double x;
	private Double y;
	private int pointIndex;

	public CoordinatesXY(final Double x, final Double y, final int index) {
		super();
		this.x = x;
		this.y = y;
		pointIndex = index;
	}

	public CoordinatesXY() {}

	public Double getX() {
		return x;
	}

	public void setX(final Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(final Double y) {
		this.y = y;
	}

	public int getPointIndex() {
		return pointIndex;
	}

	public void setPointIndex(final int pointIndex) {
		this.pointIndex = pointIndex;
	}

}
