package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import br.unicamp.cepetro.unisim.mero.gui.app.helper.CalculatorCoordinatesHelper;
import br.unicamp.cepetro.unisim.mero.gui.app.model.ConstantsSystem;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesXYInitialFinal;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Connection extends Conector {
	private Diagram startDiagram;
	private Diagram endDiagram;
	private CoordinatesXYInitialFinal coordinates;
	private Arrow arrow;

	public Connection(final Diagram startDiagram, final Diagram endDiagram) {
		select = Boolean.FALSE;
		this.startDiagram = startDiagram;
		this.endDiagram = endDiagram;
		this.startDiagram.setNext(this.endDiagram);

		// Tira o contorno de efeito
		startDiagram.unSelect();
		endDiagram.unSelect();

		coordinates = CalculatorCoordinatesHelper.calculatePointsXY(startDiagram, endDiagram);

		// Retorno o efeito
		startDiagram.select();
		endDiagram.select();

		// Set coordinates
		setCoordinatesControl(coordinates);

		setStroke(Color.GRAY);
		setStrokeWidth(2);
		setStrokeLineCap(StrokeLineCap.ROUND);
		setFill(Color.TRANSPARENT);

		arrow = new Arrow(this);
	}

	private void setCoordinatesControl(final CoordinatesXYInitialFinal coordinates) {
		setStartX(coordinates.getInitialX());
		setStartY(coordinates.getInitialY());
		setEndX(coordinates.getFinalX());
		setEndY(coordinates.getFinalY());

		if (coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_TWO
				&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_ZERO
				|| coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_ZERO
						&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_TWO) {

			if (coordinates.getInitialX() <= coordinates.getFinalX()) {
				setControlX1(coordinates.getInitialX() + ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
				setControlY1(coordinates.getInitialY());
				setControlX2(coordinates.getFinalX() - ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
				setControlY2(coordinates.getFinalY());

			} else if (coordinates.getInitialX() > coordinates.getFinalX()) {
				setControlX1(coordinates.getInitialX() - ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
				setControlY1(coordinates.getInitialY());
				setControlX2(coordinates.getFinalX() + ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
				setControlY2(coordinates.getFinalY());
			}
		} else if (coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_ONE
				&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_THREE
				|| coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_THREE
						&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_ONE) {

			if (coordinates.getInitialY() <= coordinates.getFinalY()) {
				setControlX1(coordinates.getInitialX());
				setControlY1(coordinates.getInitialY() + ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
				setControlX2(coordinates.getFinalX());
				setControlY2(coordinates.getFinalY() - ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);

			} else if (coordinates.getInitialY().doubleValue() > coordinates.getFinalY().doubleValue()) {
				setControlX1(coordinates.getInitialX());
				setControlY1(coordinates.getInitialY() - ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
				setControlX2(coordinates.getFinalX());
				setControlY2(coordinates.getFinalY() + ConstantsSystem.FIXED_CONTROL_CUBIC_CURVE);
			}

		} else {

			if (coordinates.getInitialY() <= coordinates.getFinalY()) {

				if (coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_TWO
						&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_THREE
						|| coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_ZERO
								&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_THREE) {

					setControlX1(coordinates.getFinalX());
					setControlY1(coordinates.getInitialY());
					setControlX2(coordinates.getFinalX());
					setControlY2(coordinates.getInitialY());

				} else if (coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_ONE
						&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_ZERO
						|| coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_ONE
								&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_TWO) {

					setControlX1(coordinates.getInitialX());
					setControlY1(coordinates.getFinalY());
					setControlX2(coordinates.getInitialX());
					setControlY2(coordinates.getFinalY());

				}

			} else if (coordinates.getInitialY() > coordinates.getFinalY()) {

				if (coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_THREE
						&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_ZERO
						|| coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_THREE
								&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_TWO) {

					setControlX1(coordinates.getInitialX());
					setControlY1(coordinates.getFinalY());
					setControlX2(coordinates.getInitialX());
					setControlY2(coordinates.getFinalY());

				} else if (coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_TWO
						&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_ONE
						|| coordinates.getInitialtPointIndex() == ConnectionPoints.INDEX_POINT_ZERO
								&& coordinates.getFinalPointIndex() == ConnectionPoints.INDEX_POINT_ONE) {

					setControlX1(coordinates.getFinalX());
					setControlY1(coordinates.getInitialY());
					setControlX2(coordinates.getFinalX());
					setControlY2(coordinates.getInitialY());

				}

			}

		}

	}

	public void recalculateXY() {
		// Tira o contorno de efeito
		if (startDiagram.isSelect()) {
			((Node) startDiagram).setEffect(null);
		}
		if (endDiagram.isSelect()) {
			((Node) endDiagram).setEffect(null);
		}

		coordinates = CalculatorCoordinatesHelper.calculatePointsXY(startDiagram, endDiagram);

		// Retorno o efeito
		if (startDiagram.isSelect()) {
			((Node) startDiagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}
		if (endDiagram.isSelect()) {
			((Node) endDiagram).setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}

		setCoordinatesControl(coordinates);
	}

	public Arrow getArrow() {
		return arrow;
	}

	public void setArrow(final Arrow arrow) {
		this.arrow = arrow;
	}

	public Diagram getStartDiagramContainer() {
		return startDiagram;
	}

	public void setStartDiagramContainer(final Diagram startDiagram) {
		this.startDiagram = startDiagram;
	}

	public Diagram getEndDiagramContainer() {
		return endDiagram;
	}

	public void setEndDiagramContainer(final ProcessDiagram endDiagram) {
		this.endDiagram = endDiagram;
	}

	public CoordinatesXYInitialFinal getCoordinates() {
		return coordinates;
	}

}
