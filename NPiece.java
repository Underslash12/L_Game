// NPiece.java

import java.awt.Color;
import java.awt.geom.Point2D;
// import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
// import java.awt.geom.Rectangle2D.Double;
import java.awt.Graphics2D;

public class NPiece {

	private Rectangle2D[] rects = new Rectangle2D[2];
	private Color mainColor, secondaryColor;
	private double scale;
	
	private int rotation;
	private boolean flipped;

	public NPiece (double x, double y, double scale, Color c)
	{
		rects[0] = new Rectangle2D.Double(x, y, 109, 109);
		rects[1] = new Rectangle2D.Double(x + 10, y + 10, 89, 89);
		// rects[2] = new Rectangle2D.Double(x + 20, y + 20, 79, 79);
		
		// mainColor = Color.GRAY;
		// secondaryColor = Color.LIGHT_GRAY;
		
		mainColor = c;
		secondaryColor = new Color(
			c.getRed() + 25 > 255 ? 255 : c.getRed() + 25, 
			c.getGreen() + 25 > 255 ? 255 : c.getGreen() + 25, 
			c.getBlue() + 25 > 255 ? 255 : c.getBlue() + 25,
			c.getAlpha()
		);
		
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
	
	public boolean contains (double x, double y)
	{
		for (Rectangle2D r : rects) {
			if (r.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean intersects (NPiece p)
	{
		return p.contains(getCenterX(), getCenterY());
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
			if (i == 1) {
				g2d.setColor(secondaryColor);
			} else if (i == 2) {
				g2d.setColor(mainColor);
			}
			g2d.fill(rects[i]);
		}
	}
	
	// public int[]
}