import java.awt.Graphics2D;
import java.awt.Shape;

public class GShape {
	int moveX, moveY;
	public boolean selected;
	public Shape shape;

	public GShape() {
		this.selected = false;
	}

	public void InitMove(Graphics2D graphics2D, int x, int y) {
		this.moveX = x;
		this.moveY = y;
	}

	public void Moving(Graphics2D graphics2D, int x, int y) {
//		int movingX = x - this.moveX;
//		int MovingY = y - this.moveY;
		this.moveX = x;
		this.moveY = y;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (this.selected) {
		}
	}
}