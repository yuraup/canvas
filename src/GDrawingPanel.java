import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private enum EDrawingState {
		eIdle, eDrawing, eMoving, eResizing, eRotating
	}

	private EDrawingState eDrawingState;

	private Vector<Rectangle> shapes; // 모든 도형 다 있음
//	private Vector<Oval> shapesO;
	private Rectangle currentShape; // 현재 도형
//	private Oval currentShapeO;

	private GToolBar toolbar;

	public void setToolBar(GToolBar toolbar) { // 메인프레임에서 툴바랑 연결하기(자식-자식)
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<Rectangle>();
//		this.shapesO = new Vector<Oval>();

		this.currentShape = null; // 선택된 거 없음
//		this.currentShapeO = null;
		this.setBackground(Color.WHITE);

		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics); // 오버라이딩

		if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) {
			for (Rectangle shape : shapes) {
				shape.draw(graphics);
			}
		}
//		else if (toolbar.getEButtonShape() == GToolBar.EShape.eOval) {
//			for (Oval shapeO : shapesO) {
//				shapeO.draw(graphics);
//			}
//		}
	}

	public Rectangle onShape(Point point) { // 도형 있니
		for (Rectangle rectangle : shapes) {
			if (rectangle.onShape(point)) { // 80번째 함수
				return rectangle;
			}
		}
		return null;
	}

	private class Rectangle {
		private int x, y, w, h;

		public Rectangle(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public boolean onShape(Point p) { // 도형 움직이는 거?? 도형이 있냐고 ??
			if ((p.x > x && p.y < x + w) && (p.x > y && p.y < y)) {
				return true;
			}
			return false;
		}

		public void draw(Graphics graphics) {
			graphics.drawRect(x, y, w, h);

		}

		public void setDimension(int x2, int y2) {
			w = x2 - x;
			h = y2 - y;
		}
	}

//	private class Oval {
//		private int x, y, w, h;
//
//		public Oval(int x, int y, int w, int h) {
//			this.x = x;
//			this.y = y;
//			this.w = w;
//			this.h = h;
//		}
//
//		public void draw(Graphics graphics) {
//			graphics.drawOval(x, y, w, h);
//
//		}
//
//		public void setDimension(int x2, int y2) {
//			w = x2 - x;
//			h = y2 - y;
//		}
//	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (toolbar.getEButtonShape() == null) { // 툴바 선택 안 됐어 -> 기존 도형 움직여
					currentShape = onShape(e.getPoint());
					if (currentShape != null) { // 도형이 있으면
						eDrawingState = EDrawingState.eMoving;
					}
				} else {
					if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) { // 제약 조건
						currentShape = new Rectangle(e.getX(), e.getY(), 0, 0);
					} else if (toolbar.getEButtonShape() == GToolBar.EShape.eOval) {
						currentShape = new Rectangle(e.getX(), e.getY(), 0, 0);
					} else if (toolbar.getEButtonShape() == GToolBar.EShape.eLine) {
						currentShape = new Rectangle(e.getX(), e.getY(), 0, 0);
					} else if (toolbar.getEButtonShape() == GToolBar.EShape.ePolygon) {
						currentShape = new Rectangle(e.getX(), e.getY(), 0, 0);
					}
				}
				eDrawingState = EDrawingState.eDrawing; // drawing 상태로 전환
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) {
					Graphics graphics = getGraphics();
					graphics.setXORMode(getBackground());

					currentShape.draw(graphics);
					currentShape.setDimension(e.getX(), e.getY());
					currentShape.draw(graphics);
				} else if (eDrawingState == EDrawingState.eMoving) {

				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				shapes.add(currentShape);
				currentShape = null;
//				currentShapeO = null;
				eDrawingState = EDrawingState.eIdle;
				toolbar.resetESelectedShape();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
}