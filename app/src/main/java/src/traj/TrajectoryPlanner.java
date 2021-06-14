package src.traj;

import java.io.IOException;
import java.util.ArrayList;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.Universe;

public abstract class TrajectoryPlanner
{
	static ArrayList<Vector3d[]> trajectories = new ArrayList<>();

	public static void integratedPlot(Universe universe, SimulationSettings settings)
	{
		/*Route to Titan*/
		SimulationSettings routeToTitanSettings = createRouteToTitanSettings(settings);
		newtonRaphsonPlot(universe, 3, 8, routeToTitanSettings, new Vector3d(0,0,0));
		//TODO Avoid magic number (Reason for 2 is initial position + plotting same state)
		int stepOffsetTitan = routeToTitanSettings.stepOffset - 2;
		CelestialBody[] lastState = universe.getCelestialBodyAt(routeToTitanSettings.noOfSteps);

		/*Titan Orbit*/
		SimulationSettings orbitSettings = createOrbitalSettings(settings, lastState);
		Universe subUniverse = new Universe(orbitSettings);
		Vector3d[] trajectory = plotOrbit(subUniverse, orbitSettings);
		universe.addPermTrajectory(trajectory);

		/*RouteToEarth*/
		int orbitOffset = (int) (orbitSettings.stepSize * orbitSettings.noOfSteps / routeToTitanSettings.stepSize);
		System.out.println("Orbit offset: " + orbitOffset);
		SimulationSettings routeToEarthSettings = createRouteToEarthSettings(settings, stepOffsetTitan, orbitOffset);
		newtonRaphsonPlot(universe, 8, 3, routeToEarthSettings, new Vector3d(-8000,8000,0));
	}
	public static Vector3d[] simplePlot(Universe universe, SimulationSettings settings)
	{
		LaunchController lc = new LaunchController(universe, 0, settings);
		return lc.getTrajectory();
	}

	public static Vector3d[] plotOrbit(Universe universe, SimulationSettings settings)
	{
		OrbitController oc = new OrbitController(universe, 8, settings);
		return oc.getTrajectory();
	}

	public static Vector3d[] newtonRaphsonPlot(Universe universe, int origin, int target, SimulationSettings settings, Vector3d startingVelocity)
	{
		NewtonRaphson nr = new NewtonRaphson(universe, origin, target, settings, startingVelocity);
		Vector3d optimalVelocity = nr.newtonRaphsonIterativeMethod();
		Vector3d[] trajectory = nr.planRoute(optimalVelocity);
		settings.stepOffset = settings.noOfSteps;
		return trajectory;

	}

	public static Vector3d[] plotRoute(Universe universe, SimulationSettings settings)
	{
		int earth = 3;
		int titan = 8;

		int wp1 = settings.noOfSteps/4;
		int wp2 = settings.noOfSteps/4 * 2;

		CelestialBody earthStartPsn = universe.U[earth][0];
		Vector3d titanEndPsn = universe.U[titan][wp1].location;

		SimulationSettings outSettings = settings.copy();
		outSettings.probeStartVelocity = universe.U[earth][0].velocity;
		outSettings.probeStartPosition = earthStartPsn.closestLaunchPoint(titanEndPsn);
		outSettings.noOfSteps = wp1;
		RouteController outController = new RouteController(universe, earth, titan, outSettings);
		trajectories.add(outController.getTrajectory());

		CelestialBody titanStartPsn = universe.U[titan][wp2];
		Vector3d earthEndPsn = universe.U[earth][settings.noOfSteps].location;

		SimulationSettings backSettings = outController.getFinalSettings();
		backSettings.probeStartPosition = titanStartPsn.closestLaunchPoint(earthEndPsn);
		backSettings.probeStartVelocity = universe.U[titan][wp2].velocity;;
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

	public static SimulationSettings createRouteToTitanSettings(SimulationSettings baseSettings)
	{
		//Settings for 3 years
		SimulationSettings settingsToTitan = baseSettings.copy();
		settingsToTitan.noOfSteps = 9461;
		settingsToTitan.stepSize = 10000;
		return settingsToTitan;
	}

	public static SimulationSettings createRouteToEarthSettings(SimulationSettings baseSettings, int stepOffsetTitan, int stepOffsetOrbit)
	{
		SimulationSettings settingsToEarth = baseSettings.copy();
		settingsToEarth.noOfSteps = 9461;
		settingsToEarth.stepSize = 10000;
		settingsToEarth.stepOffset = stepOffsetTitan + stepOffsetOrbit;
		return settingsToEarth;
	}

	public static SimulationSettings createOrbitalSettings(SimulationSettings baseSettings, CelestialBody[] previousState)
	{
		SimulationSettings orbitSettings = baseSettings.copy();
		orbitSettings.celestialBodies = previousState;
		orbitSettings.noOfSteps = 100000;
		orbitSettings.stepSize = 50; //50 best so far
		return orbitSettings;
	}

}