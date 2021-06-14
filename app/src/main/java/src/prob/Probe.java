package src.prob;

import src.peng.Vector3d;

public class Probe 
{
	private final double MAX_THRUST = 3e7;  		// Newtons
	private final double EXHAUST_VELOCITY = 2e4;	// m/s
	private final double DRY_MASS = 7.8e4;			// kg
	private final double LANDER_MASS = 6e3;			// kg
	
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
		
	public void burn(Vector3d startVelocity, Vector3d finishVelocity, double stepSize)
	{		
		// (mass * (v2 - v1) / t) / (2e4 * t)
		Vector3d deltaVelocity = finishVelocity.sub(startVelocity);
		double deltaVelocityNorm = Math.abs(deltaVelocity.norm());
		double acceleration = deltaVelocityNorm / stepSize;
		double massOfFuelUsed = (getMass() * acceleration)/(EXHAUST_VELOCITY * stepSize);

		fuelMass = fuelMass - massOfFuelUsed;	
	}
	
	public double getFuelMass()
	{
		return fuelMass;
	}
	
	public void reset()
	{
		fuelMass = 1e5;
		landerAttached = true;
	}
		
}
