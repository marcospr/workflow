package diagram;

import helper.CalculatorCoordinatesHelper;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.CoordinatesXYInitialFinal;

public class Connection extends Line implements Selectable {
	private Boolean select;
	private Processo startProcess;
	private Processo endProcess;
	private Arrow arrow;

	public Connection(final Processo startProcess, final Processo endProcess) {
		select = Boolean.FALSE;
		this.startProcess = startProcess;
		this.endProcess = endProcess;
		this.startProcess.setNext(this.endProcess);
		setStroke(Color.BLACK);
		setStrokeWidth(1);

		// Tira o contorno de efeito
		if (startProcess.isSelect()) {
			startProcess.setEffect(null);
		}
		if (endProcess.isSelect()) {
			endProcess.setEffect(null);
		}

		CoordinatesXYInitialFinal coordinates =
				CalculatorCoordinatesHelper.calculatePointsXY(startProcess, endProcess);

		// Retorno o efeito
		if (startProcess.isSelect()) {
			startProcess.setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}
		if (endProcess.isSelect()) {
			endProcess.setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
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
		if (startProcess.isSelect()) {
			startProcess.setEffect(null);
		}
		if (endProcess.isSelect()) {
			endProcess.setEffect(null);
		}

		CoordinatesXYInitialFinal coordinates =
				CalculatorCoordinatesHelper.calculatePointsXY(startProcess, endProcess);

		// Retorno o efeito
		if (startProcess.isSelect()) {
			startProcess.setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		}
		if (endProcess.isSelect()) {
			endProcess.setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
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

	public Processo getStartProcess() {
		return startProcess;
	}

	public void setStartProcess(final Processo startProcess) {
		this.startProcess = startProcess;
	}

	public Processo getEndProcess() {
		return endProcess;
	}

	public void setEndProcess(final Processo endProcess) {
		this.endProcess = endProcess;
	}

}
