package br.unicamp.cepetro.unisim.mero.ui.app.command;

public class CommandHLDG implements Command {

	private String name = "HLDG";

	@Override
	public void execute() {
		System.out.println("Processando Ferramenta " + name);
	}

	@Override
	public String getName() {
		return name;
	}

}
