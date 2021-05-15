package src.traj;

import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	public static Vector3d[] plot(Universe universe, SimulationSettings settings)
	{					
		RouteController rc = new RouteController(universe, "titan", settings);
		return rc.getTrajectory();
	}

	public static Vector3d[] plotOrbit(Universe universe, SimulationSettings settings)
	{
		OrbitController oc = new OrbitController(universe, "sun", settings);
		return oc.getTrajectory();
	}
	
}
