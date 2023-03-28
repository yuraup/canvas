import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private enum EDrawingState {
		eIdle, eDrawing
	}

	private EDrawingState eDrawingState;

	private Vector<Rectangle> shapes;
	private Rectangle currentShape; // 현재 도형

	private GToolBar toolbar;

	public void setToolBar(GToolBar toolbar) { // 메인프레임에서 툴바랑 연결하기(자식-자식)
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<Rectangle>();
		this.currentShape = null; // 선택된 거 없음

		this.setBackground(Color.WHITE);

		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics); // 오버라이딩
		for (Rectangle shape : shapes) {
			shape.draw(graphics);
		}
	}

	private class Rectangle {
		private int x, y, w, h;

		public Rectangle(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public void draw(Graphics graphics) {
			graphics.drawRect(x, y, w, h);

		}

		public void setDimension(int x2, int y2) {
			w = x2 - x;
			h = y2 - y;
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
				if (toolbar.getEButtonShape() == GToolBar.EButtonShape.eRectangle) {
					currentShape = new Rectangle(e.getX(), e.getY(), 0, 0);
				}
//				else if () {
//					
//				}

				eDrawingState = EDrawingState.eDrawing;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				Graphics graphics = getGraphics();
				graphics.setXORMode(getBackground());
				currentShape.draw(graphics);
				currentShape.setDimension(e.getX(), e.getY());
				currentShape.draw(graphics);
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				shapes.add(currentShape);
				currentShape = null;
				eDrawingState = EDrawingState.eIdle;
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