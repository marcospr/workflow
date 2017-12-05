package br.unicamp.cepetro.unisim.mero.gui.app.helper;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.cepetro.unisim.mero.gui.app.diagram.Connection;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaneHelper {

	public PaneHelper() {}

	public static <T> List<T> getSpecificNodesFromPane(final Class<T> clazz, final Pane pane) {
		List<T> result = new ArrayList<>();
		for (Node node : pane.getChildren()) {
			if (isIstanceof(clazz, node)) {
				result.add(clazz.cast(node));
			}
		}
		return result;
	}

	public static <T> T getSpecificNodeFromCoordinatesXY(final double mouseX, final double mouseY,
			final Pane pane, final Class<T> clazz) {
		T diagramResult = null;
		ObservableList<Node> nodes = pane.getChildren();

		for (Node node : nodes) {
			if (isIstanceof(clazz, node)) {
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

	public static List<Connection> getConnectionFromPane(final Pane pane) {
		List<Connection> connections = new ArrayList<>();
		ObservableList<Node> nodes = pane.getChildren();

		for (Node node : nodes) {
			if (node instanceof Connection) {
				connections.add((Connection) node);
			}
		}
		return connections;

	}

	private static <T> Boolean isIstanceof(final Class<T> clazz, final Object object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		return clazz.isInstance(object);
	}
}
