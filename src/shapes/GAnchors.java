package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class GAnchors {
	public enum EAnchors {
		NW, NN, NE, EE, SE, SS, SW, WW, RR, // rotate
		MM // move
	}

	private Ellipse2D[] anchors;

	public GAnchors() {
		anchors = new Ellipse2D[EAnchors.values().length - 1]; // move는 실제로 앵커가 없으니까 -1
		for (int i = 0; i < anchors.length; i++) {
			anchors[i] = new Ellipse2D.Float(0, 0, 10, 10);
		}
	}

	public void setPosition(Rectangle rectangle) {
	}

	public void draw(Graphics2D graphics2D, Rectangle rectangle) {
		// set position
		setPosition(rectangle);
		for (Ellipse2D anchor : anchors) {
			graphics2D.draw(anchor);
		}
	}
}