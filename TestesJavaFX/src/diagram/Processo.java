package diagram;

import java.util.UUID;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;
import model.CoordinatesXY;

public class Processo extends Rectangle implements Selectable, ConnectionPoints {
	private CoordinatesMouseXY coordinatesMouse;
	private Boolean select;
	private Processo next;

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

		select = Boolean.FALSE;

		setArcWidth(20);
		setArcHeight(20);
		coordinatesMouse = new CoordinatesMouseXY();

		setOnMousePressed(rectangleOnMousePressedEventHandler);
		setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
		setOnMouseReleased(rectangleOnMouseReleaseEventHandler);

	}

	// public boolean addConnection(final Connection connection) {
	// return connections.add(connection);
	// }
	//
	// public boolean removeConnection(final Connection connection) {
	// return connections.remove(connection);
	// }
	//
	// public Set<Connection> getConnections() {
	// return connections;
	// }

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	EventHandler<MouseEvent> rectangleOnMousePressedEventHandler = mouseEvent -> {
		if (!mouseEvent.isShiftDown()) {
			getParent().setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(((Rectangle) mouseEvent.getSource()).getTranslateX());
			coordinatesMouse.setStartTranslateY(((Rectangle) mouseEvent.getSource()).getTranslateY());
		}
	};

	EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler = mouseEvent -> {
		if (!mouseEvent.isShiftDown()) {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			Rectangle rectangle = (Rectangle) mouseEvent.getSource();

			rectangle.setTranslateX(endTranslateX);
			rectangle.setTranslateY(endTranslateY);
		}
	};

	EventHandler<MouseEvent> rectangleOnMouseReleaseEventHandler = mouseEvent -> {
		if (!mouseEvent.isShiftDown()) {
			double mouseSceneX = mouseEvent.getSceneX();
			double mouseSceneY = mouseEvent.getSceneY();
			if (mouseSceneX < 0) {
				((Rectangle) mouseEvent.getSource()).setTranslateX(1);
			}

			if (mouseSceneY < 0) {
				((Rectangle) mouseEvent.getSource()).setTranslateY(1);
			}

			if (mouseSceneX > ConstantsSystem.WIDTH_SCENE) {
				((Rectangle) mouseEvent.getSource())
						.setTranslateX(ConstantsSystem.WIDTH_SCENE - ConstantsSystem.WIDTH_RECTANGLE);
			}

			if (mouseSceneY > ConstantsSystem.HEIGHT_SCENE) {
				((Rectangle) mouseEvent.getSource())
						.setTranslateY(ConstantsSystem.HEIGHT_SCENE - ConstantsSystem.HEIGHT_RECTANGLE);
			}

			getParent().setCursor(Cursor.DEFAULT);
		}
	};

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

	public Processo getNext() {
		return next;
	}

	public void setNext(final Processo next) {
		this.next = next;
	}

}
