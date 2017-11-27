package diagram;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;
import model.CoordinatesXY;

public class DiagramContainer extends StackPane implements Diagram {
	private CoordinatesMouseXY coordinatesMouse;
	private Boolean select;
	private DiagramContainer next;

	public DiagramContainer(final Node node, final double x, final double y, final Text text) {
		super(node);
		select = Boolean.FALSE;
		coordinatesMouse = new CoordinatesMouseXY();

		setTranslateX(x);
		setTranslateY(y);

		setOnMousePressed(diagramOnMousePressedEventHandler);
		setOnMouseDragged(diagramOnMouseDraggedEventHandler);
		setOnMouseReleased(diagramOnMouseReleaseEventHandler);

		setAlignment(node, Pos.BASELINE_CENTER);

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
	public DiagramContainer getNext() {
		return next;
	}

	@Override
	public void setNext(final DiagramContainer next) {
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

	EventHandler<MouseEvent> diagramOnMousePressedEventHandler = mouseEvent -> {
		if (!mouseEvent.isShiftDown()) {
			getParent().setCursor(Cursor.HAND);
			coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
			coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
			coordinatesMouse.setStartTranslateX(((StackPane) mouseEvent.getSource()).getTranslateX());
			coordinatesMouse.setStartTranslateY(((StackPane) mouseEvent.getSource()).getTranslateY());
		}
	};

	EventHandler<MouseEvent> diagramOnMouseDraggedEventHandler = mouseEvent -> {
		if (!mouseEvent.isShiftDown()) {
			double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
			double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
			double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
			double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

			StackPane pane = (StackPane) mouseEvent.getSource();

			pane.setTranslateX(endTranslateX);
			pane.setTranslateY(endTranslateY);
		}
	};

	EventHandler<MouseEvent> diagramOnMouseReleaseEventHandler = mouseEvent -> {
		if (!mouseEvent.isShiftDown()) {
			double mouseSceneX = mouseEvent.getSceneX();
			double mouseSceneY = mouseEvent.getSceneY();
			if (mouseSceneX < 0) {
				((StackPane) mouseEvent.getSource()).setTranslateX(1);
			}

			if (mouseSceneY < 0) {
				((StackPane) mouseEvent.getSource()).setTranslateY(1);
			}

			if (mouseSceneX > ConstantsSystem.WIDTH_SCENE) {
				((StackPane) mouseEvent.getSource())
						.setTranslateX(ConstantsSystem.WIDTH_SCENE - ConstantsSystem.WIDTH_RECTANGLE);
			}

			if (mouseSceneY > ConstantsSystem.HEIGHT_SCENE) {
				((StackPane) mouseEvent.getSource())
						.setTranslateY(ConstantsSystem.HEIGHT_SCENE - ConstantsSystem.HEIGHT_RECTANGLE);
			}

			getParent().setCursor(Cursor.DEFAULT);
		}
	};
}
