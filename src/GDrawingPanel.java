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
	private String choice;
	private Vector<Shape> shapes; // 모든 도형 다 있음
	private Shape currentShape; // 현재 도형
	private GToolBar toolbar; // 연결

	public void setToolBar(GToolBar toolbar) { // 메인프레임에서 툴바랑 연결하기(자식-자식)
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<Shape>();
		this.currentShape = null; // 선택된 거 없음
		this.setBackground(Color.WHITE);

		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics); // 오버라이딩

		if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) {
			for (Shape shape : shapes) {
				shape.draw(graphics);
			}
		}
	}

	public Shape onShape(Point point) { // 도형 있니
		for (Shape shape : shapes) {
			if (shape.onShape(point)) {
				return shape;
			}
		}
		return null;// 점 밑에 도형 없음
	}

	private class Shape {
		private int x, y, w, h;
		int gapX, gapY;

		public Shape(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public boolean onShape(Point p) {
			if ((p.x > x && p.x < x + w) && (p.y > y && p.y < y + h)) {
				return true;
			}
			return false;
		}

		public void draw(Graphics graphics) {
			if (choice.equals("a")) {
				graphics.drawRect(x, y, w, h);
			} else if (choice.equals("b")) {
				graphics.drawOval(x, y, w, h);
			}
		}

		public void setDimensionDraw(int x2, int y2) {
			w = x2 - x;
			h = y2 - y;
		}

		public void setDimensionMove(int x2, int y2) {
			x = x2 - gapX;
			y = y2 - gapY;
		}

		public void setGap(int x2, int y2) {
			gapX = x2 - x;
			gapY = y2 - y;
		}
	}

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
					if (currentShape != null) { // 도형이 있으면 움직여
						eDrawingState = EDrawingState.eMoving;
						currentShape.setGap(e.getX(), e.getY());
					}
				} else {
					if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) { // 제약 조건
						currentShape = new Shape(e.getX(), e.getY(), 0, 0);
						choice = "a";
						eDrawingState = EDrawingState.eDrawing;
					} else if (toolbar.getEButtonShape() == GToolBar.EShape.eOval) {
						eDrawingState = EDrawingState.eDrawing;
						choice = "b";
						currentShape = new Shape(e.getX(), e.getY(), 0, 0);
					} else if (toolbar.getEButtonShape() == GToolBar.EShape.eLine) {
						choice = "c";
						eDrawingState = EDrawingState.eDrawing;
						currentShape = new Shape(e.getX(), e.getY(), 0, 0);
					} else if (toolbar.getEButtonShape() == GToolBar.EShape.ePolygon) {
						eDrawingState = EDrawingState.eDrawing;
						currentShape = new Shape(e.getX(), e.getY(), 0, 0);
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Graphics graphics = getGraphics();
			graphics.setXORMode(getBackground());
			if (eDrawingState == EDrawingState.eDrawing) {
				currentShape.draw(graphics);
				currentShape.setDimensionDraw(e.getX(), e.getY());
				currentShape.draw(graphics);
			} else if (eDrawingState == EDrawingState.eMoving) {
				currentShape.draw(graphics);
				currentShape.setDimensionMove(e.getX(), e.getY());
				currentShape.draw(graphics);
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				shapes.add(currentShape);
				currentShape = null;
				eDrawingState = EDrawingState.eIdle;
				toolbar.resetESelectedShape();
			} else if (eDrawingState == EDrawingState.eMoving) {
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