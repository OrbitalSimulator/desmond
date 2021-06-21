package src.land;

import src.peng.State;
import src.peng.Vector3d;

public class ClosedLoopController extends LandingController
{
	private final Vector3d MAX_BURN = new Vector3d(10,0,0);
	private double burnHeight = 500;
	
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
			double angle = getAngleFromState(currentState);
			if(	angle < 30)
			{
				
			} 
			else if (angle > 330)
			{
				
			}
			
			currentState.velocity.set(0, currentState.velocity.get(0).add(MAX_BURN));
		}
		return currentState;
	}
	
	private double getAngleFromState(State state)
	{
		return getAngle(state.velocity.get(0), state.position.get(1), state.position.get(0));
	}
	
	public double getAngle(Vector3d velocity, Vector3d landLocation, Vector3d probeLocation)
	{
		double a = distance(landLocation,velocity);
		double b = distance(probeLocation,landLocation);
		double c = distance(probeLocation,velocity);
		double cosA = cosineLaw(a,b,c);
		return cosA;
	}
	
	private Vector3d scaleBurn(Vector3d velocity)
	{
		if(Math.abs(velocity.norm()) > Math.abs(MAX_BURN.norm()))
			return MAX_BURN;
		
		double percentage = Math.abs(MAX_BURN.norm()) - Math.abs(velocity.norm());
		return null;
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
