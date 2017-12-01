package helper;

import java.util.ArrayList;
import java.util.List;

import diagram.Connection;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaneHelper {
	private TypeHelper typeHelper;

	public PaneHelper(final TypeHelper typeHelper) {
		this.typeHelper = typeHelper;
	}

	public <T> List<T> getSpecificNodesFromPane(final Class<T> clazz, final Pane pane) {
		List<T> result = new ArrayList<>();
		for (Node node : pane.getChildren()) {
			if (typeHelper.isTypeFrom(clazz, node)) {
				result.add(clazz.cast(node));
			}
		}
		return result;
	}

	public <T> T getSpecificNodeFromCoordinatesXY(final double mouseX, final double mouseY,
			final Pane pane, final Class<T> clazz) {
		T diagramResult = null;
		ObservableList<Node> nodes = pane.getChildren();

		for (Node node : nodes) {
			if (typeHelper.isTypeFrom(clazz, node)) {
				Bounds bounds = node.getBoundsInParent();
				if (bounds != null && mouseX <= bounds.getMaxX() && mouseY <= bounds.getMaxY()
						&& mouseX >= bounds.getMinX() && mouseY >= bounds.getMinY()) {

					diagramResult = clazz.cast(node);
					break;
				}
			}
		}
		return diagramResult;
	}

	public List<Connection> getConnectionFromPane(final Pane pane) {
		List<Connection> connections = new ArrayList<>();
		ObservableList<Node> nodes = pane.getChildren();

		for (Node node : nodes) {
			if (node instanceof Connection) {
				connections.add((Connection) node);
			}
		}
		return connections;

	}
}
