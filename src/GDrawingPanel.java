import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Graphics graphics;
	boolean isfouse = false;

//	private int x = 10;
//	private int y = 10;
//	
	boolean isfocus = false;

//	2주 
	Point firstPointer = new Point(0, 0);
	Point secondPointer = new Point(0, 0);

	int width;
	int height;
	int minPointx;
	int minPointy;

	String shape = "Oval";

	public GDrawingPanel() {
		this.setBackground(Color.black);

		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void getShape(String selectedShape) { // 어떤 모양의 도형을 그릴지
		this.shape = selectedShape;
	}

	public void paint(Graphics g) { // JPanel 의 원래 paint함수를 대체함 (오버라이드)
		super.paint(g); // graphics == 그림 도구 //super 쓰면
		g.setColor(Color.white);

		// 2주차
		width = Math.abs(secondPointer.x - firstPointer.x); // 절댓값 리턴 == 양수 반환
		height = Math.abs(secondPointer.y - firstPointer.y);

		minPointx = Math.min(firstPointer.x, secondPointer.x);
		minPointy = Math.min(firstPointer.y, secondPointer.y);

		switch (this.shape) {

		case ("Rectangle"):
			g.drawRect(minPointx, minPointy, width, height);
			break;

		case ("Oval"):
			g.drawOval(minPointx, minPointy, width, height);
			break;

		default:
			break;
		}
		g.dispose();
	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseMoved(MouseEvent e) {
//			System.out.println("ddd" + e.getX() + e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			firstPointer.setLocation(0, 0);
			secondPointer.setLocation(0, 0);
			firstPointer.setLocation(e.getX(), e.getY());

			// 도형 드래그 움직이기 : 1주차 과제
			if ((e.getX() - secondPointer.x <= width && e.getX() - secondPointer.x >= 0)
					&& (e.getY() - secondPointer.y <= height && e.getY() - secondPointer.y >= 0)) {
				isfocus = true;

			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			width = Math.abs(secondPointer.x - firstPointer.x); // 가로
			height = Math.abs(secondPointer.y - firstPointer.y); // 세로

			minPointx = Math.min(firstPointer.x, secondPointer.x); // 둘 중 최소값 리턴 == x좌표
			minPointy = Math.min(firstPointer.y, secondPointer.y); // y좌표

			if (shape == "Rectangle") {
				Graphics g = getGraphics();
				g.drawRect(minPointx, minPointy, width, height);
				secondPointer.setLocation(e.getX(), e.getY());

				repaint();
				g.dispose();
			} else if (shape == "Oval") {
				Graphics g = getGraphics();
				g.drawOval(minPointx, minPointy, width, height);
				secondPointer.setLocation(e.getX(), e.getY());

				repaint();
				g.dispose();
			}

			if (isfocus) {
				firstPointer.x = e.getX() - width;
				firstPointer.y = e.getY() - height;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isfocus = false;
			secondPointer.setLocation(e.getX(), e.getY());
			paint(getGraphics());

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}
}