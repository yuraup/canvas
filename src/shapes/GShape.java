package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

import main.GConstants.EAnchors;

abstract public class GShape {
	protected Shape shape;
	private GAnchors gAnchors;
	private boolean bSlected;

	public GShape() {
		this.bSlected = false;
		this.gAnchors = new GAnchors();
	}

	abstract public GShape clone(); // GShape은 뭘 만들지 모름. 도형이 다 다르다.

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public boolean onShape(Point p) {
		return shape.contains(p);
	}

	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(shape);
		if (this.bSlected) {
			this.gAnchors.draw(graphics2D, this.shape.getBounds());
		}
	}

	public void setSelected(boolean bSelected) { // setter
		this.bSlected = bSelected;

	};

	public EAnchors onShape(int x, int y) {
		if (this.bSlected) {
			EAnchors eAnchor = this.gAnchors.onShape(x, y);
			if (eAnchor != null) {
				return eAnchor;
			}
		}
		if (this.shape.contains(x, y)) {
			return EAnchors.MM;
		}
		;
		return null; // 아무데도 없다
	}

	public abstract void setShape(int x1, int y1, int x2, int y2);

	public abstract void resizePoint(int x2, int y2);

	public abstract void movePoint(int x, int y);

	public void addPoint(int x, int y) {
	}

	public abstract void setPoint(int x, int y); // 없어도 되고 있어도 됨 =/= abstract

}