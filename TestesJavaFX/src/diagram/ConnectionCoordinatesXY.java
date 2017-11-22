package diagram;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.shape.Line;

public class ConnectionCoordinatesXY {

	public void calculatePointsXY(final Line connection, final Processo processInitial,
			final Processo processFinal) {
		Bounds startBounds = processInitial.getBoundsInParent();
		Bounds endBounds = processFinal.getBoundsInParent();

		SimpleDoubleProperty connectionInitialX = new SimpleDoubleProperty(0);
		SimpleDoubleProperty connectionInitialY = new SimpleDoubleProperty(0);
		SimpleDoubleProperty connectionFinalX = new SimpleDoubleProperty(0);
		SimpleDoubleProperty connectionFinalY = new SimpleDoubleProperty(0);

		if (startBounds.getMaxX() < endBounds.getMinX()) {
			connectionInitialX = new SimpleDoubleProperty(startBounds.getMaxX());
			connectionInitialY =
					new SimpleDoubleProperty(startBounds.getHeight() / 2 + startBounds.getMinY());
			connectionFinalX = new SimpleDoubleProperty(endBounds.getMinX());
			connectionFinalY = new SimpleDoubleProperty(endBounds.getHeight() / 2 + endBounds.getMinY());

		} else if (startBounds.getMinX() > endBounds.getMaxX()) {
			connectionInitialX = new SimpleDoubleProperty(startBounds.getMinX());
			connectionInitialY =
					new SimpleDoubleProperty(startBounds.getHeight() / 2 + startBounds.getMinY());

			connectionFinalX = new SimpleDoubleProperty(endBounds.getMaxX());
			connectionFinalY = new SimpleDoubleProperty(endBounds.getHeight() / 2 + endBounds.getMinY());

		} else if (startBounds.getMaxY() < endBounds.getMinY()) {

			connectionInitialX =
					new SimpleDoubleProperty(startBounds.getWidth() / 2 + startBounds.getMinX());
			connectionInitialY = new SimpleDoubleProperty(startBounds.getMaxY());

			connectionFinalX = new SimpleDoubleProperty(endBounds.getWidth() / 2 + endBounds.getMinX());
			connectionFinalY = new SimpleDoubleProperty(endBounds.getMinY());

		} else if (startBounds.getMinY() > endBounds.getMaxY()) {

			connectionInitialX =
					new SimpleDoubleProperty(startBounds.getWidth() / 2 + startBounds.getMinX());
			connectionInitialY = new SimpleDoubleProperty(startBounds.getMinY());

			connectionFinalX = new SimpleDoubleProperty(endBounds.getWidth() / 2 + endBounds.getMinX());
			connectionFinalY = new SimpleDoubleProperty(endBounds.getMaxY());

		}

		connection.startXProperty().bind(connectionInitialX);
		connection.startYProperty().bind(connectionInitialY);
		connection.endXProperty().bind(connectionFinalX);
		connection.endYProperty().bind(connectionFinalY);

		//
		// connection.setStartX(connectionInitialX.get());
		// connection.setScaleY(connectionInitialY.get());
		// connection.setEndX(connectionFinalX.get());
		// connection.setEndY(connectionFinalY.get());

	}
}
