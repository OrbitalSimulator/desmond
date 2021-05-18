package src.traj;

import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	public static Vector3d[] plot(Universe universe, SimulationSettings settings)
	{					
		int startPlanet = 3;
		int endPlanet = 8;
		
		SimulationSettings leg1 = settings.copy();
		leg1.noOfSteps = 2700;
		SimulationSettings leg2 = settings.copy();
		leg2.noOfSteps = 2700;
		leg2.stepOffset = 2700;
		
		// Lookup end planet
		//	-> P = startPlanet(t = 0).closestLaunchPoint(endPlanet(t = end)
		Vector3d end = universe.universe[endPlanet][universe.universe.length-1].location;
		
		// Lookup start planet
		//	-> V = V of start planet
		settings.probeStartPosition = universe.universe[startPlanet][0].closestLaunchPoint(end);
		settings.probeStartVelocity = universe.universe[startPlanet][0].velocity;
		
		RouteController rc = new RouteController(universe, 8, settings);
		return rc.getTrajectory();
	}

	public static Vector3d[] plotOrbit(Universe universe, SimulationSettings settings)
	{
		OrbitController oc = new OrbitController(universe, "sun", settings);
		return oc.getTrajectory();
	}
	
}
