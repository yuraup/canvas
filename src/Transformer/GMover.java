package Transformer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GMover extends GTransformer {
	private AffineTransform affineTransform;
	private int px, py;
//	private int x0, y0, tx, ty;

	public GMover(GShape shape) {
		super(shape);
		this.affineTransform = new AffineTransform();

	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		px = x;
		py = y;
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2D) {
		// 두 가지 방법이 있음
		this.shape.draw(graphics2D); // 원래 도형
		this.affineTransform.setToTranslation(x - px, y - py);
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape.getShape());
		this.shape.setShape(transformedShape); // 움직인 도형
		this.shape.draw(graphics2D);
		px = x;
		py = y;
		// 무버 코드 이상함
	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
//		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape.getShape());
//		this.shape.setShape(transformedShape);
		this.shape.setSelected(true);
		this.shape.draw(graphics2d);
	}

	@Override
	public void continueTransform(int x, int y, Graphics2D graphics2d) {

	}

}
