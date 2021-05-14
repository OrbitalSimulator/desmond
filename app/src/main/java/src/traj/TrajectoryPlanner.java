package src.traj;

import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	public static Vector3d[] plot(Universe universe, SimulationSettings settings)
	{					
		// Lookup start planet
		//	-> V = V of start planet
		// Lookup end planet
		//	-> P = startPlanet(t = 0).closestLaunchPoint(endPlanet(t = end)
		
		
		Vector3d titan = universe.universe[8][universe.universe.length-1].location;
		universe.universe[3][0].closestLaunchPoint(titan);
		
		RouteController rc = new RouteController(universe, "titan", settings);
		return rc.getTrajectory();
	}
	
}
