package br.unicamp.cepetro.unisim.mero.ui.app.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.unicamp.cepetro.unisim.mero.ui.app.diagram.ConnectionPoints;
import br.unicamp.cepetro.unisim.mero.ui.app.model.CoordinatesXY;
import br.unicamp.cepetro.unisim.mero.ui.app.model.CoordinatesXYInitialFinal;

public class CalculatorCoordinatesHelper {

	public static CoordinatesXYInitialFinal calculatePointsXY(final ConnectionPoints start,
			final ConnectionPoints end) {

		Double startX = null;
		Double startY = null;
		Double endX = null;
		Double endY = null;
		int startPointIndex = 0;
		int endPointIndex = 0;

		CoordinatesXY[] startPoints =
				{start.getPoint0(), start.getPoint1(), start.getPoint2(), start.getPoint3()};

		CoordinatesXY[] endPoints =
				{end.getPoint0(), end.getPoint1(), end.getPoint2(), end.getPoint3()};

		Map<CoordinatesXY[], Double> distancias = calculateDistanceCoordinates(startPoints, endPoints);

		CoordinatesXY[] CoordinatesXY = getPointsMinimumDistance(distancias);

		startX = CoordinatesXY[0].getX();
		startY = CoordinatesXY[0].getY();
		startPointIndex = CoordinatesXY[0].getPointIndex();

		endX = CoordinatesXY[1].getX();
		endY = CoordinatesXY[1].getY();
		endPointIndex = CoordinatesXY[1].getPointIndex();

		return new CoordinatesXYInitialFinal(startX, startY, startPointIndex, endX, endY,
				endPointIndex);

	}

	private static Map<CoordinatesXY[], Double> calculateDistanceCoordinates(
			final CoordinatesXY[] startPoints, final CoordinatesXY[] endPoints) {
		Map<CoordinatesXY[], Double> distancias = new HashMap<>();
		for (CoordinatesXY startPoint : startPoints) {
			for (CoordinatesXY endPoint : endPoints) {
				double startX = startPoint.getX();
				double startY = startPoint.getY();
				double endX = endPoint.getX();
				double endY = endPoint.getY();

				double distanceValue = Math.sqrt(Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2));

				CoordinatesXY[] points = {startPoint, endPoint};
				distancias.put(points, distanceValue);
			}
		}
		return distancias;
	}

	private static CoordinatesXY[] getPointsMinimumDistance(
			final Map<CoordinatesXY[], Double> distancias) {
		Double min = Double.MAX_VALUE;
		Set<CoordinatesXY[]> keys = distancias.keySet();
		CoordinatesXY[] result = null;

		if (keys != null) {
			for (CoordinatesXY[] key : keys) {
				Double value = distancias.get(key);
				if (min.compareTo(value) > 0) {
					min = value;
					result = key;
				}
			}
		}
		return result;
	}

	private void rulesOld() {
		// if (startBounds.getMaxX() < endBounds.getMinX()) {
		// startX = new Double(startBounds.getMaxX());
		// startY = new Double(startBounds.getHeight() / 2 + startBounds.getMinY());
		// endX = new Double(endBounds.getMinX());
		// endY = new Double(endBounds.getHeight() / 2 + endBounds.getMinY());
		//
		// } else if (startBounds.getMinX() > endBounds.getMaxX()) {
		// startX = new Double(startBounds.getMinX());
		// startY = new Double(startBounds.getHeight() / 2 + startBounds.getMinY());
		//
		// endX = new Double(endBounds.getMaxX());
		// endY = new Double(endBounds.getHeight() / 2 + endBounds.getMinY());
		//
		// } else if (startBounds.getMaxY() < endBounds.getMinY()) {
		//
		// startX = new Double(startBounds.getWidth() / 2 + startBounds.getMinX());
		// startY = new Double(startBounds.getMaxY());
		//
		// endX = new Double(endBounds.getWidth() / 2 + endBounds.getMinX());
		// endY = new Double(endBounds.getMinY());
		//
		// } else if (startBounds.getMinY() > endBounds.getMaxY()) {
		//
		// startX = new Double(startBounds.getWidth() / 2 + startBounds.getMinX());
		// startY = new Double(startBounds.getMinY());
		//
		// endX = new Double(endBounds.getWidth() / 2 + endBounds.getMinX());
		// endY = new Double(endBounds.getMaxY());
		//
		// } else {
		// startX = new Double(0);
		// startY = new Double(0);
		// endX = new Double(0);
		// endY = new Double(0);
		// }
	}

}
