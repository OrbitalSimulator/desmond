package src.land;

import java.util.ArrayList;

import src.conf.Logger;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.ODESolver;
import src.solv.Verlet;

public class LandingController 
{
	private final double LANDER_AREA = 100; // TODO (CAN) get real value for Mars Lander
	private final double DRAG_COEFICIENT = 1; //TODO (CAN) get value
	
	public LandingController() 
	{
		
	}
	
	public Vector3d[] plotTrajectory(Vector3d landerLocation, 
			 						 Vector3d landerVelocity,
									 double landerMass,
									 Vector3d planetLocation,
									 Vector3d planetVelocity,
									 double planetMass,
									 double planetRadius)
	{
		double[] masses = {landerMass, planetMass};
	
		ArrayList<Vector3d> positions = new ArrayList<Vector3d>();
		positions.add(removeZDimension(normalise(landerLocation, planetLocation))); 		
		positions.add(new Vector3d(0,0,0));											// Planet always starts at 0,0,0
		
		ArrayList<Vector3d> velocities = new ArrayList<Vector3d>();
		velocities.add(removeZDimension(normalise(landerVelocity, planetVelocity))); 		
		velocities.add(new Vector3d(0,0,0));											// Planet always starts at 0,0,0
		
		State currentState = new State(velocities, positions);
		ODESolver solver = new Verlet();
		ODEFunctionInterface f = new NewtonGravityFunction(masses);
		
		Logger.logCSV("landing_controller", "Time,Pos X, Pos Y, Pos Z, Vel X, Vel Y, Vel Z");
		double time = 0;
		double stepSize = 0.1;
		while(!impact(currentState.position.get(0),planetRadius))
		{
			Logger.logCSV("landing_controller", time + "," + currentState.position.get(0).toCSV() + currentState.velocity.get(0).toCSV());
			currentState = solver.step(f, time, currentState, stepSize);
			time = time + stepSize;
		}
		
		return null;
	}
	
	/**
	 * @return true if the position is within the radius of (0,0,0)
	 */
	public boolean impact(Vector3d landerPosition, double radius)
	{
		double distance = landerPosition.dist(new Vector3d(0,0,0));
		distance = Math.abs(distance);
		return distance <= radius;
	}
	
	/**
	 * Normalise the vectors so that a is given relative to b where b = (0,0,0)
	 * @return a vector a relative to b
	 */
	protected Vector3d normalise(Vector3d a, Vector3d b)
	{
		double x = a.getX() - b.getX();
		double y = a.getY() - b.getY();
		double z = a.getZ() - b.getZ();
		return new Vector3d(x, y, z);
	}
	
	/**
	 * @return a new Vector3d object with x and y copied and a 0 value for z
	 */
	protected Vector3d removeZDimension(Vector3d v)
	{
		double x = v.getX();
		double y = v.getY();
		return new Vector3d(x, y, 0);
	}
	
	/**
	 * @return the force of drag exerted at the current velocity using the final values declared
	 * in the class
	 */
	protected Vector3d calculateDrag(Vector3d velocity)
	{
		// TODO (Can) 2hrs
		return new Vector3d();
	}
}
