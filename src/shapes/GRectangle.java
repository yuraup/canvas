package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class GRectangle extends GShape {
	public GRectangle(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2); // 부모한테 얘기
		shape = new Rectangle(x1, y1, x2 - x1, y2 - y1);

	}

	public boolean onShape(Point p) { // 도형 움직이는 거?? 도형이 있냐고 ??
		return shape.contains(p);
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(shape);
	}

	public void addPoint(int x2, int y2) {
		Rectangle rect = (Rectangle) shape; // 현재 도형의 shape 객체를 Rectangle 타입으로 캐스팅
		rect.setSize(x2 - rect.x, y2 - rect.y); // 우측 하단의 점을 이용하여 width와 height를 계산하여 shape의 크기를 변경
	}
}