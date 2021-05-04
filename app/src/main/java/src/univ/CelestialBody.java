package src.univ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import src.peng.Vector3d;

/**
 * Group 22
 * A class to hold the information of a CelestialBody
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class CelestialBody 
{
	public double mass;
	public double radius;
	public String name = null;
	public String image = null;
	public String icon = null;
	public Vector3d velocity;
	public Vector3d location;
	public LocalDateTime time = null;

	public CelestialBody(Vector3d location, Vector3d velocity, double mass, double radius, String name, String image, String icon, LocalDateTime time)
	{
		this.location = location;
		this.velocity = velocity;
		this.mass = mass;
		this.radius = radius;
		this.name = name;
		this.image = image;
		this.icon = icon;
		this.time = time;
		time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * Returns true if the coordinate is within the location of the Celestial Body
	 * @param coordinate
	 * @return
	 */
	public boolean collision(Vector3d object)
	{
		// Calculate distance from origin ( (a1-a2)^2 + (b1-b2)^2 + (c1-c2)^2)^1/2 
		double deltaA = Math.pow((location.getX() - object.getX()), 2);
		double deltaB = Math.pow((location.getY() - object.getY()), 2);
		double deltaC = Math.pow((location.getZ() - object.getZ()), 2);
		double dist = deltaA + deltaB + deltaC;
		dist = Math.sqrt(dist);
		if(dist < radius)
			return true;
		return false;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}
	
	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public CelestialBody updateCopy(Vector3d position, Vector3d velocity, LocalDateTime time)
	{
		return new CelestialBody(position, velocity, mass, radius, name, image, icon, time);
	}
	
	public double timeInMs()
	{
		double t = 0;
		t = t + time.getSecond() * 1000;
		t = t + time.getMinute() * 60000;
		t = t + time.getHour() * 3.6E+6;
		return t;
	}
}

