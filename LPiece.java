// LPiece.java

import java.awt.Color;
import java.awt.geom.Point2D;
// import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
// import java.awt.geom.Rectangle2D.Double;
import java.awt.Graphics2D;

public class LPiece {

	private Rectangle2D[] rects = new Rectangle2D[6];
	private Color mainColor, secondaryColor;
	private double scale;
	
	private int rotation;
	private boolean flipped;

	public LPiece (double x, double y, double scale, Color c)
	{
		// rects[0] = new Rectangle2D.Double(x, y, 109 * scale, 399 * scale);
		rects[0] = new Rectangle2D.Double(x, y, 129 * scale, 419 * scale);
		rects[1] = new Rectangle2D.Double(x + 129, y + 290, 145 * scale, 129 * scale);
		
		rects[2] = new Rectangle2D.Double(x + 10, y + 10, 109 * scale, 399 * scale);
		rects[3] = new Rectangle2D.Double(x + 119, y + 300, 145 * scale, 109 * scale);
		
		rects[4] = new Rectangle2D.Double(x + 20, y + 20, 89 * scale, 379 * scale);
		rects[5] = new Rectangle2D.Double(x + 109, y + 310, 145 * scale, 89 * scale);
		
		mainColor = c;
		secondaryColor = new Color(
			c.getRed() + 25 > 255 ? 255 : c.getRed() + 25, 
			c.getGreen() + 25 > 255 ? 255 : c.getGreen() + 25, 
			c.getBlue() + 25 > 255 ? 255 : c.getBlue() + 25,
			c.getAlpha()
		);
		this.scale = scale;
		
		rotation = 0;
		flipped = false;
	}
	
	public double getX () 
	{
		return rects[0].getX();
	}
	
	public double getY () 
	{
		return rects[0].getY();
	}
	
	public double getWidth ()
	{
		return rects[0].getWidth();
	}
	
	public double getHeight ()
	{
		return rects[0].getHeight();
	}
	
	public double getCenterX ()
	{
		return rects[0].getWidth() / 2 + getX();
	}
	
	public double getCenterY ()
	{
		return rects[0].getHeight() / 2 + getY();
	}
	
	public Color getColor ()
	{
		return mainColor;
	}
	
	public Rectangle2D getBoundingBox ()
	{
		double x1 = rects[0].getX();
		double x2 = rects[1].getX();
		double y1 = rects[0].getY();
		double y2 = rects[1].getY();
		
		return new Rectangle2D.Double(
			Math.min(x1, x2),
			Math.min(y1, y2),
			Math.max(x1 + rects[0].getWidth(), x2 + rects[1].getWidth()) - Math.min(x1, x2),
			Math.max(y1 + rects[0].getHeight(), y2 + rects[1].getHeight()) - Math.min(y1, y2)
		);
	}
	
	public void rotate90 (int times)
	{
		for (int i = 0; i < times; i++) {
			for (int j = 0; j < rects.length; j++) {
				double  x1 = rects[j].getX(), 
						y1 = rects[j].getY(), 
						x2 = rects[j].getX() + rects[j].getWidth(),
						y2 = rects[j].getY() + rects[j].getHeight();
				
				double[] p1 = rotate90Point(x1, y1, getCenterX(), getCenterY());
				double[] p2 = rotate90Point(x2, y2, getCenterX(), getCenterY());
				
				rects[j] = createRectangleFromPoints(
					p1[0],
					p1[1],
					p2[0],
					p2[1]
				);
			}
		}
		
		rotation += times;
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
				getCenterX() * 2 - rects[i].getX(),
				rects[i].getY(),
				getCenterX() * 2 - rects[i].getX() - rects[i].getWidth(),
				rects[i].getY() + rects[i].getHeight()
			);
		}
		
		flipped = !flipped;
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
	
	
	public boolean intersects (LPiece p)
	{
		double cx = getCenterX();
		double cy = getCenterY();
		
		boolean horizontal = rotation % 2 == 1;
		
		boolean isLeft = flipped;
		
		// checks if center square is inside p
		if (p.contains(cx, cy)) {
			return true;
		}
		
		// checks if above and below squares are inside p
		if (horizontal) {
			if (p.contains(cx - 145, cy) || p.contains(cx + 145, cy)) {
				return true;
			}
		} else {
			if (p.contains(cx, cy - 145) || p.contains(cx, cy + 145)) {
				return true;
			}
		} 
		
		// checks if sticking out bit is inside p
		if (p.contains(rects[1].getWidth() / 2 + rects[1].getX(), rects[1].getHeight() / 2 + rects[1].getY())) {
			return true;
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
	
	public void setColor (Color c)
	{
		mainColor = c;
		secondaryColor = new Color(
			c.getRed() + 25 > 255 ? 255 : c.getRed() + 25, 
			c.getGreen() + 25 > 255 ? 255 : c.getGreen() + 25, 
			c.getBlue() + 25 > 255 ? 255 : c.getBlue() + 25,
			c.getAlpha()
		);
	}
	
	public void paintComponent (Graphics2D g2d)
	{
		g2d.setColor(mainColor);
		for (int i = 0; i < rects.length; i++) {
			if (i == 2) {
				g2d.setColor(secondaryColor);
			} else if (i == 4) {
				g2d.setColor(mainColor);
			}
			g2d.fill(rects[i]);
		}
	}
	
	// public int[]
}