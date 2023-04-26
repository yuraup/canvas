package shapes;

import java.awt.geom.Line2D;

public class GLine extends GShape {
	public GLine() {
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		this.shape = new Line2D.Double(x1, y1, x2, y2);
	}

	@Override
	public void resizePoint(int x2, int y2) {
		Line2D line2D = ((Line2D) shape);
		line2D.setLine(line2D.getX1(), line2D.getY1(), x2, y2);
	}

	@Override
	public GShape clone() {
		return new GLine();
	}

	@Override
	public void movePoint(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPoint(int x, int y) {
		// TODO Auto-generated method stub

	}

}