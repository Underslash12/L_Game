// NPiece.java

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class NPiece implements Piece {

	// private Rectangle2D boundingRect;
	private Rectangle2D[] rects = new Rectangle2D[2];
	private Color mainColor, secondaryColor;
	private double scale;
	
	private int rotation;
	private boolean flipped;

	public NPiece (double x, double y, double scale, Color c)
	{
		// boundingRect = new Rectangle2D.Double(x, y, 129, 129);
		rects[0] = new Rectangle2D.Double(x + 10, y + 10, 109, 109);
		rects[1] = new Rectangle2D.Double(x + 20, y + 20, 89, 89);
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
		return rects[0].getX() - 10;
	}
	
	public double getY () 
	{
		return rects[0].getY() - 10;
	}
	
	public double getWidth ()
	{
		return rects[0].getWidth() + 20;
	}
	
	public double getHeight ()
	{
		return rects[0].getHeight() + 20;
	}
	
	public double getCenterX ()
	{
		return getWidth() / 2 + getX();
	}
	
	public double getCenterY ()
	{
		return getHeight() / 2 + getY();
	}
	
	public Color getColor ()
	{
		return mainColor;
	}
	
	public Rectangle2D getBoundingBox ()
	{
		// return rects[0];
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
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
	
	public ArrayList<Point2D> getCenters ()
	{
		ArrayList<Point2D> centers = new ArrayList<Point2D>();
		centers.add(new Point2D.Double(getCenterX(), getCenterY()));
		return centers;
	}
	
	// same in LPiece
	public boolean intersects (Piece p)
	{
		boolean intersects = false;
		for (Point2D p2d : p.getCenters()) {
			if (contains(p2d.getX(), p2d.getY())) {
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
		double initx = getX();
		double inity = getY();
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
	
	public void flip () {}
	public void rotate90 (int times) {}
}