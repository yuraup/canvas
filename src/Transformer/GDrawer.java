package Transformer;

import java.awt.Graphics2D;

import shapes.GShape;

public class GDrawer extends GTransformer {

	public GDrawer(GShape shape) {
		super(shape);
	}

	public void initTransform(int x, int y, Graphics2D graphics2D) {
		this.shape.setShape(x, y, x, y);
	}

	public void keepTransform(int x, int y, Graphics2D graphics2D) {
		this.shape.draw(graphics2D);
		this.shape.resizePoint(x, y);
		this.shape.draw(graphics2D); // 그리고 지우기
	}

	public void finalizeTransform(int x, int y, Graphics2D graphics2D) {
//		this.shape.movePoint(x, y);
	}

	@Override
	public void continueTransform(int x, int y, Graphics2D graphics2D) {

	}

}
