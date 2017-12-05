package br.unicamp.cepetro.unisim.mero.gui.app.command;

public class CommandAQNS implements Command {
	private String name = "AQNS";

	@Override
	public void execute() {
		System.out.println("Processando Ferramenta " + name);
	}

	@Override
	public String getName() {
		return name;
	}

}
