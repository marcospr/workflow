package diagram;

import java.util.UUID;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.ConstantsSystem;

public class Processo extends Rectangle {

	public Processo() {
		super(ConstantsSystem.WIDTH_RECTANGLE, ConstantsSystem.HEIGHT_RECTANGLE);
		setId(UUID.randomUUID().toString());
		// preenchimento
		setFill(Color.WHITE);
		// borda
		setStrokeWidth(2.0);

		setArcWidth(20);
		setArcHeight(20);

		setStroke(Color.GREY);
		setFill(Color.ANTIQUEWHITE);
	}

}
