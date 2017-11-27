package diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class End extends Circle {

	public End(final double x, final double y, final double radius) {
		super();
		setCenterX(x);
		setCenterY(y);
		setRadius(radius);
		setFill(Color.LIGHTGRAY);
		// borda
		setStroke(Color.BLACK);
		setStrokeWidth(5.0);

	}

}
