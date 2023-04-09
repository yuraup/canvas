package shapes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;

abstract public class GShape { // 추상 클래스 =/= 실제 클래스

	protected Shape shape; // 라이브러리 사용 - shape: 인터페이스 == 내용 없음

	public GShape(int x1, int y1, int x2, int y2) {
	}

	public void addPoint(int x2, int y2) { // n개의 점만큼 반복될 것임

	}

	abstract public boolean onShape(Point p); // 자식들에게 필수 메소드 명시

	abstract public void draw(Graphics graphics); // 자식들에게 필수 메소드 명시
}
