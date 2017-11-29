package model;

public enum CommandType {
	AQNS("AQNS"),
	DS("DS"),
	GAS("GAS"),
	GU("GU"),
	HLDG("HLDG"),
	Start("Start"),
	End("End");

	private String type;

	CommandType(final String type) {
		this.type = type;
	}

	String getType(final String type) {
		return type;
	}
}
