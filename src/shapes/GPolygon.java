package shapes;

import java.awt.Polygon;

public class GPolygon extends GShape {
	public GPolygon() {
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) { // 원점 찍기
		this.shape = new Polygon();
		Polygon polygon = ((Polygon) shape);
		polygon.addPoint(x1, y1);
		polygon.addPoint(x2, y2);
	}

	public void addPoint(int x, int y) { // 점이 필요할 때 추가
		Polygon polygon = ((Polygon) shape);
		polygon.addPoint(x, y);
	}

	@Override
	public void movePoint(int x, int y) { // 마지막 점을 움직이는 point
		Polygon polygon = ((Polygon) shape);
		polygon.xpoints[polygon.npoints - 1] = x; // 마지막 점
		polygon.ypoints[polygon.npoints - 1] = y;
	}

	@Override
	public GShape clone() {
		return new GPolygon();
	}
}