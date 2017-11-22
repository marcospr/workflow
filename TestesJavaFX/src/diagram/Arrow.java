package diagram;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Arrow extends Polygon implements Selectable {
	private Boolean select;

	public Arrow(final Rectangle startRectangle, final Rectangle endRectangle) {

		select = Boolean.FALSE;
		double startX = startRectangle.getTranslateX();
		double startY = startRectangle.getTranslateY();
		double endx = endRectangle.getTranslateX();
		double endy = endRectangle.getTranslateY();

		getPoints().addAll(new Double[] {0.0, 5.0, -5.0, -5.0, 5.0, -5.0});

		double angle = Math.atan2(endy - startY, endx - startX) * 180 / Math.PI;

		setRotate(angle - 90);

		translateXProperty().bind(endRectangle.translateXProperty().add(50));
		translateYProperty().bind(endRectangle.translateYProperty().add(25));

		// translateXProperty()
		// .bind(Bindings.when(translateXProperty().isEqualTo(endRectangle.translateXProperty()))
		// .then(startRectangle.translateXProperty().add(50))
		// .otherwise(endRectangle.translateYProperty().add(25)));
		//
		// translateYProperty()
		// .bind(Bindings.when(translateYProperty().isEqualTo(endRectangle.translateYProperty()))
		// .then(startRectangle.translateYProperty().add(50))
		// .otherwise(endRectangle.translateYProperty().add(25)));
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

}
