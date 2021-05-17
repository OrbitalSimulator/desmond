package src.traj;

import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	public static Vector3d[] plot(Universe universe, SimulationSettings settings)
	{					
		int startPlanet = 7;
		int endPlanet = 9;
		
		// Lookup end planet
		//	-> P = startPlanet(t = 0).closestLaunchPoint(endPlanet(t = end)
		Vector3d end = universe.universe[startPlanet][universe.universe.length-1].location;
		
		// Lookup start planet
		//	-> V = V of start planet
		settings.probeStartPosition = universe.universe[endPlanet][0].closestLaunchPoint(end);
		settings.probeStartVelocity = universe.universe[endPlanet][0].velocity;
		
		RouteController rc = new RouteController(universe, "titan", settings);
		return rc.getTrajectory();
	}
	
}
