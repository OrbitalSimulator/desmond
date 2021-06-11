package src.visu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.peng.Vector2d;
import src.peng.Vector3d;
import src.univ.CelestialBody;

public class LandingVisualiser extends JPanel
{
			
	private int xOrigin;
	private int yOrigin;
	
	private double distanceScaling = 1.00;
	
	private Vector2d vectorToPaint;
	private CelestialBody planetToPaint;
	
	public LandingVisualiser() 
	{
		System.out.print("Starting the landing visualisation... ");
		setSize(900, 600);
		xOrigin = getWidth() / 2;
		yOrigin = getHeight() / 2;
		setBackground(Color.BLACK);
		setVisible(true);
		System.out.println("Finished");
	}
	
	@Override
	public void paint(Graphics G) {
		super.paint(G);
		setBackground(Color.BLACK);
		Graphics2D g = (Graphics2D) G;
		paintVector(g);
		//paintPlanet(g);
	}
	
	private void paintVector(Graphics2D g) {
		g.setColor(Color.GREEN);
		for(int i = 0; i < 100; i++) {
			int x = xOrigin;
			int y = yOrigin;
			x += (int) vectorToPaint.getX();
			y += (int) vectorToPaint.getY();
			g.fillOval(x, y + i, 2, 2);
		}
	}
	
	private void paintPlanet(Graphics2D g) {
		
	}
	
	public void addVector(Vector2d vector) {
		vectorToPaint = vector;
		repaint();
	}
	
	public void addPlanet(CelestialBody planet) {
		planetToPaint = planet;
		repaint();
	}

}