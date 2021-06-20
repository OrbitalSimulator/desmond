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
	private final double LANDER_AREA = 1.91; 		// Mars InSight lander was 1.56 meters in diameter, pi * radius^2
	private final double DRAG_COEFFICIENT = 2.1;	// page 1187, from https://pdfs.semanticscholar.org/5410/30f5b4c387a3d5d06fbee8549347d6bddf82.pdf
	private final double AIR_DENSITY = 5.428; 		// https://www.aero.psu.edu/avia/pubs/LanSch17.pdf, page 3
	
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
		
		ArrayList<Vector3d> trajectory = new ArrayList<Vector3d>();
		
		Logger.logCSV("landing_controller", "Time,Pos X, Pos Y, Pos Z, Vel X, Vel Y, Vel Z");
		double time = 0;
		double stepSize = 0.1;
		while(!testHeight(currentState.position.get(0), currentState.position.get(1), planetRadius))
		{
			Logger.logCSV("landing_controller", time + "," + currentState.position.get(0).toCSV() + currentState.velocity.get(0).toCSV());
			currentState = solver.step(f, time, currentState, stepSize);
			trajectory.add(currentState.position.get(0));
			time = time + stepSize;
		}
		
		return toArray(trajectory);
	}
	
	/**
	 * @return true if the position is within the radius of (0,0,0)
	 */
	public boolean testHeight(Vector3d landerPosition, Vector3d planetPosition, double radius)
	{
		double distance = landerPosition.dist(planetPosition);
		distance = Math.abs(distance);
		return distance <= radius;
	}
	
	/**
	 * Normalise the vectors so that a is given relative to b where b = (0,0,0)
	 * @return a vector a relative to b
	 */
	public Vector3d normalise(Vector3d a, Vector3d b)
	{
		double x = a.getX() - b.getX();
		double y = a.getY() - b.getY();
		double z = a.getZ() - b.getZ();
		return new Vector3d(x, y, z);
	}
	
	/**
	 * @return a new Vector3d object with x and y copied and a 0 value for z
	 */
	public Vector3d removeZDimension(Vector3d v)
	{
		double x = v.getX();
		double y = v.getY();
		return new Vector3d(x, y, 0);
	}
	
	/**
	 * @param a velocity vector which is used to calculate drag from
	 * @return the force of drag exerted at the current velocity using the final values declared
	 * in the class
	 * 
	 * Implementing Fd = Cd * rho * V^2 * Area * 1/2 * unitVector
	 * Represents (respectively): Force of drag = dragCoefficient * airDensity * magnitudeOfVelocity^2 * 1/2 * unitVector  
	 */
	public Vector3d calculateDrag(Vector3d velocity)
	{
		if (velocity.getX() == 0 && velocity.getY() == 0) {
			return new Vector3d(0,0,0);
		}
		Vector3d unitVector = velocity.unitVector();
		Vector3d vectorDirection = unitVector.mul(-1);						//drag acts in the opposite direction in relation to the velocity.
		
		double veloMagnitude = velocity.norm();
		double constant = DRAG_COEFFICIENT * LANDER_AREA * AIR_DENSITY * veloMagnitude * veloMagnitude * 0.5;
		
		Vector3d dragForce = vectorDirection.mul(constant);					//scale the unit vector by the constants we have, to get the actual drag force vector	
		return dragForce;
	}
	
	/** Converts: Arraylist<Vector3d>  ->  Vector3d[] 
	 */
	protected Vector3d[] toArray(ArrayList<Vector3d> input)
	{
		Vector3d[] array = new Vector3d[input.size()];
		for(int i=0;i<input.size();i++)
		{
			array[i]= input.get(i);
		}
		return array;
	}
}
