package src.land;

import java.util.ArrayList;

import src.conf.Logger;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.ODESolver;
import src.solv.Verlet;

public class OpenLoopController extends LandingController
{
	private Vector3d height = new Vector3d(0,40000,0);

	public OpenLoopController() 
	{
		super();
		
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
		
		Logger.logCSV("openloop_controller", "Time,Pos X, Pos Y, Pos Z, Vel X, Vel Y, Vel Z");
		double time = 0;
		double stepSize = 0.1;
		while(!impact(currentState.position.get(0), currentState.position.get(1), planetRadius))
		{
			Logger.logCSV("openloop_controller", time + "," + currentState.position.get(0).toCSV() + currentState.velocity.get(0).toCSV());
			currentState = solver.step(f, time, currentState, stepSize);
			time = time + stepSize;
		}
	
		return null;
	}
	
	/*
	 * Checks whether a given point is below a certain distance, in relation to the radius of the planet
	 * 
	 * @param a vector representing a certain point
	 * @return boolean answering whether the point is below the certain height
	 */
	public boolean belowHeight(Vector3d point) {
		if (impact(point, new Vector3d(0,0,0), 1000)) 											//having radius be a random number
			return true;
		else if (point.dist(new Vector3d()) < height.dist(new Vector3d()))			
			return true;
		else if (point.dist(new Vector3d()) < height.dist(new Vector3d()))
			return true;
		else
			return false;
	}
}
