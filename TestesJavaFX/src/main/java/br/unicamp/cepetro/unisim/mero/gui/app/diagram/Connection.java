package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import br.unicamp.cepetro.unisim.mero.gui.app.helper.CalculatorCoordinatesHelper;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesXY;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesXYInitialFinal;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Connection extends AbstractLine implements ConnectionPoints {
	private AbstractDiagram startDiagram;
	private AbstractDiagram endDiagram;
	private Arrow arrow;

	public Connection(final AbstractDiagram startDiagram, final AbstractDiagram endDiagram) {
		select = Boolean.FALSE;
		this.startDiagram = startDiagram;
		this.endDiagram = endDiagram;
		this.startDiagram.setNext(this.endDiagram);
		setStroke(Color.GREY);
		setStrokeWidth(1);

		// Tira o contorno de efeito
		startDiagram.unSelect();
		endDiagram.unSelect();

		CoordinatesXYInitialFinal coordinates =
				CalculatorCoordinatesHelper.calculatePointsXY(startDiagram, endDiagram);

		// Retorno o efeito
		startDiagram.select();
		endDiagram.select();

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

		CoordinatesXYInitialFinal coordinates =
				CalculatorCoordinatesHelper.calculatePointsXY(startDiagram, endDiagram);

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

	public Arrow getArrow() {
		return arrow;
	}

	public void setArrow(final Arrow arrow) {
		this.arrow = arrow;
	}

	public AbstractDiagram getStartDiagramContainer() {
		return startDiagram;
	}

	public void setStartDiagramContainer(final AbstractDiagram startDiagram) {
		this.startDiagram = startDiagram;
	}

	public AbstractDiagram getEndDiagramContainer() {
		return endDiagram;
	}

	public void setEndDiagramContainer(final ProcessDiagram endDiagram) {
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

}
