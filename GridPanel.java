// TestPanel.java

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.Color;

public class GridPanel extends JPanel {
	
	boolean draw = false;
	
	public GridPanel() 
	{
		super();
		int bg = 246;
		setBackground(new Color(bg, bg, bg));
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
		
		double xOffset = 100;
		double yOffset = 100;
		double xScale = 1;
		double yScale = 1;
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
    }
	
	public void draw(boolean state) 
	{
		draw = state;
		repaint();
	}
}