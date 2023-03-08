import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public GDrawingPanel() {
		this.setBackground(Color.PINK);
		
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics); //graphics == 그림 도구 
		graphics.setColor(Color.red);
		graphics.drawRect(10, 10, 40, 40);

	}
}
