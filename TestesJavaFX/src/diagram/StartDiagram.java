package diagram;

import command.Command;
import javafx.scene.Node;

public class StartDiagram extends AbstractDiagram {

	public StartDiagram(final Node node, final double x, final double y, final Command command) {
		super(node, x, y, command);
	}
}
