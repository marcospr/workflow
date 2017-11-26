package diagram;

import helper.CalculatorCoordinatesHelper;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.CoordinatesXY;
import model.CoordinatesXYInitialFinal;

public class Connection extends Line implements Diagram {
	private Boolean select;
	private Diagram startDiagram;
	private Diagram endDiagram;
	private Arrow arrow;

	public Connection(final Diagram startDiagram, final Diagram endDiagram) {
		select = Boolean.FALSE;
		this.startDiagram = startDiagram;
		this.endDiagram = endDiagram;
		this.startDiagram.setNext(this.endDiagram);
		setStroke(Color.BLACK);
		setStrokeWidth(1);

		// Tira o contorno de efeito
		if (startDiagram.isSelect()) {
			((Node) startDiagram).setEffect(null);
		}
		if (endDiagram.isSelect()) {
			((Node) endDiagram).setEffect(null);
		}

		CoordinatesXYInitialFinal coordinates = CalculatorCoordinatesHelper.calculatePointsXY(startDiagram, endDiagram);

		// Retorno o efeito
		if (startDiagram.isSelect()) {
			((Node) startDiagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}
		if (endDiagram.isSelect()) {
			((Node) endDiagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}

		setStartX(coordinates.getInitialX());
		setStartY(coordinates.getInitialY());
		setEndX(coordinates.getFinalX());
		setEndY(coordinates.getFinalY());

		// startXProperty().bind(startProcess.translateXProperty().add(50));
		// startYProperty().bind(startProcess.translateYProperty().add(25));
		// endXProperty().bind(endProcess.translateXProperty().add(50));
		// endYProperty().bind(endProcess.translateYProperty().add(25));

		// this.startProcess.addConnection(this);
		// this.endProcess.addConnection(this);
		arrow = new Arrow(this);
	}

	public void recalculateXY() {
		// Tira o contorno de efeito
		if (startDiagram.isSelect()) {
			((Node) startDiagram).setEffect(null);
		}
		if (endDiagram.isSelect()) {
			((Node) endDiagram).setEffect(null);
		}

		CoordinatesXYInitialFinal coordinates = CalculatorCoordinatesHelper.calculatePointsXY(startDiagram, endDiagram);

		// Retorno o efeito
		if (startDiagram.isSelect()) {
			((Node) startDiagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}
		if (endDiagram.isSelect()) {
			((Node) endDiagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}

		setStartX(coordinates.getInitialX());
		setStartY(coordinates.getInitialY());
		setEndX(coordinates.getFinalX());
		setEndY(coordinates.getFinalY());
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	public Arrow getArrow() {
		return arrow;
	}

	public void setArrow(final Arrow arrow) {
		this.arrow = arrow;
	}

	public Diagram getStartDiagram() {
		return startDiagram;
	}

	public void setStartDiagram(final Diagram startDiagram) {
		this.startDiagram = startDiagram;
	}

	public Diagram getEndDiagram() {
		return endDiagram;
	}

	public void setEndNode(final Diagram endDiagram) {
		this.endDiagram = endDiagram;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNext(Diagram diagram) {
		// TODO Auto-generated method stub

	}

}
