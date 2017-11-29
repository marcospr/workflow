package factory;

import command.Command;
import command.CommandAQNS;
import command.CommandDS;
import command.CommandEnd;
import command.CommandGAS;
import command.CommandGU;
import command.CommandHLDG;
import command.CommandStart;
import model.CommandType;

public class CommandFactory {

	public Command createCommand(final CommandType type) {
		switch (type) {
			case AQNS:
				return new CommandAQNS();
			case DS:
				return new CommandDS();
			case GAS:
				return new CommandGAS();
			case GU:
				return new CommandGU();
			case HLDG:
				return new CommandHLDG();
			case Start:
				return new CommandStart();
			case End:
				return new CommandEnd();
			default:
				break;
		}
		return null;
	}

}
