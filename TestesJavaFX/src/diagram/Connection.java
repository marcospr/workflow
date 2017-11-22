package diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Connection extends Line implements Selectable {
	private Boolean select;
	private Processo startRectangle;
	private Processo endRectangle;
	private Arrow arrow;

	public Connection(final Processo startRectangle, final Processo endRectangle) {
		select = Boolean.FALSE;
		this.startRectangle = startRectangle;
		this.endRectangle = endRectangle;
		setStroke(Color.BLACK);
		setStrokeWidth(1);
		startXProperty().bind(startRectangle.translateXProperty().add(50));
		startYProperty().bind(startRectangle.translateYProperty().add(25));
		endXProperty().bind(endRectangle.translateXProperty().add(50));
		endYProperty().bind(endRectangle.translateYProperty().add(25));

		this.startRectangle.addConnection(this);
		this.endRectangle.addConnection(this);
		arrow = new Arrow(startRectangle, endRectangle);
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	public Processo getStartRectangle() {
		return startRectangle;
	}

	public Processo getEndRectangle() {
		return endRectangle;
	}

	public Arrow getArrow() {
		return arrow;
	}

	public void setArrow(final Arrow arrow) {
		this.arrow = arrow;
	}

}
