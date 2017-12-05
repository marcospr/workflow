package br.unicamp.cepetro.unisim.mero.gui.app.command;

public class CommandGU implements Command {

	private String name = "GU";

	@Override
	public void execute() {
		System.out.println("Processando Ferramenta " + name);
	}

	@Override
	public String getName() {
		return name;
	}

}
