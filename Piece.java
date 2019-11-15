// Piece.java

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.util.ArrayList;

public interface Piece {
	
	public double getX (); 
	public double getY (); 
	public double getWidth (); 
	public double getHeight (); 
	public double getCenterX (); 
	public double getCenterY (); 
	public Color getColor ();
	public Rectangle2D getBoundingBox ();
	public ArrayList<Point2D> getCenters (); 
	
	public void rotate90 (int times);
	public void flip ();
	public boolean contains (double x, double y);
	public boolean intersects (Piece p);
	public void translate (double dx, double dy);
	public void translateTo (double x, double y);
	public void setColor (Color c);
	public void paintComponent (Graphics2D g2d);
}