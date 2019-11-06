// TestPanel.java

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class TestPanel extends JPanel {
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
		
		Rectangle2D rect = new Rectangle2D.Double(100, 100, 60, 80);
		g2d.draw(rect);
    }
}