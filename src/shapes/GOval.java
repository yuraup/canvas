package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class GOval extends GShape {
	public GOval(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
		shape = new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1);
	}

	public boolean onShape(Point p) {
		return shape.contains(p);
	}

	public void draw(Graphics graphics) { // 그림 그리기
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shape);
	}

	public void addPoint(int x2, int y2) {
		double x1 = shape.getBounds2D().getX(); //좌표 하나 더 필요
		double y1 = shape.getBounds2D().getY();
		((Ellipse2D.Double) shape).setFrameFromDiagonal(x1, y1, x2, y2);
	}
}