package br.unicamp.cepetro.unisim.mero.gui.app.model;

public class CoordinatesXY {
	private Double x;
	private Double y;

	public CoordinatesXY(final Double x, final Double y) {
		super();
		this.x = x;
		this.y = y;
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

}
