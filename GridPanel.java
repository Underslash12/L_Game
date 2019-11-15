// TestPanel.java

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.Color;
import java.awt.event.*;

public class GridPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	boolean draw = false;
	
	// 2 colors, 2 neutrals
	Piece[] mainPiece = new Piece[4];
	Piece[] shadow = new Piece[4];
	Piece[] tile = new Piece[4];
	
	boolean[] canMove = new boolean[4];
	boolean[] canPlace = new boolean[4];
	
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
		
		// neutral pieces
		mainPiece[2] = new NPiece(181 - 145, 181 - 145, 1, Color.GRAY);
		shadow[2] = new NPiece(181 - 145 + shadowDepth, 181 - 145 - shadowDepth, 1, Color.DARK_GRAY);
		tile[2] = new NPiece(181 - 145, 181 - 145, 1, Color.LIGHT_GRAY);
		
		mainPiece[3] = new NPiece(181 + 2 * 145, 181 + 2 * 145, 1, Color.GRAY);
		shadow[3] = new NPiece(181 + 2 * 145 + shadowDepth, 181 + 2 * 145 - shadowDepth, 1, Color.DARK_GRAY);
		tile[3] = new NPiece(181 + 2 * 145, 181 + 2 * 145, 1, Color.LIGHT_GRAY);
		
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		
        Graphics2D g2d = (Graphics2D) g;
		g2d.translate(xOffset, yOffset);
		
		if (draw) {
			g2d.setColor(new Color(160, 160, 160));
			g2d.fill(new Rectangle2D.Double(shadowDepth + xOffset, -shadowDepth + yOffset, 640 * scale, 640 * scale));
			g2d.fill(new Rectangle2D.Double(-shadowDepth + xOffset, shadowDepth + yOffset, 640 * scale, 640 * scale));
			
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
		for (int i = 0; i < 4; i++) {
			mainPiece[i].paintComponent(g2d);
		}
		
		for (int i = 0; i < 4; i++) {
			if (canMove[i]) {
				if (canPlace[i]) {
					tile[i].paintComponent(g2d);
				}
				shadow[i].paintComponent(g2d);
				mainPiece[i].paintComponent(g2d);
			}
		}
    }
	
	public void mouseMoved(MouseEvent e)
	{
		for (int i = 0; i < 4; i++) {
			if (canMove[i]) {
				mainPiece[i].translateTo(e.getX() - mainPiece[i].getWidth() / 2, e.getY() - mainPiece[i].getHeight() / 2);
				shadow[i].translateTo(mainPiece[i].getX() + shadowDepth, mainPiece[i].getY() - shadowDepth);
				
				// aligns the piece with the tiles
				tile[i].translateTo(
					(36 + xOffset) + (int)((mainPiece[i].getX() + 20) / 145) * 145, 
					(36 + yOffset) + (int)((mainPiece[i].getY() + 20) / 145) * 145
				);
				
				Rectangle2D tileBound = tile[i].getBoundingBox();
				double maxX = tileBound.getX() + tileBound.getWidth();
				double maxY = tileBound.getY() + tileBound.getHeight();
				
				canPlace[i] = false;
				// inbounds
				if (tileBound.getX() >= 0 && tileBound.getY() >= 0 && maxX < 145 * 4 + 36 + xOffset && maxY < 145 * 4 + 36 + yOffset) {
					boolean intersects = false;
					for (int j = 0; j < 4; j++) {
						if (j != i) {
							if (tile[i].intersects(mainPiece[j])) {
								intersects = true;
							}
						}
					}
					System.out.println(intersects + " | " + e.getX() + " " + e.getY());
					canPlace[i] = !intersects;
				}
			}
		}
		
		repaint();
	}
	
	public void mousePressed(MouseEvent e) 
	{
		// left click
		// picks and sets down the piece
		if (SwingUtilities.isLeftMouseButton(e)) {
			for (int i = 0; i < 4; i++) {
				boolean anyMoveable = false;
				for (boolean b : canMove) {
					if (b) {
						anyMoveable = true;
					}
				}
				
				if (!anyMoveable) {
					if (mainPiece[i].contains(e.getX(), e.getY())) {
						canMove[i] = true;
					}
				} else {
					if (canPlace[i]) {
						canMove[i] = false;
						mainPiece[i].translateTo(tile[i].getX(), tile[i].getY());
					}
				}
			}
		}
		
		// right click
		// flips the piece horizontally
		else if (SwingUtilities.isRightMouseButton(e)) {
			for (int i = 0; i < 4; i++) {
				if (canMove[i]) {
					mainPiece[i].flip();
					shadow[i].flip();
					tile[i].flip();
				}
			}
			repaint();
		}
		// SwingUtilities.isMiddleMouseButton(e);
		
		mouseMoved(e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{	
		// decreases scroll sensitivity
		scrollCounter += Math.abs(e.getPreciseWheelRotation());
		
		// rotates piece on scroll
		if (scrollCounter >= scrollSensitivity) {
			for (int i = 0; i < 4; i++) {
				if (canMove[i]) {
					int amountToRotate = e.getPreciseWheelRotation() > 0 ? 1 : 3;
					
					mainPiece[i].rotate90(amountToRotate);
					shadow[i].rotate90(amountToRotate);
					tile[i].rotate90(amountToRotate);
					
					repaint();
				}
			}
			scrollCounter = 0;
		}
		
		mouseMoved(e);
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