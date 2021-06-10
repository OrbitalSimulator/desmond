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

	public static Vector3d[] newtonRaphsonPlot(Universe universe, int origin, int target, SimulationSettings settings, double launchTime, double targetTime)
	{
		NewtonRaphson nr = new NewtonRaphson(universe, origin, target, settings, launchTime, targetTime);
		return nr.getTrajectory();
	}

	public static Vector3d[] plotRoute(Universe universe, SimulationSettings settings)
	{
		int earth = 3;
		int titan = 8;

		int wp1 = settings.noOfSteps/4;
		int wp2 = settings.noOfSteps/4 * 2;

		CelestialBody earthStartPsn = universe.universe[earth][0];
		Vector3d titanEndPsn = universe.universe[titan][wp1].location;

		SimulationSettings outSettings = settings.copy();
		outSettings.probeStartVelocity = universe.universe[earth][0].velocity;
		outSettings.probeStartPosition = earthStartPsn.closestLaunchPoint(titanEndPsn);
		outSettings.noOfSteps = wp1;
		RouteController outController = new RouteController(universe, earth, titan, outSettings);
		trajectories.add(outController.getTrajectory());

		CelestialBody titanStartPsn = universe.universe[titan][wp2];
		Vector3d earthEndPsn = universe.universe[earth][settings.noOfSteps].location;

		SimulationSettings backSettings = outController.getFinalSettings();
		backSettings.probeStartPosition = titanStartPsn.closestLaunchPoint(earthEndPsn);
		backSettings.probeStartVelocity = universe.universe[titan][wp2].velocity;;
		backSettings.noOfSteps = (settings.noOfSteps/4);
		backSettings.stepOffset = wp2;
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