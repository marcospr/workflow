package br.unicamp.cepetro.unisim.mero.ui.app.command;

public class CommandStart implements Command {
	private String name = "Start";

	@Override
	public void execute() {
		System.out.println("Processando Ferramenta " + name);
	}

	@Override
	public String getName() {
		return name;
	}

}
