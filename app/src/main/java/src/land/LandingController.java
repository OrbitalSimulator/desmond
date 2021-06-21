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
	private final double DRAG_COEFFICIENT = 1.1e-4;	// page 1187, from https://pdfs.semanticscholar.org/5410/30f5b4c387a3d5d06fbee8549347d6bddf82.pdf
	private final double AIR_DENSITY = 5.428; 		// https://www.aero.psu.edu/avia/pubs/LanSch17.pdf, page 3
	private final double LANDER_AREA = 3.822; 		// Mars InSight lander was 1.56 meters in diameter, pi * radius^2
	private final double PARACHUTE_AREA = 1000;
	private final double airPresSeaLevel = 1.5;		// 1.5 bars
	private final double grav = 1.352;				// acceleration due to gravity
	private final double k = 1.38064852e-23;		// Boltzmann constants
	private final double m = 27.60867588e-3;		// average molar mass of air molecules
	
	protected double stepSize = 1;
	protected double deployParachuteHeight = 5000;
	protected int parachuteState = 0;
	protected boolean parachuteDeployed = false;
	
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
		
		Logger.logCSV("landing_controller3", "Time,Pos X, Pos Y, Pos Z, Vel X, Vel Y, Vel Z");
		
		double time = 0;
		while(!testHeight(currentState.position.get(0), currentState.position.get(1), planetRadius))
		{
			Logger.logCSV("landing_controller3", time + "," + currentState.position.get(0).toCSV() + currentState.velocity.get(0).toCSV());
						
			Vector3d drag = calculateDrag(currentState.velocity.get(0), currentState.position.get(0), stepSize, planetRadius);
			currentState.velocity.set(0, currentState.velocity.get(0).sub(drag));
			currentState = solver.step(f, time, currentState, stepSize);
			
			trajectory.add(new LanderObject(currentState.position.get(0), 0));
			time = time + stepSize;
			
			if(time > 1e7)// Safety cutoff
				break;
		}
		
		return trajectory;
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
	 * Implementing Fd = Cd * rho * (V^2 * Area)/2 * unitVector
	 * Represents (respectively): Force of drag = dragCoefficient * airDensity * magnitudeOfVelocity^2 * 1/2 * unitVector  
	 */
	public Vector3d calculateDrag(Vector3d velocity, Vector3d position, double stepSize, double radius)
	{
		if (velocity.getX() == 0 && velocity.getY() == 0)
			return new Vector3d(0,0,0);
		
		double totalArea = LANDER_AREA;
		if(parachuteDeployed)
			totalArea = totalArea + getParachuteState();
		
		double veloMagnitude = velocity.norm();
		double drag = DRAG_COEFFICIENT * airPressureScaling(position, radius) * ((totalArea * (veloMagnitude * veloMagnitude))/2);
		drag = drag * stepSize;
		
		Vector3d direction = velocity.unitVector();
		return direction.mul(drag);						//scale the unit vector by the constants we have, to get the actual drag force vector	
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
	
	protected void deployParachute()
	{
		parachuteDeployed = true;
		Logger.logCSV("openloop_controller", "Parachute Deployed!");
	}
	
	protected double getParachuteState()
	{
		if(parachuteState < 100)
			parachuteState = parachuteState + 5;
		
		return (PARACHUTE_AREA/100) * parachuteState;
	}
	
	
	/*
	 * Method represents:
	 * Ph = P0 * e^((-m*g*h)/k*T
	 * Where:
	 * Ph is pressure at height, P0 pressure at seal level
	 * m is mass of one molecule of..., g is gravitational acceleration, h is the height	
	 * k is Boltzmann's constant, t is the absolute temperature (in kelvin)
	 * 
	 * 
	 * if impact() is true, return 1.5 (on surface)
	 */
	public double airPressureAt(Vector3d height) {
		
		double h = height.dist(new Vector3d());
		double t = generateTemp(h);
		
		double result = airPresSeaLevel * Math.exp((-m*grav*h)/k*t);
		return result;
	}
	
	public double generateTemp(double height) {
		if (height == 0) {							// if impacted on surface hence temp is given
			return 94;
		}
		else if (0<height && height<50000) {
			long min = (long) 71;
			long max = (long) 81;
			double randValue = (double)Math.floor(Math.random()*(max-min+1)+min);
			return randValue;
		}
		else if (height>=50000 && height<200000) {
			long min = (long) 71;
			long max = (long) 180;
			double randValue = (double)Math.floor(Math.random()*(max-min+1)+min);
			return randValue;
		}
		else {
			long min = (long) 160;
			long max = (long) 180;
			double randValue = (double)Math.floor(Math.random()*(max-min+1)+min);
			return randValue;
		}
	}
	
	public double airPressureScaling(Vector3d point, double radius) {
		
		double atmosphereMaxRange = 600000;
		double originToPoint = point.dist(new Vector3d());
		double realDistance = originToPoint - radius;
		
		if (realDistance>=atmosphereMaxRange) {
			return 0;
		}
		else if (-radius<realDistance && realDistance<=0) {			//touching or inside the planet
			return airPresSeaLevel;
		}
		
		double scaleOfDistance = 1-(realDistance/atmosphereMaxRange);
		return scaleOfDistance*airPresSeaLevel;
	}
}
