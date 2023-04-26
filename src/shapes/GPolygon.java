package shapes;

import java.awt.Polygon;

public class GPolygon extends GShape {
	private int px, py;

	public GPolygon() {
	}

	@Override
	public GShape clone() {
		return new GPolygon(); // 빈게 하나 만들어진 것이지 / 사실 이건 복제라기보단 빈 것을 만든 것이지
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) { // 항상 처음에 두 개의 점을 찍고 시작해야 움직일 수 있음
		this.shape = new Polygon();
		Polygon polygon = (Polygon) shape;
		polygon.addPoint(x1, y1);
		polygon.addPoint(x2, y2);
	}

	public void addPoint(int x, int y) { // 점을 하나 추가하는 함수
		Polygon polygon = (Polygon) shape;
		polygon.addPoint(x, y);
	}

	@Override
	public void resizePoint(int x, int y) { // 마지막 점을 결정하는 아이 / 얘로 인해 마지막 점이 결정됨
		Polygon polygon = (Polygon) shape;
		polygon.xpoints[polygon.npoints - 1] = x;
		polygon.ypoints[polygon.npoints - 1] = y;
	}

	@Override
	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}

	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon) shape;
		Polygon newPolygon = new Polygon();

		for (int i = 0; i < polygon.npoints; i++) {
			newPolygon.addPoint(polygon.xpoints[i] + x - px, polygon.ypoints[i] + y - py);
		}

		this.shape = newPolygon;
		px = x;
		py = y;
	}
}