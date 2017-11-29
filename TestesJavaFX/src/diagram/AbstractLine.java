package diagram;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class AbstractLine extends Line implements Selectable {
	protected Boolean select;

	public AbstractLine() {}

	public AbstractLine(final double startX, final double startY, final double endX,
			final double endY) {
		super(startX, startY, endX, endY);
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	@Override
	public void select() {
		setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		setSelect(Boolean.TRUE);
	}

	@Override
	public void unSelect() {
		setEffect(null);
		setSelect(Boolean.FALSE);
	}
}
