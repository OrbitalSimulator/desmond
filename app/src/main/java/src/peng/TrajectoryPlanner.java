package src.peng;

import java.util.ArrayList;

import src.conf.SimulationSettings;
import src.solv.RungeKutta4th;
import src.univ.Universe;

public abstract class TrajectoryPlanner 
{	
	public static Vector3d[] plot(Universe universe, SimulationSettings settings)
	{					
		double[] masses = addMassToEnd(universe.masses, 700);
		ODEFunctionInterface funct = new NewtonGravityFunction(masses);
		
		RungeKutta4th solver = new RungeKutta4th();									
		Vector3d[] trajectory = new Vector3d[settings.noOfSteps];
		trajectory[0] = (Vector3d) settings.probeStartPosition;
		
		int currentStep = 0;
		Vector3d currentPosition = (Vector3d) settings.probeStartPosition;
		Vector3d currentVelocity = (Vector3d) settings.probeStartVelocity;
		while(currentStep < settings.noOfSteps)
		{
			// Add impulse to the probe
			//if(currentStep == 1000) currentVelocity = currentVelocity.add(new Vector3d(5000,5000,0));
			
			double currentTime = currentStep * settings.stepSize;
			State currentState = addProbe(universe.getStateAt(currentStep), currentPosition, currentVelocity);
			State nextState = solver.step(funct, currentTime, currentState, settings.stepSize);
			
			currentPosition = getProbePosition(nextState);
			currentPosition = getProbePosition(nextState);
			trajectory[currentStep++] = currentPosition;
		}
		return trajectory;
	}
	
	/*
	 * Create a new deep copy state with the probe added (avoiding side effects on universe) 
	 */
	private static State addProbe(State state, Vector3d position, Vector3d velocity)
	{
		ArrayList<Vector3d> velocities = new ArrayList<Vector3d>();
		state.velocity.forEach((Vector3d v) -> velocities.add(v));
		velocities.add(velocity);
		
		ArrayList<Vector3d> positions = new ArrayList<Vector3d>();
		state.velocity.forEach((Vector3d p) -> positions.add(p));
		positions.add(position);
		
		return new State(velocities, positions, state.time);
	}
	
	private static Vector3d getProbePosition(State state)
	{
		int index = state.position.size() -1;
		return state.position.get(index);
	}
	
	private static Vector3d getProbeVelocity(State state)
	{
		int index = state.velocity.size() -1;
		return state.velocity.get(index);
	}
	
	private static double[] addMassToEnd(double[] array, double value)
	{
		double[] copy = new double[array.length+1];
		for(int i = 0; i < array.length; i++)
		{
			copy[i] = array[i];
		}
		copy[array.length] = value;
		return copy;
	}
}
