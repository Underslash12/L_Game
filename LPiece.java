// LPiece.java

import java.awt.Color;
import java.awt.geom.Point2D;
// import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
// import java.awt.geom.Rectangle2D.Double;
import java.awt.Graphics2D;

public class LPiece {

	private Rectangle2D[] rects = new Rectangle2D[2];
	private Color color;
	private double scale;

	public LPiece (double x, double y, double scale, Color c)
	{
		// rects[0] = new Rectangle2D.Double(x, y, 109 * scale, 399 * scale);
		rects[0] = new Rectangle2D.Double(x, y, 129 * scale, 419 * scale);
		rects[1] = new Rectangle2D.Double(x + 129, y + 419 - 129, (129 + 16) * scale, 129 * scale);
		color = c;
		this.scale = scale;
	}
	
	public double getX () 
	{
		return rects[0].getX();
	}
	
	public double getY () 
	{
		return rects[0].getY();
	}
	
	public double getCenterX ()
	{
		return rects[0].getWidth() / 2;
	}
	
	public double getCenterY ()
	{
		return rects[0].getHeight() / 2;
	}
	
	public void rotate90 (int times)
	{
		for (int i = 0; i < times; i++) {
			for (int j = 0; j < rects.length; j++) {
				double  x1 = rects[j].getX(), 
						y1 = rects[j].getY(), 
						x2 = rects[j].getX() + rects[j].getWidth(),
						y2 = rects[j].getY() + rects[j].getHeight();
				
				double[] p1 = rotate90Point(x1, y1, getCenterX() + getX(), getCenterY() + getY());
				double[] p2 = rotate90Point(x2, y2, getCenterX() + getX(), getCenterY() + getY());
				
				rects[j] = createRectangleFromPoints(
					p1[0],
					p1[1],
					p2[0],
					p2[1]
				);
			}
		}
	}
	
	// rotate 90 degrees formula is (x, y) -> (-(y - b) + a, (x - a) + b)
	private double[] rotate90Point (double x, double y, double a, double b)
	{
		return new double[]{-(y - b) + a, (x - a) + b};
	}
	
	public void flip ()
	{
		for (int i = 0; i < rects.length; i++) {
			rects[i] = createRectangleFromPoints(
				-(rects[i].getX() - (getCenterX() + getX())) + (getCenterX() + getX()),
				rects[i].getY(),
				-(rects[i].getX() + rects[i].getWidth() - (getCenterX() + getX())) + (getCenterX() + getX()),
				rects[i].getY() + rects[i].getHeight()
			);
		}
		// double x = rects[0].getX();
		// double x2 = rects[1].getX();
		// double w = rects[0].getWidth();
		// double w2 = rects[1].getWidth();
		// double newX = 0;
		// newX = x > x2 ? w + w2 : -w - w2;
		// rects[1] = new Rectangle2D.Double(rects[1].getX() + newX, rects[1].getY(), rects[1].getWidth(), rects[1].getHeight());
	}
	
	private Rectangle2D createRectangleFromPoints(double x1, double y1, double x2, double y2)
	{
		return new Rectangle2D.Double(
			Math.min(x1, x2),
			Math.min(y1, y2),
			Math.abs(x1 - x2),
			Math.abs(y1 - y2)
		);
	}
	
	public boolean contains (double x, double y)
	{
		for (Rectangle2D r : rects) {
			if (r.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	public void translate (double dx, double dy)
	{
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new Rectangle2D.Double(
				rects[i].getX() + dx, 
				rects[i].getY() + dy, 
				rects[i].getWidth(), 
				rects[i].getHeight()
			);
		}
	}
	
	public void translateTo (double x, double y)
	{
		double initx = rects[0].getX();
		double inity = rects[0].getY();
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new Rectangle2D.Double(
				rects[i].getX() - initx + x, 
				rects[i].getY() - inity + y, 
				rects[i].getWidth(), 
				rects[i].getHeight()
			);
		}
	}
	
	public void paintComponent (Graphics2D g2d)
	{
		g2d.setColor(color);
		for (Rectangle2D r : rects) {
			g2d.fill(r);
		}
	}
	
	// public int[]
}