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

	public LPiece (double x, double y, double scale, Color c)
	{
		// rects[0] = new Rectangle2D.Double(x, y, 109 * scale, 399 * scale);
		rects[0] = new Rectangle2D.Double(x, y, 129 * scale, 419 * scale);
		rects[1] = new Rectangle2D.Double(x + 129, y + 419 - 129, (129 + 16) * scale, 129 * scale);
		color = c;
	}
	
	public double getX() 
	{
		return rects[0].getX();
	}
	
	public double getY() 
	{
		return rects[0].getY();
	}
	
	public void rotate90 (int times)
	{
		
	}
	
	public void flipHorizontally ()
	{
		
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