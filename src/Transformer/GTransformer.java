package Transformer;

import java.awt.Graphics2D;
import java.util.Vector;

import shapes.GShape;

abstract public class GTransformer {
	protected GShape shape;

	public GTransformer(GShape shape) {
		this.shape = shape;
	}

	abstract public void initTransform(int x, int y, Graphics2D graphics2d);

	abstract public void keepTransform(int x, int y, Graphics2D graphics2d);

	abstract public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes);

	abstract public void continueTransform(int x, int y, Graphics2D graphics2d);
}
