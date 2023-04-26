package shapes;

import java.awt.Rectangle;

public class GRectangle extends GShape {
	private int px, py;

	public GRectangle() {
		// Shape 클래스에 contains 함수 / 영역에 점이 있냐.. 이런 메소드가 있음
	}

	@Override
	public GShape clone() {
		return new GRectangle(); // 사각형 새로 만들었음 <-> 말만 클론, 실제로는 새 거
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) { // 추가
		this.shape = new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

	@Override
	public void resizePoint(int x2, int y2) {
		Rectangle rectangle = ((Rectangle) shape);
		rectangle.setFrame(rectangle.getX(), rectangle.getY(), x2 - rectangle.getX(), y2 - rectangle.getY());
	}

	@Override
	public void setPoint(int x, int y) {
		this.px = x; // 움직이는 점의 원점을 잡자.
		this.py = y;
	}

	@Override
	public void movePoint(int x, int y) {
		Rectangle rectangle = ((Rectangle) shape);
		rectangle.setLocation(rectangle.x + x - px, rectangle.y + y - py); // 위치 바꿈

		this.px = x; // 움직이는 점의 원점을 다시 잡아야 누적되지 않음
		this.py = y;
	}
}