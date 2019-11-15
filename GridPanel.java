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
	
	LPiece[] mainLPiece = new LPiece[2];
	LPiece[] LShadow = new LPiece[2];
	LPiece[] LTile = new LPiece[2];
	
	NPiece[] mainNPiece = new NPiece[2];
	NPiece[] nShadow = new NPiece[2];
	NPiece[] nTile = new NPiece[2];
	
	boolean[] LCanMove = new boolean[2];
	boolean[] LCanPlace = new boolean[2];
	
	boolean[] nCanMove = new boolean[2];
	boolean[] nCanPlace = new boolean[2];
	
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
		mainLPiece[0] = new LPiece(181, 181, scale, new Color(255, 144, 33));
		LShadow[0] = new LPiece(181 + shadowDepth, 181 - shadowDepth, scale, new Color(237, 134, 0)); //hmmm
		LTile[0] = new LPiece(181, 181, scale, new Color(255, 179, 97));
		
		// blue pieces [1]
		mainLPiece[1] = new LPiece(181 + 145, 181 - 145, scale, new Color(0, 87, 237));
		LShadow[1] = new LPiece(181 + 145 + shadowDepth, 181 - 145 - shadowDepth, scale, new Color(0, 68, 186)); //hmmm
		LTile[1] = new LPiece(181 + 145, 181 - 145, scale, new Color(130, 199, 255));
		mainLPiece[1].rotate90(2);
		LShadow[1].rotate90(2);
		LTile[1].rotate90(2);
		
		// neutral pieces
		mainNPiece[0] = new NPiece(181 - 145 + 10, 181 - 145 + 10, 1, Color.GRAY);
		nShadow[0] = new NPiece(181 - 145 + 10 + shadowDepth, 181 - 145 + 10 - shadowDepth, 1, Color.DARK_GRAY);
		nTile[0] = new NPiece(181 - 145 + 10, 181 - 145 + 10, 1, Color.LIGHT_GRAY);
		
		mainNPiece[1] = new NPiece(181 + 2 * 145 + 10, 181 + 2 * 145 + 10, 1, Color.GRAY);
		nShadow[1] = new NPiece(181 + 2 * 145 + 10 + shadowDepth, 181 + 2 * 145 + 10 - shadowDepth, 1, Color.DARK_GRAY);
		nTile[1] = new NPiece(181 + 2 * 145 + 10, 181 + 2 * 145 + 10, 1, Color.LIGHT_GRAY);
		
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
		mainLPiece[0].paintComponent(g2d);
		mainLPiece[1].paintComponent(g2d);
		mainNPiece[0].paintComponent(g2d);
		mainNPiece[1].paintComponent(g2d);
		for (int i = 0; i < 2; i++) {
			// LPieces
			if (LCanMove[i]) {
				if (LCanPlace[i]) {
					LTile[i].paintComponent(g2d);
				}
				LShadow[i].paintComponent(g2d);
				mainLPiece[i].paintComponent(g2d);
			}
			
			// NPieces
			if (nCanMove[i]) {
				if (nCanPlace[i]) {
					nTile[i].paintComponent(g2d);
				}
				nShadow[i].paintComponent(g2d);
				mainNPiece[i].paintComponent(g2d);
			}
		}
		
    }
	
	// Used mouse events
	public void mouseMoved(MouseEvent e) 
	{
		System.out.println(e.getX() + " " + e.getY());
		for (int i = 0; i < 2; i++) {
			// for each LPiece
			if (LCanMove[i]) {
				mainLPiece[i].translateTo(e.getX() - mainLPiece[i].getWidth() / 2, e.getY() - mainLPiece[i].getHeight() / 2);
				LShadow[i].translateTo(mainLPiece[i].getX() + shadowDepth, mainLPiece[i].getY() - shadowDepth);
				
				LTile[i].translateTo(
					(36 + xOffset) + (int)((mainLPiece[i].getX() + 20) / 145) * 145, 
					(36 + yOffset) + (int)((mainLPiece[i].getY() + 20) / 145) * 145
				);
				
			/** Make seperate method {*/
				// checks if the piece is in bounds
				Rectangle2D tileBound = LTile[i].getBoundingBox();
				double maxX = tileBound.getX() + tileBound.getWidth();
				double maxY = tileBound.getY() + tileBound.getHeight();
				
				LCanPlace[i] = false;
				// inbounds
				if (tileBound.getX() >= 0 && tileBound.getY() >= 0 && maxX < 145 * 4 + 36 + xOffset && maxY < 145 * 4 + 36 + yOffset) {
					// intersects other LPiece
					if (!(LTile[i].intersects(mainLPiece[1 - i]))) {
						// intersects each NPiece
						boolean intersects = false;
						for (int j = 0; j < 2; j++) {
							if (LTile[i].contains(nTile[j].getCenterX(), nTile[j].getCenterY())) {
								intersects = true;
								break;
							}
						}
						LCanPlace[i] = !intersects;
					}
				}
			/** }*/
			}
			
			// for each NPiece
			if (nCanMove[i]) {
				mainNPiece[i].translateTo(e.getX() - mainNPiece[i].getWidth() / 2, e.getY() - mainNPiece[i].getHeight() / 2);
				nShadow[i].translateTo(mainNPiece[i].getX() + shadowDepth, mainNPiece[i].getY() - shadowDepth);
				
				nTile[i].translateTo(
					(36 + xOffset + 10) + (int)((mainNPiece[i].getX() + 20) / 145) * 145, 
					(36 + yOffset + 10) + (int)((mainNPiece[i].getY() + 20) / 145) * 145
				);
				
				nCanPlace[i] = false;
				// checks if in bounds
				if (
					nTile[i].getX() >= 0 && 
					nTile[i].getY() >= 0 && 
					nTile[i].getX() + nTile[i].getWidth() < 145 * 4 + 36 + xOffset && 
					nTile[i].getY() + nTile[i].getHeight() < 145 * 4 + 36 + yOffset
				) {
					// nCanPlace[i] = true;
					
					// intersects with other NPiece
					if (!nTile[i].intersects(mainNPiece[1 - i])) {
						
						// 
						// intersects with each LPiece
						boolean intersects = false;
						for (int j = 0; j < 2; j++) {
							if (LTile[j].contains(nTile[i].getCenterX(), nTile[i].getCenterY())) {
								intersects = true;
								break;
							}
						}
						
						nCanPlace[i] = !intersects;
					}
					
					else {
						System.out.println("Intersects");
					}
					
				}
			}
		}
		repaint();
    }
	
	// public boolean valid (LPiece p) 
	// {
		
	// }
	
	public void mousePressed(MouseEvent e) 
	{
		// left click
		// picks and sets down the piece
		if (SwingUtilities.isLeftMouseButton(e)) {
			for (int i = 0; i < 2; i++) {
				// for each LPiece
				if (!LCanMove[i] && !LCanMove[1 - i] && !nCanMove[i] && !nCanMove[1 - i]) {
					if (mainLPiece[i].contains(e.getX(), e.getY())) {
						LCanMove[i] = true;
					}
				} else {
					if (LCanPlace[i]) {
						LCanMove[i] = false;
						mainLPiece[i].translateTo(LTile[i].getX(), LTile[i].getY());
					}
				}
				
				// for each NPiece
				if (!LCanMove[i] && !LCanMove[1 - i] && !nCanMove[i] && !nCanMove[1 - i]) {
					if (mainNPiece[i].contains(e.getX(), e.getY())) {
						nCanMove[i] = true;
					}
				} else {
					if (nCanPlace[i]) {
						nCanMove[i] = false;
						mainNPiece[i].translateTo(nTile[i].getX(), nTile[i].getY());
					}
				}
			}
		}
		
		// right click
		// flips the piece horizontally
		else if (SwingUtilities.isRightMouseButton(e)) {
			for (int i = 0; i < 2; i++) {
				if (LCanMove[i]) {
					mainLPiece[i].flip();
					LShadow[i].flip();
					LTile[i].flip();
				}
			}
			repaint();
		}
		// SwingUtilities.isMiddleMouseButton(e);
		
		// System.out.println("test3");
		mouseMoved(e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{	
		// decreases scroll sensitivity
		scrollCounter += Math.abs(e.getPreciseWheelRotation());
		
		// rotates piece on scroll
		if (scrollCounter >= scrollSensitivity) {
			for (int i = 0; i < 2; i++) {
				if (LCanMove[i]) {
					int amountToRotate = e.getPreciseWheelRotation() > 0 ? 1 : 3;
					
					mainLPiece[i].rotate90(amountToRotate);
					LShadow[i].rotate90(amountToRotate);
					LTile[i].rotate90(amountToRotate);
					
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