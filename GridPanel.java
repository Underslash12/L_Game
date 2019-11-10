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
	// Rectangle2D[] mainPiece[0]LMain = new Rectangle2D[4];
	// Rectangle2D[] mainPiece[0]LHelper;
	// LPiece mainPiece[0], shadow[0], tile[0];
	// LPiece mainPiece[1], shadow[1], tile[1];
	
	LPiece[] mainPiece = new LPiece[2];
	LPiece[] shadow = new LPiece[2];
	LPiece[] tile = new LPiece[2];
	
	boolean[] isMoveable = new boolean[2];
	
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
		
		// orange pieces [0]
		mainPiece[0] = new LPiece(181, 181, scale, new Color(255, 144, 33));
		shadow[0] = new LPiece(181 + shadowDepth, 181 - shadowDepth, scale, new Color(237, 134, 0)); //hmmm
		tile[0] = new LPiece(181, 181, scale, new Color(255, 179, 97));
		
		// blue pieces [1]
		mainPiece[1] = new LPiece(181 + 145, 181 - 145, scale, new Color(0, 87, 237));
		shadow[1] = new LPiece(181 + 145 + shadowDepth, 181 - 145 - shadowDepth, scale, new Color(0, 68, 186)); //hmmm
		tile[1] = new LPiece(181 + 145, 181 - 145, scale, new Color(130, 199, 255));
		mainPiece[1].rotate90(2);
		shadow[1].rotate90(2);
		tile[1].rotate90(2);
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
		
		// Hopefully deals with overlappage
		mainPiece[0].paintComponent(g2d);
		mainPiece[1].paintComponent(g2d);
		for (int i = 0; i < 2; i++) {
			if (isMoveable[i]) {
				tile[i].paintComponent(g2d);
				shadow[i].paintComponent(g2d);
				mainPiece[i].paintComponent(g2d);
			}
		}
    }
	
	// Used mouse events
	public void mouseMoved(MouseEvent e) 
	{
		// System.out.println("test1");
		for (int i = 0; i < 2; i++) {
			if (isMoveable[i]) {
				mainPiece[i].translateTo(e.getX() - mainPiece[i].getCenterX(), e.getY() - mainPiece[i].getCenterY());
				shadow[i].translateTo(mainPiece[i].getX() + shadowDepth, mainPiece[i].getY() - shadowDepth);
				tile[i].translateTo(
					(36 + xOffset) + (int)((mainPiece[i].getX() + 20) / 145) * 145, 
					(36 + yOffset) + (int)((mainPiece[i].getY() + 20) / 145) * 145
				);
			}
		}
		repaint();
    }
	
	public void mousePressed(MouseEvent e) 
	{
		// left click
		// picks and sets down the piece
		if (SwingUtilities.isLeftMouseButton(e)) {
			for (int i = 0; i < 2; i++) {
				if (!isMoveable[i]) {
					if (mainPiece[i].contains(e.getX(), e.getY())) {
						isMoveable[i] = true;
					}
				} else {
					isMoveable[i] = false;
					mainPiece[i].translateTo(tile[i].getX(), tile[i].getY());
				}
			}
			mouseMoved(e);
		}
		
		// right click
		// flips the piece horizontally
		else if (SwingUtilities.isRightMouseButton(e)) {
			for (int i = 0; i < 2; i++) {
				if (isMoveable[i]) {
					mainPiece[i].flip();
					shadow[i].flip();
					tile[i].flip();
				}
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
			for (int i = 0; i < 2; i++) {
				if (isMoveable[i]) {
					int amountToRotate = e.getPreciseWheelRotation() > 0 ? 1 : 3;
					
					mainPiece[i].rotate90(amountToRotate);
					shadow[i].rotate90(amountToRotate);
					tile[i].rotate90(amountToRotate);
					
					repaint();
				}
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