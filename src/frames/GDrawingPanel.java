package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import shapes.GSelect;
import shapes.GShape;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private enum EDrawingState {
		eIdle, eDrawing, eMoving, eResizing, eRotating
	}

	private EDrawingState eDrawingState;

	private Vector<GShape> shapes; // 모든 도형 다 있음
	private GShape currentShape; // 현재 도형

	private GToolBar toolbar;

	public void setToolBar(GToolBar toolbar) { // 메인프레임에서 툴바랑 연결하기(자식-자식)
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<GShape>();
		this.currentShape = null; // 선택된 거 없음
		this.setBackground(Color.WHITE);

		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);

	}

	public void paint(Graphics graphics) {
		super.paint(graphics); // 오버라이딩

		if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) {
			for (GShape shape : shapes) {
				shape.draw(graphics);
			}
		}
	}

	public GShape onShape(Point point) { // 도형 있니
		for (GShape shape : shapes) {
			if (shape.onShape(point)) { // 80번째 함수
				return shape;
			}
		}
		return null;
	}

	public void prepareTransforming(int x, int y) {
		currentShape = toolbar.getEButtonShape().getGShape(); // 코드 정리
		currentShape.setShape(x, y, x, y);
//
//		if (toolbar.getEButtonShape() == GToolBar.EShape.eSelect) { // 제약 조건
//			currentShape = new GSelect(x, y, x, y);
//		} else if (toolbar.getEButtonShape() == GToolBar.EShape.eRectangle) {
//			currentShape = new GRectangle(x, y, x, y);
//		} else if (toolbar.getEButtonShape() == GToolBar.EShape.eOval) {
//			currentShape = new GOval(x, y, x, y);
//		} else if (toolbar.getEButtonShape() == GToolBar.EShape.eLine) {
//			currentShape = new GLine(x, y, x, y);
//		} else if (toolbar.getEButtonShape() == GToolBar.EShape.ePolygon) {
//			currentShape = new GRectangle(x, y, x, y);
//		}
	}

	public void keepTransforming(int x, int y) { // 두 점 가지고 하는 작업
		Graphics graphics = getGraphics();
		graphics.setXORMode(getBackground());

		currentShape.draw(graphics);
		currentShape.movePoint(x, y);
		currentShape.draw(graphics);
	}

	public void finalizeTransforming(int x, int y) {
		if (currentShape instanceof GSelect) {
			Graphics graphics = getGraphics();
			graphics.setXORMode(getBackground());
			currentShape.draw(graphics);
		} else {
			shapes.add(currentShape);
		}
		currentShape = null;
		eDrawingState = EDrawingState.eIdle;
		toolbar.resetESelectedShape();
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
					if (currentShape != null) { // 도형이 있으면
						eDrawingState = EDrawingState.eMoving;
					}
				} else { // 그림 그릴 준비
					prepareTransforming(e.getX(), e.getY()); // currentShape 설정
					eDrawingState = EDrawingState.eDrawing; // drawing 상태로 전환
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				keepTransforming(e.getX(), e.getY());

			} else if (eDrawingState == EDrawingState.eMoving) {

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				finalizeTransforming(e.getX(), e.getY());
			}
//			eDrawingState = EDrawingState.eIdle;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
}