package src.traj;

import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.ODESolver;
import src.solv.RungeKutta4th;
import src.solv.Verlet;
import src.univ.Universe;
import src.visu.Visualiser;

public class LaunchController extends GuidanceController
{
	private ODESolver solver = new RungeKutta4th();

	public LaunchController(Universe universe, int target, SimulationSettings settings) 
	{
		super(universe, target);
		launch(settings);		
	}
	
	private Vector3d[] launch(SimulationSettings settings)
	{
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);
		
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
			currentVelocity = getProbeVelocity(nextState);
			trajectory[currentStep++] = currentPosition;
		}
		Visualiser.getInstance().addTempTrajectory(trajectory);
		return trajectory;
	}

}
