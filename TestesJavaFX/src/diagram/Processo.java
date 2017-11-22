package diagram;

import java.util.UUID;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Processo extends Rectangle implements Selectable {
	private CoordinatesMouseXY coordinatesMouse;
	private Boolean select;

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

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	EventHandler<MouseEvent> rectangleOnMousePressedEventHandler = mouseEvent -> {
		getParent().setCursor(Cursor.HAND);
		coordinatesMouse.setStartSceneX(mouseEvent.getSceneX());
		coordinatesMouse.setStartSceneY(mouseEvent.getSceneY());
		coordinatesMouse.setStartTranslateX(((Rectangle) mouseEvent.getSource()).getTranslateX());
		coordinatesMouse.setStartTranslateY(((Rectangle) mouseEvent.getSource()).getTranslateY());
	};

	EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler = mouseEvent -> {
		double endSceneX = mouseEvent.getSceneX() - coordinatesMouse.getStartSceneX();
		double endSceneY = mouseEvent.getSceneY() - coordinatesMouse.getStartSceneY();
		double endTranslateX = coordinatesMouse.getStartTranslateX() + endSceneX;
		double endTranslateY = coordinatesMouse.getStartTranslateY() + endSceneY;

		((Rectangle) mouseEvent.getSource()).setTranslateX(endTranslateX);
		((Rectangle) mouseEvent.getSource()).setTranslateY(endTranslateY);
	};

	EventHandler<MouseEvent> rectangleOnMouseReleaseEventHandler = mouseEvent -> {
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
	};

}
