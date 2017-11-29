package command;

public class CommandGAS implements Command {

	private String name = "GAS";

	@Override
	public void execute() {
		System.out.println("Processando Ferramenta " + name);
	}

	@Override
	public String getName() {
		return name;
	}
}
