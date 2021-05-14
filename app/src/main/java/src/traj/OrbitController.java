package src.traj;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.Verlet;
import src.univ.Universe;

public class OrbitController extends GuidanceController
{

	public OrbitController(Universe universe, String target,  SimulationSettings settings)
	{
		super(universe, target);
		trajectory = planRoute(settings);
	}

	private Vector3d[] planRoute(SimulationSettings settings)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);

		Verlet solver = new Verlet();
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps+1];
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
			currentVelocity = getProbeVelocity(nextState);
			currentStep++;
			trajectory[currentStep] = currentPosition;
		}
		return trajectory;
	}

}