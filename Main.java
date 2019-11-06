// Main.java

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import java.awt.image.BufferedImage;

public class Main {
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		TestPanel t = new TestPanel();
		// Graphics g = frame.getGraphics();
		// Graphics2D g2 = (Graphics2D) g.create();
		// g2.fill(rect);
		// g.fillRect(100, 100, 60, 80);
		// Rectangle2D rect = new Rectangle2D.Double(100, 100, 60, 80);
		// BufferedImage b = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		// Graphics2D g2d = (Graphics2D) b.getGraphics();
		// g2d.fill(rect);
		
		frame.getContentPane().add(t);
		//4. Size the frame.
		frame.setSize(600, 600);

		//5. Show it.
		frame.setVisible(true);
		// frame.getContentPane().paint(g2d);
		
		System.out.println(frame.getGraphics());
		
		
		// Graphics2D g2d = (Graphics2D) frame.getGraphics();
		// Graphics2D g2d = t.getGraphics();
		// g2d.fill(rect);

		
		
		// int x = 0;
		// while (x < 50000) {
			// g2d.fill(rect);
			// x++;
		// }
		// System.out.println("Done?");
		// frame.repaint(1000, 0, 0, 600, 600);
	}
}