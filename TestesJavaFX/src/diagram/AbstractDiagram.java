package diagram;

import command.Command;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.ConstantsSystem;
import model.CoordinatesMouseXY;
import model.CoordinatesXY;

public abstract class AbstractDiagram extends StackPane implements Selectable, ConnectionPoints {
	private Boolean select;
	private AbstractDiagram next;
	protected CoordinatesMouseXY coordinatesMouse;
	private Command command;

	public AbstractDiagram(final Node node, final double x, final double y, final Command command) {
		super(node);
		this.command = command;
		setSelect(Boolean.FALSE);
		coordinatesMouse = new CoordinatesMouseXY();

		setTranslateX(x);
		setTranslateY(y);

		setOnMousePressed(diagramOnMousePressedEventHandler);
		setOnMouseDragged(diagramOnMouseDraggedEventHandler);
		setOnMouseReleased(diagramOnMouseReleaseEventHandler);
	}

	public AbstractDiagram(final Node node) {
		super(node);
	}

	public final void execute(final ProgressBar progress) {
		System.out.println("Inicio de processamento");
		select();

		Task<String> task = new Task<String>() {
			@Override
			protected String call() throws Exception {

				try {
					command.execute();
					Thread.sleep(2000);
					AbstractDiagram next = getNext();
					if (next != null) {
						next.execute(progress);
					} else {
						System.out.println("Fim de processamento");
					}

				} catch (InterruptedException e) {
					new RuntimeException("Erro Proccessamento " + e.getMessage());
				}
				return "";
			};

			@Override
			protected void failed() {
				Throwable e = getException();
				throw new RuntimeException(e);
			}

			@Override
			protected void succeeded() {
				unSelect();
			};

		};

		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();

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
	public void select() {
		setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		setSelect(Boolean.TRUE);
	}

	@Override
	public void unSelect() {
		setEffect(null);
		setSelect(Boolean.FALSE);
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	public AbstractDiagram getNext() {
		return next;
	}

	public void setNext(final AbstractDiagram next) {
		this.next = next;
	}

	public String getLabel() {
		String label = "";
		ObservableList<Node> childrens = getChildren();
		for (Node node : childrens) {
			if (node instanceof Text) {
				label = ((Text) node).getText();
				break;
			}
		}
		return label;
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
