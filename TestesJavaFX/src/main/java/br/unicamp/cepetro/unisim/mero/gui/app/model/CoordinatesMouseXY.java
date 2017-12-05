package br.unicamp.cepetro.unisim.mero.gui.app.model;

import org.springframework.stereotype.Component;

@Component
public class CoordinatesMouseXY {
	private double startSceneX, startSceneY;
	private double startTranslateX, startTranslateY;

	public double getStartSceneX() {
		return startSceneX;
	}

	public void setStartSceneX(final double startSceneX) {
		this.startSceneX = startSceneX;
	}

	public double getStartSceneY() {
		return startSceneY;
	}

	public void setStartSceneY(final double startSceneY) {
		this.startSceneY = startSceneY;
	}

	public double getStartTranslateX() {
		return startTranslateX;
	}

	public void setStartTranslateX(final double startTranslateX) {
		this.startTranslateX = startTranslateX;
	}

	public double getStartTranslateY() {
		return startTranslateY;
	}

	public void setStartTranslateY(final double startTranslateY) {
		this.startTranslateY = startTranslateY;
	}

}
