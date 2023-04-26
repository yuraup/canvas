package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import frames.GToolBar.EShape;
import shapes.GPolygon;
import shapes.GShape;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private enum EDrawingState { // 어떤 상태를 가질 수 있는지
		eIdle, eDrawing, eSelecting, eMoving, eResizeing, eRotating
	}

	private EDrawingState eDrawingState;// 어떤 상태인지

	private Vector<GShape> shapes; // 그린 사각형 저장할 공간 / 기존 그림 여기에 저장되어 있음
	private GShape currentShape; // 현재 작업 / 잠깐잠깐 쓸 포인터

	// association
	private GToolBar toolBar;

	public void setToolBar(GToolBar toolBar) { // 친구는 부모가 연결해줘야 함 ( MainFrame에서 연결 )
		this.toolBar = toolBar;
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
		// super.paint를 해야.. 원래 JPanel이 할 paint도 하고, 내 것도 추후로 하지
		// 오버라이딩은, 확장으로 갈 것이냐, 리플레이스로 갈 것이냐의 문제가 있어
		for (GShape shape : shapes) {
			shape.draw(graphics);
		}
	}

	public GShape onShape(Point point) { // 도형 있는지 확인
		for (GShape shape : shapes) {
			if (shape.onShape(point)) {
				return shape;
			}
		}
		return null;
	}

	public void prepareTransforming(int x, int y) {
		if (eDrawingState == EDrawingState.eDrawing) {
			// 툴바에서 도형 가져오고 복제해서 사용하기 - n개를 새로 만들자
			currentShape = toolBar.getESelectedShape().getGShape().clone();
			currentShape.setShape(x, y, x, y); // 원점을 잡은 것
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape = toolBar.getESelectedShape().getGShape().clone();
			currentShape.setShape(x, y, x, y); // 원점을 잡은 것
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape.setPoint(x, y);
		}

	}

	public void keepTransforming(int x, int y) { // 두 점 가지고 하는 작업
		Graphics graphics = getGraphics();
		graphics.setXORMode(getBackground());

		if (eDrawingState == EDrawingState.eDrawing) {
			currentShape.draw(graphics);
			currentShape.resizePoint(x, y);// width height 계산하는 함수
			currentShape.draw(graphics);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape.draw(graphics);
			currentShape.resizePoint(x, y);
			currentShape.draw(graphics);
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape.draw(graphics);
			currentShape.movePoint(x, y);
			currentShape.draw(graphics);
		}
	}

	public void continueTransforming(int x, int y) { // 계속하여 point를 추가시키는 것 (click마다)
		currentShape.addPoint(x, y);
	}

	public void finalizeTransforming(int x, int y) {
		if (eDrawingState == EDrawingState.eDrawing) {
			shapes.add(currentShape);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			// 지운다고 / 그리고 저장 안함
			Graphics graphics = getGraphics();
			graphics.setXORMode(getBackground());
			currentShape.draw(graphics);
		} else if (eDrawingState == EDrawingState.eMoving) {

		}
		currentShape = null;
		toolBar.resetESelectedShape();
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
				if (toolBar.getESelectedShape().getGShape() instanceof GPolygon) { // polygon이 아니면!
					eDrawingState = EDrawingState.eDrawing;
					prepareTransforming(e.getX(), e.getY());
				}
			} else if (eDrawingState == EDrawingState.eDrawing) {
//            continueTransforming(e.getX(), e.getY());
				if ((toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
					continueTransforming(e.getX(), e.getY());
				}
			}
		}

		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (toolBar.getESelectedShape() == EShape.eSelect) {
					currentShape = onShape(e.getPoint()); // 그림 있냐고
					if (currentShape == null) { // 그림이 있으면 셀렉션 해야 됨
						eDrawingState = EDrawingState.eSelecting;
						prepareTransforming(e.getX(), e.getY());
					} else { // 그림이 없으면
								// resize, rotate, move 3개의 동작을 해야 하는 곳
						eDrawingState = EDrawingState.eMoving; // move로 상태 변경
						prepareTransforming(e.getX(), e.getY());
					}

				} else { // 셀렉트가 아니면, 폴리곤이 아닐 경우
					if (!(toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
						eDrawingState = EDrawingState.eDrawing;
						prepareTransforming(e.getX(), e.getY());
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
//            keepTransforming(e.getX(), e.getY());
				if (!(toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
					keepTransforming(e.getX(), e.getY());
				}
			} else if (eDrawingState == EDrawingState.eSelecting) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eMoving) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if ((toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
					keepTransforming(e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (!(toolBar.getESelectedShape().getGShape() instanceof GPolygon)) {
					finalizeTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			} else if (eDrawingState == EDrawingState.eSelecting) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			} else if (eDrawingState == EDrawingState.eMoving) {
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