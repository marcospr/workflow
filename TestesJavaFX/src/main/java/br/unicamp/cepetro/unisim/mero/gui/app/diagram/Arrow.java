package br.unicamp.cepetro.unisim.mero.gui.app.diagram;

import br.unicamp.cepetro.unisim.mero.gui.app.model.ConstantsSystem;
import br.unicamp.cepetro.unisim.mero.gui.app.model.CoordinatesXYInitialFinal;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Arrow extends StackPane implements Selectable {
	private Boolean select;
	private Circle circle;
	private ImageView imageView;
	private Image image;;

	public Arrow(final Connection connection) {
		setTranslateX(connection.getCoordinates().getFinalX());
		setTranslateY(connection.getCoordinates().getFinalY());

		createCircle(connection.getEndX(), connection.getEndY());
		createImageView(connection.getCoordinates());

		getChildren().addAll(circle, imageView);
	}

	private void createImageView(final CoordinatesXYInitialFinal coordinates) {
		imageView = new ImageView();

		if (coordinates.getFinalPointIndex() == 0) {
			// right
			image = new Image(this.getClass()
					.getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("arrow_right.png")));
		} else if (coordinates.getFinalPointIndex() == 1) {
			// up
			image = new Image(
					this.getClass().getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("arrow_up.png")));
		} else if (coordinates.getFinalPointIndex() == 2) {
			// left
			image = new Image(this.getClass()
					.getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("arrow_left.png")));
		} else {
			// down
			image = new Image(this.getClass()
					.getResourceAsStream(ConstantsSystem.PATH_IMAGES.concat("arrow_down.png")));
		}
		imageView.setImage(image);
	}

	private void createCircle(final double x, final double y) {
		circle = new Circle();
		circle.setCenterX(x);
		circle.setCenterY(y);
		circle.setRadius(10);
		circle.setStroke(Color.GREY);
		circle.setFill(Color.ANTIQUEWHITE);
		circle.setStrokeWidth(1.5);
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(final boolean select) {
		this.select = select;
	}

	@Override
	public void select() {
		setEffect(new DropShadow(10, Color.DEEPSKYBLUE));
		setSelect(Boolean.TRUE);
	}

	@Override
	public void unSelect() {
		setEffect(null);
		setSelect(Boolean.FALSE);
	}
}
