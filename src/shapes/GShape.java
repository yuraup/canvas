package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

abstract public class GShape {
	protected Shape shape;

	public GShape() {
	}

	abstract public GShape clone(); // GShape은 뭘 만들지 모름. 도형이 다 다르다.

	public boolean onShape(Point p) {
		return shape.contains(p);
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shape);
	}

	public abstract void setShape(int x1, int y1, int x2, int y2);

	public abstract void resizePoint(int x2, int y2);

	public abstract void movePoint(int x, int y);

	public void addPoint(int x, int y) {
	}

	public abstract void setPoint(int x, int y);; // 없어도 되고 있어도 됨 =/= abstract

}