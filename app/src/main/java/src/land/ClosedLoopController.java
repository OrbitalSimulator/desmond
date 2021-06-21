package src.land;

import src.peng.State;
import src.peng.Vector3d;

public class ClosedLoopController extends LandingController
{
	
	private double burnHeight = 500;
	private Vector3d burn = new Vector3d(5,0,0);
	
	public ClosedLoopController() 
	{
		super();
		logFileName = "closedloop_controller";
	}
	
	@Override
	protected State controllerAction(State currentState, double planetRadius)
	{
		
		if(testHeight(currentState, planetRadius + burnHeight))
		{
				currentState.velocity.set(0, currentState.velocity.get(0).add(burn));
		}
		return currentState;
	}
	
	public double getAngle(Vector3d velocity, Vector3d landLocation, Vector3d probeLocation)
	{
		double a = distance(landLocation,velocity);
		double b = distance(probeLocation,landLocation);
		double c = distance(probeLocation,velocity);
		double cosA = cosineLaw(a,b,c);
		return cosA;
	}

	private double cosineLaw(double a, double b, double c)
	{
		// cos(A) = (b^2 + c^2 - a^2) / ( 2*b*c)
		double value= Math.acos(Math.pow(b,2)/(2*b*c) + Math.pow(c,2)/(2*b*c) - Math.pow(a,2)/(2*b*c));
		return Math.toDegrees(value);
	}

	private double distance(Vector3d a, Vector3d b)
	{
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)+ Math.pow(a.getZ() - b.getZ(),2));
	}
}
