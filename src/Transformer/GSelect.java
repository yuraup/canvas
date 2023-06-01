package Transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GSelect extends GTransformer {
	private AffineTransform affineTransform;
	private int px, py;
//	private int x0, y0, tx, ty;

	public GSelect(GShape shape) {
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
		px = x;
		py = y;
	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {

	}

	@Override
	public void continueTransform(int x, int y, Graphics2D graphics2d) {

	}
}
