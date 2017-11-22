package diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Connection extends Line implements Selectable {
	private Boolean select;

	public Connection(final Processo startRectangle, final Processo endRectangle) {
		select = Boolean.FALSE;
		setStroke(Color.BLACK);
		setStrokeWidth(2);
		startXProperty().bind(startRectangle.translateXProperty().add(50));
		startYProperty().bind(startRectangle.translateYProperty().add(25));
		endXProperty().bind(endRectangle.translateXProperty().add(50));
		endYProperty().bind(endRectangle.translateYProperty().add(25));

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
