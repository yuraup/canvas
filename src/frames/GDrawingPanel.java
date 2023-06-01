package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import Transformer.GDrawer;
import Transformer.GMover;
import Transformer.GSelect;
import Transformer.GTransformer;
import main.GConstants.EAnchors;
import main.GConstants.EShape;
import main.GConstants.EUserAction;
import shapes.GShape;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

//	private EUserAction eUserAction; // 제약 조건

	private enum EDrawingEvent {
		eStart, eMoving, eCont, eEnd
	}

	private enum EDrawingState { // 어떤 상태를 가질 수 있는지
		eIdle, eTransforming
	}

	private EDrawingState eDrawingState;// 어떤 상태인지

	private Vector<GShape> shapes;
	private GShape currentShape;

	// association
	private GToolBar toolBar;

	public void setToolBar(GToolBar toolBar) { // 친구는 부모가 연결해줘야 함 ( MainFrame에서 연결 )
		this.toolBar = toolBar;
	}

	private GTransformer transformer;

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
		for (GShape shape : shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}
//
//	public GShape onShape(Point point) { // 도형 있는지 확인
//		for (GShape shape : shapes) {
//			if (shape.onShape(point)) {
//				return shape;
//			}
//		}
//		return null;
//	}

	public void initTransforming(int x, int y) { // 어떤 트랜스포머를 쓸지를 판단함.
		// 우선순위 1. 툴바가 눌렸냐 안 눌렸냐 - select는 안 눌렸다는 뜻

		Graphics2D graphics2d = (Graphics2D) this.getGraphics(); // 트랜스포머가 그림을 그리니까 필요함

		// 크게 selection과 draw로 나뉨
		if (this.toolBar.getESelectedShape() == EShape.eSelect) {// selection
			EAnchors eSelectedAnchor = this.onShape(x, y);
			if (eSelectedAnchor == null) {
				this.clearSelection();
				this.transformer = new GSelect(this.currentShape);
				this.transformer.initTransform(x, y, graphics2d);
				// selector
			} else {
				switch (eSelectedAnchor) {
				case MM:
					this.transformer = new GMover(this.currentShape);
					this.transformer.initTransform(x, y, graphics2d);
					break;
				case RR: // rotate
					break;
				default: // resize
					break;
				}
			}
		} else { // draw
			if (this.toolBar.getESelectedShape().getEUserAction() == EUserAction.e2Point) {
				this.currentShape = this.toolBar.getESelectedShape().getGShape().clone();
				this.transformer = new GDrawer(this.currentShape);
				this.transformer.initTransform(x, y, graphics2d); // 그리기 시작
			} else { // polygon

			}
		}
	}

	public void keepTransforming(int x, int y) { // 두 점 가지고 하는 작업
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(getBackground());
		this.transformer.keepTransform(x, y, graphics2d);
	}

	public void continueTransforming(int x, int y) { // 계속하여 point를 추가시키는 것 (click마다)
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		this.transformer.continueTransform(x, y, graphics2D);
	}

	public void finalizeTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		// unselect current selected Shapes
		this.transformer.finalizeTransform(x, y, graphics2D, this.shapes);
		this.toolBar.resetESelectedShape();
	}

	private EAnchors onShape(int x, int y) {
		for (GShape gShape : this.shapes) { // 루틴 돌리면서 모든 그림한테 다 물어봄
			EAnchors eAnchor = gShape.onShape(x, y);
			if (eAnchor != null) { // selection O
				this.currentShape = gShape;
				return eAnchor;
			}
		}
		return null;
	}

	private void clearSelection() {
	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				initTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) {
				finalizeTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				initTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eTransforming;
			}

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) { // 상태 이해를 위해 적어둠. 없어도 무방함.
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) {
				finalizeTransforming(e.getX(), e.getY());
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