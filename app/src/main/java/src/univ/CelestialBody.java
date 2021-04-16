package src.univ;

import java.awt.Color;

import src.peng.Vector3d;

/**
 * Group 22
 * A class to hold the information of a CelestialBody
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class CelestialBody 
{
	public Coordinate loc;
	public Coordinate bary;
	public double mass;
	public double radius;
	public Color colour = Color.YELLOW;
	public String image = null;
	public String icon = null;
	public Vector3d velocity;
	public Vector3d location;

	public CelestialBody copyOf(Vector3d vel, Vector3d pos)
	{
		CelestialBody copy = new CelestialBody(this.mass, pos, vel);
		copy.image = this.image;
		copy.mass = this.mass;
		copy.loc = new Coordinate(pos, new DTG());
		copy.icon = this.icon;
		copy.radius = this.radius;
		copy.bary = this.bary;
		copy.colour = this.colour;
		return copy; 
	}

	public CelestialBody copyOf(Vector3d vel, Vector3d pos, double time)
	{
		CelestialBody copy = new CelestialBody(this.mass, pos, vel);
		copy.image = this.image;
		copy.mass = this.mass;

		//Convert time to DTG object
		DTG initial = new DTG(2020, 4, 1, 0, 0, 0);
		initial.addSeconds(time);
		copy.loc = new Coordinate(pos, initial);
		copy.icon = this.icon;
		copy.radius = this.radius;
		copy.bary = this.bary;
		copy.colour = this.colour;
		return copy; 
	}
	
	public CelestialBody(Coordinate loc, Coordinate bary, double mass, double radius)
	{
		this.loc = loc;
		this.bary = bary;
		this.mass = mass;
		this.radius = radius;
	}

	public CelestialBody(Coordinate loc, Coordinate bary, double mass, double radius, Vector3d velocity)
	{
		this.loc = loc;
		this.bary = bary;
		this.mass = mass;
		this.radius = radius;
		this.velocity = velocity;
	}

	public CelestialBody(Coordinate loc, Coordinate bary, double mass, double radius, Vector3d velocity, String image)
	{
		this.loc = loc;
		this.bary = bary;
		this.mass = mass;
		this.radius = radius;
		this.velocity = velocity;
	}

	public CelestialBody(double mass, Vector3d location, Vector3d velocity)
	{
		this.mass = mass;
		this.velocity = velocity;
		this.location = location;
		this.loc = new Coordinate(velocity, new DTG());
	}

	public CelestialBody(double mass, Vector3d location, Vector3d velocity, double radius)
	{
		this.mass = mass;
		this.velocity = velocity;
		this.location = location;
		this.radius = radius;
		this.loc = new Coordinate(velocity, new DTG());
	}
	
	public CelestialBody(Coordinate loc, Coordinate bary, double mass, double radius, String image, String icon)
	{
		this.loc = loc;
		this.bary = bary;
		this.mass = mass;
		this.radius = radius;
		this.image = image;
		this.icon = icon;
	}
	
	public CelestialBody(Coordinate loc, Coordinate bary, double mass, double radius, Vector3d velocity, String image, String icon)
	{
		this.loc = loc;
		this.bary = bary;
		this.mass = mass;
		this.velocity = velocity;
		this.radius = radius;
		this.image = image;
		this.icon = icon;
	}
	
	/**
	 * Returns true if the coordinate is within the location of the Celestial Body
	 * @param coordinate
	 * @return
	 */
	public boolean collision(Coordinate c)
	{
		// Calculate distance from origin ( (a1-a2)^2 + (b1-b2)^2 + (c1-c2)^2)^1/2 
		double deltaA = Math.pow((loc.getX() - c.getX()), 2);
		double deltaB = Math.pow((loc.getY() - c.getY()), 2);
		double deltaC = Math.pow((loc.getZ() - c.getZ()), 2);
		double dist = deltaA + deltaB + deltaC;
		dist = Math.sqrt(dist);
		if(dist < radius)
			return true;
		return false;
	}
	
	public void setColour(Color colour)
	{
		this.colour = colour;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}
	
	public void setIcon(String icon)
	{
		this.icon = icon;
	}


	public CelestialBody copyOf()
	{
		return new CelestialBody(this.loc, this.bary, this.mass, this.radius, this.velocity, this.image, this.icon);
	}
}

