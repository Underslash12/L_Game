// Main.java

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import java.awt.Color;

public class Main {
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// JPanel bg = new JPanel();
		// bg.setBackground(new Color(150, 150, 150));
		// frame.getContentPane().add(bg);
		
		GridPanel t = new GridPanel();
		frame.getContentPane().add(t);
	
		frame.setSize(800 + 18, 800 + 47);
		// frame.pack();
		System.out.println(frame.getContentPane().getSize());
		frame.getContentPane().setBackground(new Color(20, 150, 150));
		
		frame.setVisible(true);
		
		// sleep(2000);
		
		t.draw(true);

		// System.out.println(frame.getGraphics());
	}
	
	public static void sleep(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (Exception e){
			System.out.println(e);
		}
	}
}