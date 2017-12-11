package br.unicamp.cepetro.unisim.mero.ui.app.model;

public class CoordinatesXYInitialFinal {
	private Double initialX = new Double(0);
	private Double initialY = new Double(0);
	private Double finalX = new Double(0);
	private Double finalY = new Double(0);
	private int initialtPointIndex;
	private int finalPointIndex;

	public CoordinatesXYInitialFinal(final Double initialX, final Double initialY,
			final int initialtPointIndex, final Double finalX, final Double finalY,
			final int finalPointIndex) {
		super();
		this.initialX = initialX;
		this.initialY = initialY;
		this.setInitialtPointIndex(initialtPointIndex);
		this.finalX = finalX;
		this.finalY = finalY;
		this.setFinalPointIndex(finalPointIndex);
	}

	public CoordinatesXYInitialFinal() {}

	public Double getInitialX() {
		return initialX;
	}

	public void setInitialX(final Double initialX) {
		this.initialX = initialX;
	}

	public Double getInitialY() {
		return initialY;
	}

	public void setInitialY(final Double initialY) {
		this.initialY = initialY;
	}

	public Double getFinalX() {
		return finalX;
	}

	public void setFinalX(final Double finalX) {
		this.finalX = finalX;
	}

	public Double getFinalY() {
		return finalY;
	}

	public void setFinalY(final Double finalY) {
		this.finalY = finalY;
	}

	public int getInitialtPointIndex() {
		return initialtPointIndex;
	}

	public void setInitialtPointIndex(int initialtPointIndex) {
		this.initialtPointIndex = initialtPointIndex;
	}

	public int getFinalPointIndex() {
		return finalPointIndex;
	}

	public void setFinalPointIndex(int finalPointIndex) {
		this.finalPointIndex = finalPointIndex;
	}

}
