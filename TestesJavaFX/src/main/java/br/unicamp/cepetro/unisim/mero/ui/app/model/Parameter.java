package br.unicamp.cepetro.unisim.mero.ui.app.model;

public class Parameter {
	private String name;
	private String value;

	public Parameter(final String name, final String value) {
		super();
		setName(name);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
