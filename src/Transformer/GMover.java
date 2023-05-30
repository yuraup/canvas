package Transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapes.GShape;

public class GMover extends GTransformer {
private AffineTransform affineTransform;
private int x0, y0, tx, ty;

	public GMover(GShape shape) {
		super(shape);
		this.affineTransform = new AffineTransform();

	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		x0 = x;
		y0 =y;
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) {
		tx = x - x0;
		ty = y - y0;
		this.affineTransform.translate(tx, ty); //tx, ty만큼 translate
		this.shape.setShape( this.affineTransform.createTransformedShape(this.shape.getShape()));
	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d) {

	}

	@Override
	public void continueTransform(int x, int y, Graphics2D graphics2d) {

	}

}
