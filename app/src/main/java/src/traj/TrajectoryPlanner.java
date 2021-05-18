package src.traj;

import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	public static void plot(Universe universe, SimulationSettings settings)
	{					
		int start = 3;
		int end = 7;
		
		settings.probeStartVelocity = universe.universe[start][0].velocity;
		
		SimulationSettings leg1 = settings.copy();
		leg1.noOfSteps = 2700;
		RouteController rc1 = new RouteController(universe, start, end, leg1);
		
		SimulationSettings leg2 = rc1.getFinalSettings();
		RouteController rc2 = new RouteController(universe, end, start, leg2);
	}

	public static Vector3d[] plotOrbit(Universe universe, SimulationSettings settings)
	{
		OrbitController oc = new OrbitController(universe, 0, settings);
		return oc.getTrajectory();
	}
}
