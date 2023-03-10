import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;	
	private int x = 10;
	private int y = 10;
	
	private boolean isfocus = false;
	
	public GDrawingPanel() {
		this.setBackground(Color.PINK);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics); //graphics == 그림 도구 
		graphics.setColor(Color.red);
		graphics.drawRect(this.x, this.y, 40, 40);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if ((e.getX() - this.x <= 40 && e.getX() - this.x >= 0) && (e.getY() - this.y <= 40 && e.getY() - this.y >= 0))
		this.isfocus = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.isfocus = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isfocus) {
			this.x = e.getX() - 20;
			this.y = e.getY() - 20;
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
