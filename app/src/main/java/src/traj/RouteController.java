package src.traj;

import java.util.ArrayList;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.peng.Vector3dInterface;
import src.solv.RungeKutta4th;
import src.univ.CelestialBody;
import src.univ.Universe;

public class RouteController extends GuidanceController
{
	private final double VELOCITY_MUTATION = 100000;		
	private final double PROXIMITY_ERROR = 10000;	// TODO (Leon) Load from settings -> add into settings file
	
	public RouteController(Universe universe, String targetName, SimulationSettings settings) 
	{
		super(universe, targetName);	
		trajectory = hillClimbAlogrithm(targetName, settings);
	}
	
	private Vector3d[] hillClimbAlogrithm(String targetName, SimulationSettings settings)
	{
		ArrayList<SimulationSettings> settingsGrid = generateSettingsGrid(settings);
		Vector3d target = getTargetVector(targetName, settings);
		
		SimulationSettings bestSettings = settings;
		Vector3d closestPoint = testRoute(settings);
		
		for(SimulationSettings each: settingsGrid)
		{
			Vector3d currentPoint = testRoute(each);
			if(closerToTarget(currentPoint, closestPoint, target))
			{
				bestSettings = each;
				closestPoint = currentPoint;
				System.out.println(currentPoint.toString());
			}
		}
		
		return planRoute(settings);
	}

	/*
	 * Plan the final route chosen and plot all of the points on the trajectory
	 */
	private Vector3d[] planRoute(SimulationSettings settings)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);
		
		RungeKutta4th solver = new RungeKutta4th();	
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps];
		trajectory[0] = (Vector3d) settings.probeStartPosition;
		
		int currentStep = 0;
		Vector3d currentPosition = (Vector3d) settings.probeStartPosition;
		Vector3d currentVelocity = (Vector3d) settings.probeStartVelocity;
		while(currentStep < settings.noOfSteps)
		{		
			double currentTime = currentStep * settings.stepSize;
			State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
			State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);
			
			currentPosition = getProbePosition(nextState);
			currentPosition = getProbePosition(nextState);
			trajectory[currentStep++] = currentPosition;
		}
		return trajectory;
	}
	
	/*
	 * Test routes to compare different parameters return only the final location for comparison
	 */
	private Vector3d testRoute(SimulationSettings settings)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);
		
		RungeKutta4th solver = new RungeKutta4th();	
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps];
		trajectory[0] = (Vector3d) settings.probeStartPosition;
		
		int currentStep = 0;
		Vector3d currentPosition = (Vector3d) settings.probeStartPosition;
		Vector3d currentVelocity = (Vector3d) settings.probeStartVelocity;
		while(currentStep < settings.noOfSteps)
		{		
			double currentTime = currentStep * settings.stepSize;
			State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
			State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);
			
			currentPosition = getProbePosition(nextState);
			currentPosition = getProbePosition(nextState);
			trajectory[currentStep++] = currentPosition;
		}
		return trajectory[trajectory.length-1];
	}
	
	private ArrayList<SimulationSettings> generateSettingsGrid(SimulationSettings initialSettings)
	{
		ArrayList<SimulationSettings> outputSettings = new ArrayList<SimulationSettings>();
		
		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				for(int z = -1; z <= 1; z++)
				{
					Vector3d changeAmount = new Vector3d(VELOCITY_MUTATION * x, VELOCITY_MUTATION * y, VELOCITY_MUTATION * z);
					SimulationSettings modifiedSettings = initialSettings.copy();
					modifiedSettings.probeStartPosition = initialSettings.probeStartPosition.add(changeAmount);
					outputSettings.add(modifiedSettings);
				}
			}
		}
		return outputSettings;
	}
	
	/*
	 * returns true if a is closer to the target than b
	 */
	private boolean closerToTarget(Vector3d a, Vector3d b, Vector3d target)
	{
		double dA = a.dist(target);
		double dB = b.dist(target);
		if( dA > dB )
			return true;
		return false;
	}
	
	private Vector3d getTargetVector(String target, SimulationSettings settings)
	{
		CelestialBody[] bodies = settings.celestialBodies;
		for(int i = 0; i < bodies.length; i++)
		{
			if(bodies[i].name.equalsIgnoreCase(target))
				return bodies[i].location;
		}
		return new Vector3d();
	}
}
