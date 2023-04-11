package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

abstract public class GShape {

	protected Shape shape;

	public GShape() {
	}

	public void addPoint(int x2, int y2) {
//		this.x2 = x2;
//		this.y2 = y2;
	}

	public boolean onShape(Point p) {
		return shape.contains(p);
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shape);
	}

	public abstract void setShape(int x1, int y1, int x2, int y2);

	public abstract void movePoint(int x2, int y2);

}