package src.visu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import src.land.LanderObject;
import src.misc.ResourceLoader;
import src.peng.Vector3d;
import src.univ.CelestialBody;

public class LandingVisualiser extends JPanel
{
			
	private int xOrigin;
	private int yOrigin;
	private int yOffset = -20;
	private double distanceScaling = 51510;
	
	private ArrayList<LanderObject> landerList;
	
	private Timer timer;
	private int time;
	private int timeInterval = 20;
	private int endTime;
	
	private CelestialBody planetToPaint;
	
	private boolean play = true;
	private boolean hasLanded = false;
	
	public LandingVisualiser() 
	{
		System.out.print("Starting the landing visualisation... ");
		setSize(900, 600);
		xOrigin = getWidth() /2;
		yOrigin = getHeight() / 2;
		setBackground(Color.BLACK);
		setVisible(true);
		time = 0;
		ActionListener timeEvent = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if(play) {
					incrementTime(timeInterval);
				}
			}
		};
		timer = new Timer(1, timeEvent);
//		timer.start();
		System.out.println("Finished");
	}
	
	@Override
	public void paint(Graphics G) {
		super.paint(G);
		setBackground(Color.BLACK);
		Graphics2D g = (Graphics2D) G;
		if(landerList != null && planetToPaint != null) {
			paintPlanet(g);
			paintTrajectory(g);
			paintOrbit(g);
			//paintLander(g);
		}
	}
	
	private void paintTrajectory(Graphics2D g) {
		g.setColor(Color.GREEN);
		for(int i = 0; i < landerList.size(); i++) {
			int x = xOrigin;
			int y = yOrigin + yOffset;
			double rx = (landerList.get(i).getPosition().getX() / distanceScaling) - 22;
			double ry = (landerList.get(i).getPosition().getY() / distanceScaling);
			g.fillOval(x - (int) ry, y - (int) rx, 2, 2);
		}
		
	}
	
	private void paintLander(Graphics2D g) {
		BufferedImage image = ResourceLoader.getImage("craftIcon.png");
		int w = 50;
		int h = 50;
		
		int x = xOrigin;
		int y = yOrigin + yOffset;
			
		x += (int) landerList.get(time).getPosition().getX();
		y += (int) landerList.get(time).getPosition().getY();
			
		double degrees = landerList.get(time).getAngle();
		double theta = Math.toRadians(degrees);
		AffineTransform tx = AffineTransform.getRotateInstance(theta, w/2, h/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			
		g.drawImage(op.filter(image, new BufferedImage(w, h, image.getType())), x-(w/2), y-(h/2), w, h, null);
	}
	
	private void paintPlanet(Graphics2D g) {
		int r = (int) planetToPaint.radius;
		r /= distanceScaling;
		int x = xOrigin;
		int y = yOrigin + yOffset;
		
		if(planetToPaint.image != null) {
			BufferedImage image = ResourceLoader.getImage(planetToPaint.image);
			g.drawImage(image, x -(r/2), y -(r/2), r, r, null);
		}else {
			g.setColor(Color.WHITE);
			g.fillOval(x -(r/2), y -(r/2), r, r);
		}
	}
	
	private void paintOrbit(Graphics2D g) {
		int x = xOrigin;
		int y = yOrigin + yOffset;
		int r = (int) (10000000 / distanceScaling);
		r += 150;
		g.setColor(Color.YELLOW);
		g.drawOval(x - (r/2), y - (r/2), r, r);
	}
	
	public void addObjectList(ArrayList<LanderObject> list) {
		landerList = list;
		endTime = landerList.size();
		repaint();
	}
	
	public void addPlanet(CelestialBody planet) {
		planetToPaint = planet;
		repaint();
	}
	
	private void incrementTime(int interval) {
		if((time + interval < endTime) && (time + interval >= 0)) {
			time += interval;
			repaint();
		}
	}

}