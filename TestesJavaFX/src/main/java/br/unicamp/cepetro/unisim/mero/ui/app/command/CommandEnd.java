package br.unicamp.cepetro.unisim.mero.ui.app.command;

public class CommandEnd implements Command {
	private String name = "End";

	@Override
	public void execute() {
		System.out.println("Processando Ferramenta " + name);
	}

	@Override
	public String getName() {
		return name;
	}

}
