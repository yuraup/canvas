import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;	
	Graphics graphics;
	boolean isfouse = false;
	
//	private int x = 10;
//	private int y = 10;
//	
//	private boolean isfocus = false;
	
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
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	
	public void getShape (String selectedShape) {
		this.shape = selectedShape;
	}
	
	public void paint(Graphics g) {
		super.paint(g); //graphics == 그림 도구 
		g.setColor(Color.white);
		
		//2주차 
		width = Math.abs(secondPointer.x - firstPointer.x);
		height = Math.abs(secondPointer.y - firstPointer.y);
		
		minPointx = Math.min(firstPointer.x, secondPointer.x);
		minPointy = Math.min(firstPointer.y, secondPointer.y);
		
		switch (this.shape) {
		
		case("Rectangle"):
			g.drawRect(minPointx, minPointy, width, height);
			break;
			
		case("Oval"):
			g.drawOval(minPointx, minPointy, width, height);
			break;
			
		default:
			break;
			
		}
		g.dispose();
	}
	
	public void savePaint() {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		firstPointer.setLocation(0,0);
		secondPointer.setLocation(0,0);
		firstPointer.setLocation(e.getX(), e.getY());
		
		//도형 드래그 움직이기 : 1주차 과제 
//		if ((e.getX() - this.x <= 40 && e.getX() - this.x >= 0) && (e.getY() - this.y <= 40 && e.getY() - this.y >= 0))
//		this.isfocus = true;	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		this.isfocus = false;
		secondPointer.setLocation(e.getX(), e.getY());
		paint(getGraphics());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		width = Math.abs(secondPointer.x - firstPointer.x);
		height = Math.abs(secondPointer.y - firstPointer.y);
		
		minPointx = Math.min(firstPointer.x, secondPointer.x);
		minPointy = Math.min(firstPointer.y, secondPointer.y);
		
		if (this.shape == "Rectangle") {
			Graphics g = getGraphics();
			g.drawRect(minPointx, minPointy, width, height);
			secondPointer.setLocation(e.getX(), e.getY());
			
			repaint();
			g.dispose();
		} else if (this.shape == "Oval") {
			Graphics g = getGraphics();
			g.drawOval(minPointx, minPointy, width, height);
			secondPointer.setLocation(e.getX(), e.getY());
			
			repaint();
			g.dispose();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
