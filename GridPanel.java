// TestPanel.java

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.Color;
import java.awt.event.*;

public class GridPanel extends JPanel implements MouseMotionListener, MouseListener{
	
	boolean draw = false;
	// Rectangle2D[] orangeLMain = new Rectangle2D[4];
	// Rectangle2D[] orangeLHelper;
	LPiece orange;
	boolean moveable;
	
	double xOffset;
	double yOffset;
	double xScale;
	double yScale;
	
	public GridPanel() 
	{
		super();
		int bg = 246;
		setBackground(new Color(bg, bg, bg));
		addMouseMotionListener(this);
		addMouseListener(this);
		
		xOffset = 0;
		yOffset = 0;
		xScale = 1;
		yScale = 1;
		
		// orangeLMain[0] = new Rectangle2D.Double(155 + 36, 155 + 36, 109 * xScale, 109 * yScale);
		// orange = new LPiece(155 + 36, 155 + 36, 1, new Color(0, 0, 200));
		orange = new LPiece(155 + 26, 155 + 26, 1, new Color(0, 0, 200));
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
		
		
		g2d.translate(xOffset, yOffset);
		
		if (draw) {
			g2d.setColor(new Color(200, 200, 200));
			Rectangle2D out1 = new Rectangle2D.Double(0 + xOffset, 0 + yOffset, 640 * xScale, 640 * yScale);
			g2d.fill(out1);
			
			g2d.setColor(new Color(120, 120, 120));
			Rectangle2D out2 = new Rectangle2D.Double(20 + xOffset, 20 + yOffset, 600 * xScale, 600 * yScale);
			g2d.fill(out2);
			
			g2d.setColor(new Color(246, 246, 246));
			for (int j = 0; j < 4; j++) {
				for (int i = 0; i < 4; i++) {
					// g2d.setColor(new Color(120, 120, 120));
					Rectangle2D tempBoxes = new Rectangle2D.Double(145 * i + 36 + xOffset, 145 * j + 36 + yOffset, 129 * xScale, 129 * yScale);
					g2d.fill(tempBoxes);
				}
			}
		}
		
		// g2d.setColor(new Color(0, 0, 200));
		orange.paintComponent(g2d);
    }
	
	// Used mouse events
	public void mouseMoved(MouseEvent e) 
	{
		// System.out.println("test1");
		if (moveable) {
			// System.out.println("test2");
			// orange = new Rectangle2D.Double(e.getX() - 55, e.getY() - 55, 109, 109);
			orange.translateTo(e.getX(), e.getY());
			repaint();
		}
    }
	
	public void mousePressed(MouseEvent e) 
	{
		// System.out.println("test3");
		if (orange.contains(e.getX(), e.getY())) {
			// System.out.println("test4");
			moveable = !moveable;
			mouseMoved(e);
		}
	}
	
	public void mouseReleased(MouseEvent e) 
	{
		// moveable = false;
	}
	
	
	// Unusued mouse events
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
	
	public void draw(boolean state) 
	{
		draw = state;
		repaint();
	}
}