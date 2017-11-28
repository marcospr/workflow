package diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Start extends Circle {

	public Start(final double x, final double y, final double radius) {
		super();
		setCenterX(x);
		setCenterY(y);
		setRadius(radius);
		// borda
		setStroke(Color.GREY);
		setFill(Color.ANTIQUEWHITE);
		setStrokeWidth(2.0);
	}

}
