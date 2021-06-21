package src.land;

import java.util.ArrayList;

import src.conf.Logger;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.ODESolver;
import src.solv.Verlet;

public class ClosedLoopController extends LandingController
{
	public ClosedLoopController() 
	{
		super();
		
	}
	
	public ArrayList<LanderObject> plotTrajectory(Vector3d landerLocation, 
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
	
	ArrayList<LanderObject> trajectory = new ArrayList<LanderObject>();
	
	Logger.logCSV("closedloop_controller", "Time,Pos X, Pos Y, Pos Z, Vel X, Vel Y, Vel Z");
	double time = 0;
	double stepSize = 0.1;
	while(!testHeight(currentState.position.get(0), currentState.position.get(1), planetRadius))
	{
		Logger.logCSV("closedloop_controller", time + "," + currentState.position.get(0).toCSV() + currentState.velocity.get(0).toCSV());
		currentState = solver.step(f, time, currentState, stepSize);
		trajectory.add(new LanderObject(currentState.position.get(0), 0));
		time = time + stepSize;
	}
	
	return trajectory;
	}

	public double getAngle(Vector3d velocity, Vector3d landLocation, Vector3d probeLocation)
	{
		double a = distance(landLocation,velocity);
		double b = distance(probeLocation,landLocation);
		double c = distance(probeLocation,velocity);
		double cosA= cosineLaw(a,b,c);
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
