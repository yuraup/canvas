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

	private enum EDrawingState {
		eIdle, eDrawing, eMoving, eSelecting, eResizing, eRotating
	}

	private EDrawingState eDrawingState;

	private Vector<GShape> shapes; // 모든 도형 다 있음
	private GShape currentShape; // 현재 도형

	private GToolBar toolbar;

	public void setToolBar(GToolBar toolbar) { // 메인프레임에서 툴바랑 연결하기(자식-자식)
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		super();
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

		if (toolbar.getESelectedShape() == GToolBar.EShape.eRectangle) {
			for (GShape shape : shapes) {
				shape.draw(graphics);
			}
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
			currentShape = toolbar.getESelectedShape().getGShape().clone();
			currentShape.setShape(x, y, x, y);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape = toolbar.getESelectedShape().getGShape().clone();
			currentShape.setShape(x, y, x, y);
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape.setPoint(x, y);
		}
	}

	public void keepTransforming(int x, int y) { // 두 점 가지고 하는 작업
		Graphics graphics = getGraphics();
		graphics.setXORMode(getBackground());

		if (eDrawingState == EDrawingState.eDrawing) {

			currentShape.draw(graphics);
			currentShape.resizePoint(x, y);
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

	public void continueTransforming(int x, int y) {
		currentShape.addPoint(x, y);
	}

	public void finalizeTransforming(int x, int y) {
		if (eDrawingState == EDrawingState.eSelecting) {
			Graphics graphics = getGraphics();
			graphics.setXORMode(getBackground());
			currentShape.draw(graphics);
		} else if (eDrawingState == EDrawingState.eDrawing) {
			shapes.add(currentShape);
		} else if (eDrawingState == EDrawingState.eMoving) {
		}
		currentShape = null;
		toolbar.resetESelectedShape();
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
				if (toolbar.getESelectedShape().getGShape() instanceof GPolygon) {
					// polygon이 아니면!
					prepareTransforming(e.getX(), e.getY()); // currentShape 설정
					eDrawingState = EDrawingState.eDrawing; // drawing 상태로 전환
				}
			} else if (eDrawingState == EDrawingState.eDrawing) { // 2번째부터 찍을 때
				if (toolbar.getESelectedShape().getGShape() instanceof GPolygon) {
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
				if (toolbar.getESelectedShape() == EShape.eSelect) {
					currentShape = onShape(e.getPoint()); // 그림 있냐고
					if (currentShape == null) { // 그림이 있으면 셀렉션 해야 됨
						eDrawingState = EDrawingState.eSelecting;
						prepareTransforming(e.getX(), e.getY());
					} else { // 그림이 없으면
						// resize, rotate, move 3개의 동작을 해야 하는 곳
						eDrawingState = EDrawingState.eMoving;
						prepareTransforming(e.getX(), e.getY());
					}
				} else { // 셀렉트가 아니면, 폴리곤이 아닐 경우
					if (!(toolbar.getESelectedShape().getGShape() instanceof GPolygon)) {
						eDrawingState = EDrawingState.eDrawing;
						prepareTransforming(e.getX(), e.getY());
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (!(toolbar.getESelectedShape().getGShape() instanceof GPolygon)) {
					keepTransforming(e.getX(), e.getY());
				}
			} else if (eDrawingState == EDrawingState.eSelecting) {
				keepTransforming(e.getX(), e.getY());
			}

			else if (eDrawingState == EDrawingState.eMoving) {
				keepTransforming(e.getX(), e.getY());
			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if ((toolbar.getESelectedShape().getGShape() instanceof GPolygon)) {
					keepTransforming(e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eDrawing) {
				if (!(toolbar.getESelectedShape().getGShape() instanceof GPolygon)) {
					finalizeTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			} else if (eDrawingState == EDrawingState.eSelecting) { // select일 때 끝 안 해서
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