package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class GLine extends GShape {
	public GLine(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
		shape = new Line2D.Double(x1, y1, x2, y2);
	}

	public boolean onShape(Point p) {
		return ((Line2D) shape).ptSegDist(p) < 2.0;
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shape);
	}

	public void addPoint(int x2, int y2) {
		double x1 = shape.getBounds2D().getX();
		double y1 = shape.getBounds2D().getY();
		((Line2D.Double) shape).setLine(x1, y1, x2, y2);
	}
}