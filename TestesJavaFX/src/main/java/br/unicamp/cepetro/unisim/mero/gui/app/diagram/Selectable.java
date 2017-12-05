package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

public interface Selectable {
	boolean isSelect();

	void select();

	void unSelect();

	void setSelect(final boolean select);
}
