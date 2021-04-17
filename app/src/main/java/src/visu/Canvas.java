package src.visu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import src.univ.*;

/**
 * Group 22
 * A class that creates a graphical representation of a set of celestial bodies in orbit 
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class Canvas extends JPanel
{
	private static final long serialVersionUID = 1L;
	public final boolean DEBUG = true;
	
	private int xOffset;
	private int yOffset;
	private int xOrigin;
	private int yOrigin;
	private double distScaling = 5E-9;		// The starting scale sizes
	private double sizeScaling = 1E-6;
	private double generalDist = 5E-9;		// The model in general scales
	private double generalSize = 1E-6;
	private double detailDist = 4.8E-7;		// The model in detail scales
	private double detailSize = 3E-6;
	private double zoom_rate = 1E-10;
	private Dimension screen;
	private CelestialBody[][] U;
	private int time;						// Current time
	private int endTime;
	private boolean follow = false;
	private int following = -1;
	
	public Canvas(CelestialBody[][] U, Dimension screen)
	{
		this.U = U;
		time = 0;
		endTime = U[0].length;
		setSize(screen);
		this.screen = screen;
		xOffset = 0;
		yOffset = 0;
		xOrigin = getWidth()/ 2;
		yOrigin = getHeight()/ 2;
	}
	
	@Override
	public void paint(Graphics G)
	{
		super.paint(G);
		setBackground(Color.BLACK);
		Graphics2D g = (Graphics2D) G;
		
		// Paint the DTG in the corner
		Font font = new Font("SansSerif", Font.PLAIN, 24);
		g.setFont(font);
		int timeX = 10;
		int timeY = (int) screen.getHeight()- 140;
		g.drawString(U[0][time].time.toStringDate(), timeX, timeY);
		
		if(follow)
		{
			distScaling = detailDist;
			sizeScaling = detailSize;
			xOffset = (int) - (U[following][time].location.getX() * distScaling);
			yOffset = (int) - (U[following][time].location.getY() * distScaling);	
		}
		
		// Paint Orbits
		g.setColor(Color.GREEN);
		for(int i = 0; i < U.length; i++)		
		{
			for(int j = 0; j < U[i].length; j += 50)
			{
				int x = xOrigin;
				x += (int) (U[i][j].location.getX() * distScaling);
				x += xOffset;
				
				int y = yOrigin;
				y += (int) (U[i][j].location.getY() * distScaling);
				y += yOffset;
				
				g.fillOval(x, y, 2, 2);
			}
		}
		
		
		// Run through each CelestialBody and paint it at time t
		g.setColor(Color.WHITE);
		for(int i = 0; i < U.length; i++)		
		{		
			int r = (int) (U[i][time].radius * sizeScaling);
			
			int x = xOrigin;
			x += (int) (U[i][time].location.getX() * distScaling);
			x -= r/2;
			x += xOffset;
			
			int y = yOrigin;
			y += (int) (U[i][time].location.getY()  * distScaling);
			y -= r/2;
			y += yOffset;
			
			// If the planet has a skin, draw a scaled image
			if(U[i][time].image != null)
			{
				try 
				{ 
					BufferedImage img = ImageIO.read(new File(U[i][time].image));
					g.drawImage(img, x, y, r, r, null);
				} 
				catch (IOException e) {}
			}
			else
				g.fillOval(x, y, r, r);
		}
		
		// Paint trajectories
		g.setColor(Color.RED);
		for(int i = 11; i < U.length; i++)		
		{
			for(int j = 0; j < U[i].length; j++)
			{
				int x = xOrigin;
				x += (int) (U[i][j].location.getX() * distScaling);
				x += xOffset;
				
				int y = yOrigin;
				y += (int) (U[i][j].location.getY() * distScaling);
				y += yOffset;
				
				g.fillOval(x, y, 2, 2);
			}
		}
	}
	
	public void incrementTime(int interval)
	{
		if((time+interval < endTime) && (time+interval >= 0))
		{
			time += interval;
			if(DEBUG) System.out.println(time);
			repaint();
		}
	}
	
	public void setTime(int t)
	{
		if((time < endTime) && (time > 0))
		{
			time = t;
			if(DEBUG) System.out.println(time);
			repaint();
		}
	}
	
	public void zoom(double quantity, Point loc)
	{
		distScaling += (quantity * zoom_rate);
		sizeScaling += (quantity * 100 * zoom_rate);
		if(DEBUG) System.out.println("Dist: " + distScaling);
		if(DEBUG) System.out.println("Size: " + sizeScaling);
		repaint();
	}
	
	public void follow(int i)
	{
		if(i == -1)
			follow = false;
		else if(i >= 0 && i < U.length)
		{
			following = i;
			follow = true;
		}
		repaint();
		return;
	}
	
	public void move(double x, double y)
	{
		xOffset += (int) x;
		yOffset += (int) y;
		repaint();
	}
	
	public void generalView()
	{
		xOffset = 0;
		yOffset = 0;
		distScaling = generalDist;
		sizeScaling = generalSize;
		repaint();
	}
	
	public int getTime()
	{
		return time;
	}
}
