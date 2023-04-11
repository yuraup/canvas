package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class GPolygon extends GShape {

	private int[] xPoints, yPoints; // 포인트가 n개
	private int nPoints;

	public GPolygon() {
		super();
		xPoints = new int[10];
		yPoints = new int[10];
		nPoints = 0;
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		if (nPoints < xPoints.length) {
			xPoints[nPoints] = x2;
			yPoints[nPoints] = y2;
			nPoints++;
			shape = new Polygon(xPoints, yPoints, nPoints);
		}
	}

	@Override
	public void movePoint(int x2, int y2) {
		if (nPoints > 0) { // 포인트가 있다면
			xPoints[nPoints - 1] = x2;
			yPoints[nPoints - 1] = y2;
			shape = new Polygon(xPoints, yPoints, nPoints);
		}
	}

	@Override
	public boolean onShape(Point p) {
		Polygon polygon = (Polygon) shape;
		int[] xPoints = polygon.xpoints;
		int[] yPoints = polygon.ypoints;
		int nPoints = polygon.npoints;
		for (int i = 0, j = nPoints - 1; i < nPoints; j = i++) {
			if ((yPoints[i] > p.y) != (yPoints[j] > p.y)
					&& (p.x < (xPoints[j] - xPoints[i]) * (p.y - yPoints[i]) / (yPoints[j] - yPoints[i])
							+ xPoints[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics graphics) {
		if (shape != null) {
			Graphics2D graphics2d = (Graphics2D) graphics;
			graphics2d.draw(shape);
		}
	}
}