package src.traj;

import java.util.ArrayList;

import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	static ArrayList<Vector3d[]> trajectories = new ArrayList<>();
	
	public static Vector3d[] simplePlot(Universe universe, SimulationSettings settings)
	{												
		LaunchController lc = new LaunchController(universe, 0, settings);
		return lc.getTrajectory();
	}
	
	public static Vector3d[] plotRoute(Universe universe, SimulationSettings settings)
	{							
		int earth = 3;
		int titan = 8;
		
		CelestialBody earthPsn = universe.universe[earth][0];
		Vector3d titanPsn = universe.universe[titan][settings.noOfSteps].location;
		
		SimulationSettings outSettings = settings.copy();
		outSettings.probeStartVelocity = universe.universe[earth][0].velocity;
		outSettings.probeStartPosition = earthPsn.closestLaunchPoint(titanPsn);
		outSettings.noOfSteps = settings.noOfSteps/2;
		RouteController outController = new RouteController(universe, earth, titan, outSettings);
		trajectories.add(outController.getTrajectory());
		
		SimulationSettings backSettings = outController.getFinalSettings();
		backSettings.probeStartVelocity = new Vector3d();
		backSettings.noOfSteps = settings.noOfSteps/2;
		backSettings.stepOffset = settings.noOfSteps/2;
		RouteController backController = new RouteController(universe, titan, earth, backSettings);
		trajectories.add(backController.getTrajectory());
		
		return sewUpTrajectories();
	}
	
	public static Vector3d[] sewUpTrajectories()
	{
		int totalSteps = 0;
		for(Vector3d[] each: trajectories)
		{
			totalSteps += each.length;
		}
		
		Vector3d[] array = new Vector3d[totalSteps];
		int pntr = 0;
		for(Vector3d[] each: trajectories)
		{
			for(int i = 0; i < each.length; i++)
			{
				array[pntr++] = each[i];
			}
		}
		return array;
	}
}
