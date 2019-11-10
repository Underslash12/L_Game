// TestPanel.java

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.Color;
import java.awt.event.*;

public class GridPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener{
	
	boolean draw = false;
	// Rectangle2D[] orangeLMain = new Rectangle2D[4];
	// Rectangle2D[] orangeLHelper;
	LPiece orange, orangeShadow, orangeTile;
	LPiece blue, blueShadow, blueTile;
	
	boolean isOrangeMoveable, isBlueMoveable;
	
	int scrollSensitivity;
	double scrollCounter;
	
	double xOffset;
	double yOffset;
	double scale;
	
	double shadowDepth = 7;
	
	public GridPanel() 
	{
		super();
		
		int bg = 246;
		setBackground(new Color(bg, bg, bg));
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		
		xOffset = 0;
		yOffset = 0;
		scale = 1;
		
		// scroll sensitivity
		scrollSensitivity = 2;
		scrollCounter = 0;
		
		// orange pieces
		orange = new LPiece(181, 181, scale, new Color(255, 144, 33));
		orangeShadow = new LPiece(181 + shadowDepth, 181 - shadowDepth, scale, new Color(237, 134, 0)); //hmmm
		orangeTile = new LPiece(181, 181, scale, new Color(255, 179, 97));
		
		// blue pieces
		blue = new LPiece(181 + 145, 181 - 145, scale, new Color(0, 87, 237));
		blueShadow = new LPiece(181 + 145 + shadowDepth, 181 - 145 - shadowDepth, scale, new Color(0, 68, 186)); //hmmm
		blueTile = new LPiece(181 + 145, 181 - 145, scale, new Color(130, 199, 255));
		blue.rotate90(2);
		blueShadow.rotate90(2);
		blueTile.rotate90(2);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
		
		
		g2d.translate(xOffset, yOffset);
		
		if (draw) {
			g2d.setColor(new Color(200, 200, 200));
			Rectangle2D out1 = new Rectangle2D.Double(0 + xOffset, 0 + yOffset, 640 * scale, 640 * scale);
			g2d.fill(out1);
			
			g2d.setColor(new Color(120, 120, 120));
			Rectangle2D out2 = new Rectangle2D.Double(20 + xOffset, 20 + yOffset, 600 * scale, 600 * scale);
			g2d.fill(out2);
			
			g2d.setColor(new Color(246, 246, 246));
			for (int j = 0; j < 4; j++) {
				for (int i = 0; i < 4; i++) {
					Rectangle2D tempBoxes = new Rectangle2D.Double(145 * i + 36 + xOffset, 145 * j + 36 + yOffset, 129 * scale, 129 * scale);
					g2d.fill(tempBoxes);
				}
			}
		}
		if (isOrangeMoveable) {
			orangeTile.paintComponent(g2d);
			orangeShadow.paintComponent(g2d);
		} else if (isBlueMoveable) {
			blueTile.paintComponent(g2d);
			blueShadow.paintComponent(g2d);
		}
		orange.paintComponent(g2d);
		blue.paintComponent(g2d);
		if (isOrangeMoveable) {
			orange.paintComponent(g2d);
		}
		
    }
	
	// Used mouse events
	public void mouseMoved(MouseEvent e) 
	{
		// System.out.println("test1");
		if (isOrangeMoveable) {
			orange.translateTo(e.getX() - orange.getCenterX(), e.getY() - orange.getCenterY());
			orangeShadow.translateTo(orange.getX() + shadowDepth, orange.getY() - shadowDepth);
			orangeTile.translateTo(
				(36 + xOffset) + (int)((orange.getX() + 20) / 145) * 145, 
				(36 + yOffset) + (int)((orange.getY() + 20) / 145) * 145
			);
		} else if (isBlueMoveable) {
			blue.translateTo(e.getX() - blue.getCenterX(), e.getY() - blue.getCenterY());
			blueShadow.translateTo(blue.getX() + shadowDepth, blue.getY() - shadowDepth);
			blueTile.translateTo(
				(36 + xOffset) + (int)((blue.getX() + 20) / 145) * 145, 
				(36 + yOffset) + (int)((blue.getY() + 20) / 145) * 145
			);
		}
		repaint();
    }
	
	public void mousePressed(MouseEvent e) 
	{
		// left click
		// picks and sets down the piece
		if (SwingUtilities.isLeftMouseButton(e)) {
			// orange
			if (!isOrangeMoveable) {
				if (orange.contains(e.getX(), e.getY())) {
					isOrangeMoveable = true;
				}
			} else {
				isOrangeMoveable = false;
				orange.translateTo(orangeTile.getX(), orangeTile.getY());
			}
			
			// blue
			if (!isBlueMoveable) {
				if (blue.contains(e.getX(), e.getY())) {
					isBlueMoveable = true;
				}
			} else {
				isBlueMoveable = false;
				blue.translateTo(blueTile.getX(), blueTile.getY());
			}
			mouseMoved(e);
		}
		
		// right click
		// flips the piece horizontally
		else if (SwingUtilities.isRightMouseButton(e)) {
			if (isOrangeMoveable) {
				orange.flip();
				orangeShadow.flip();
				orangeTile.flip();
			} else if (isBlueMoveable){
				blue.flip();
				blueShadow.flip();
				blueTile.flip();
			}
			repaint();
		}
		// SwingUtilities.isMiddleMouseButton(e);
		
		// System.out.println("test3");
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{	
		// decreases scroll sensitivity
		scrollCounter += Math.abs(e.getPreciseWheelRotation());
		
		// rotates piece on scroll
		if (scrollCounter >= scrollSensitivity) {
			if (isOrangeMoveable) {
				int amountToRotate = e.getPreciseWheelRotation() > 0 ? 1 : 3;
				
				orange.rotate90(amountToRotate);
				orangeShadow.rotate90(amountToRotate);
				orangeTile.rotate90(amountToRotate);
				
				repaint();
			}
			
			scrollCounter = 0;
		}
		
		
	}
	
	
	// Unusued mouse events
	public void mouseReleased(MouseEvent e) {}
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