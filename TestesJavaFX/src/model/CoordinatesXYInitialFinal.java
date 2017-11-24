package model;

public class CoordinatesXYInitialFinal {
	Double initialX = new Double(0);
	Double initialY = new Double(0);
	Double finalX = new Double(0);
	Double finalY = new Double(0);

	public CoordinatesXYInitialFinal(final Double initialX, final Double initialY, final Double finalX,
			final Double finalY) {
		super();
		this.initialX = initialX;
		this.initialY = initialY;
		this.finalX = finalX;
		this.finalY = finalY;
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

}
