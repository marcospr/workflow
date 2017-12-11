package br.unicamp.cepetro.unisim.mero.ui.app.diagram;

import br.unicamp.cepetro.unisim.mero.ui.app.model.CoordinatesXY;

public interface ConnectionPoints {
	static final int INDEX_POINT_ZERO = 0;
	static final int INDEX_POINT_ONE = 1;
	static final int INDEX_POINT_TWO = 2;
	static final int INDEX_POINT_THREE = 3;

	CoordinatesXY getPoint0();

	CoordinatesXY getPoint1();

	CoordinatesXY getPoint2();

	CoordinatesXY getPoint3();
}
