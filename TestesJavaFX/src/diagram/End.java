package diagram;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;
import model.CoordinatesXY;

public class End extends Circle implements Diagram {
	private Diagram next;
	private Boolean select;
	private CoordinatesMouseXY coordinatesMouse;

	public End(final double x, final double y, final double radius) {
		super();
		setCenterX(x);
		setCenterY(y);
		setRadius(radius);
		setFill(Color.WHITE);
		// borda
		setStroke(Color.BLACK);
		setStrokeWidth(2.0);
		coordinatesMouse = new CoordinatesMouseXY();

		setOnMousePressed((mouseEvent) -> {
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());

			coordinatesMouse.setStartTranslateX(((Circle) (mouseEvent.getSource())).getTranslateX());
			coordinatesMouse.setStartTranslateY(((Circle) (mouseEvent.getSource())).getTranslateY());

		});

		setOnMouseDragged((mouseEvent) -> {
			if (!mouseEvent.isShiftDown()) {
				double offsetX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
				double offsetY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
				double newTranslateX = coordinatesMouse.getStartTranslateX() + offsetX;
				double newTranslateY = coordinatesMouse.getStartTranslateY() + offsetY;

				((Circle) (mouseEvent.getSource())).setTranslateX(newTranslateX);
				((Circle) (mouseEvent.getSource())).setTranslateY(newTranslateY);
			}
		});

		setOnMouseReleased((mouseEvent) -> {
			if (!mouseEvent.isShiftDown()) {
				double mouseSceneX = mouseEvent.getSceneX();
				double mouseSceneY = mouseEvent.getSceneY();
				if (mouseSceneX < 0) {
					((Node) mouseEvent.getSource()).setTranslateX(1);
				}

				if (mouseSceneY < 0) {
					((Node) mouseEvent.getSource()).setTranslateY(1);
				}

				if (mouseSceneX > ConstantsSystem.WIDTH_SCENE) {
					((Node) mouseEvent.getSource())
							.setTranslateX(ConstantsSystem.WIDTH_SCENE - ConstantsSystem.WIDTH_RECTANGLE);
				}

				if (mouseSceneY > ConstantsSystem.HEIGHT_SCENE) {
					((Node) mouseEvent.getSource())
							.setTranslateY(ConstantsSystem.HEIGHT_SCENE - ConstantsSystem.HEIGHT_RECTANGLE);
				}

				getParent().setCursor(Cursor.DEFAULT);
			}
		});

	}

	@Override
	public CoordinatesXY getPoint0() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMinX(), bounds.getMinY() + bounds.getHeight() / 2);
	}

	@Override
	public CoordinatesXY getPoint1() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMinX() + bounds.getWidth() / 2, bounds.getMaxY());

	}

	@Override
	public CoordinatesXY getPoint2() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMaxX(), bounds.getMinY() + bounds.getHeight() / 2);
	}

	@Override
	public CoordinatesXY getPoint3() {
		Bounds bounds = getBoundsInParent();
		return new CoordinatesXY(bounds.getMinX() + bounds.getWidth() / 2, bounds.getMinY());
	}

	@Override
	public Diagram getNext() {
		return next;
	}

	@Override
	public void setNext(final Diagram next) {
		this.next = next;
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
