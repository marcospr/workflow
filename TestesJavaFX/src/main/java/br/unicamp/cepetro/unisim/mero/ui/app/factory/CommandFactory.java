package br.unicamp.cepetro.unisim.mero.ui.app.factory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.unicamp.cepetro.unisim.mero.ui.app.command.Command;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandAQNS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandDS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandEnd;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandGAS;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandGU;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandHLDG;
import br.unicamp.cepetro.unisim.mero.ui.app.command.CommandStart;
import br.unicamp.cepetro.unisim.mero.ui.app.model.CommandType;

@Component
@Scope("singleton")
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
