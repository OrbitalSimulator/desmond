package src.prob;

import src.peng.Vector3d;

public class Probe 
{
	private final double MAX_THRUST = 3e7;
	private final double EXHAUST_VELOCITY = 2e4;
	private final double DRY_MASS = 7.8e4;
	private final double LANDER_MASS = 6e3;
	
	private double fuelMass = 1e5;
	private boolean landerAttached = true;
	
	private static Probe instance;
	
	private Probe()
	{
		// Left private for singleton
	}
	
	public static Probe getInstance()
	{
		if(instance == null)
			instance = new Probe();				
		return instance;
	}
	
	public double getMass()
	{
		double totalMass = DRY_MASS + fuelMass;
		
		if(landerAttached)
			totalMass += LANDER_MASS;
		
		return totalMass;
	}
	
	public void releaseLander()
	{
		landerAttached = false;
	}
		
	public void burn(Vector3d velocity, double stepSize)
	{		
		double deltaVelocity = velocity.norm();
		double acceleration = deltaVelocity / stepSize;
		double force = getMass() * acceleration;
		
		
		double mDot = 
		
	}
	
	public double getFuelMass()
	{
		return fuelMass;
	}
		
}
