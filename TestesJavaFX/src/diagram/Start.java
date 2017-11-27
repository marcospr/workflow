package diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Start extends Circle {

	public Start(final double x, final double y, final double radius) {
		super();
		setCenterX(x);
		setCenterY(y);
		setRadius(radius);
		setFill(Color.WHITE);
		// borda
		setStroke(Color.BLACK);
		setStrokeWidth(2.0);
	}

}
