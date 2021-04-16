package src.univ;

import src.peng.Vector3d;

/**
 * Group 22
 * A class to hold a 3 dimensional coordinate reference of a CelestialBody
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class Coordinate extends Vector3d
{
	public DTG t;
	
	public Coordinate()
	{
		super(0,0,0);
		t = new DTG();
	}
	
	public Coordinate(Vector3d v3d, DTG t)
	{
		super(v3d.getX(),v3d.getY(),v3d.getZ());
		this.t = t;
	}
	
	public Coordinate(double x, double y, double z, DTG t)
	{
		super(x,y,z);
		this.t = t;
	}
	
	public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3d v3d)
	{
		x = v3d.getX();
		y = v3d.getY();
		z = v3d.getZ();
	}
	
	public void set(double x, double y, double z, DTG t)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}
	
	public void set(Vector3d v3d, DTG t)
	{
		x = v3d.getX();
		y = v3d.getY();
		z = v3d.getZ();
		this.t = t;
	}
	
	public DTG difference(Coordinate c)
	{
		DTG difference = this.t.sub(c.t);
		return difference;
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("X:" + x + " Y:" + y + " Z:" + z + " T:" + t);
		return s.toString();
	}
	
	public Vector3d toVector()
	{
		return new Vector3d(x,y,z);
	}
}
