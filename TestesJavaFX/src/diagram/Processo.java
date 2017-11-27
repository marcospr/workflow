package diagram;

import java.util.UUID;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.ConstantsSystem;

public class Processo extends Rectangle {

	public Processo(final double X, final double Y) {
		super(ConstantsSystem.WIDTH_RECTANGLE, ConstantsSystem.HEIGHT_RECTANGLE);
		setId(UUID.randomUUID().toString());
		setTranslateX(X);
		setTranslateY(Y);
		// preenchimento
		setFill(Color.WHITE);
		// borda
		setStroke(Color.BLACK);
		setStrokeWidth(2.0);

		setArcWidth(20);
		setArcHeight(20);

	}

}
