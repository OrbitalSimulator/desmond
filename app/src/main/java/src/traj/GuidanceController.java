package src.traj;

import java.util.ArrayList;

import src.conf.SimulationSettings;
import src.peng.State;
import src.peng.Vector3d;
import src.univ.Universe;

public class GuidanceController 
{
	protected Universe universe;
	protected int target;
	protected Vector3d[] trajectory;
	protected Vector3d[] corrections;
	protected SimulationSettings finalSettings;
		
	public GuidanceController(Universe universe, int target) 
	{
		this.universe = universe;
		this.target = target;
	}
	
	public Vector3d[] getTrajectory() 
	{
		return trajectory;
	}
	
	public Vector3d[] getCorrections() 
	{
		return corrections;
	}
	
	public SimulationSettings getFinalSettings()
	{
		return finalSettings;
	}
	
	/*
	 * Create a new deep copy state with the probe added (avoiding side effects on U) 
	 */
	protected static State addProbe(State state, Vector3d position, Vector3d velocity)
	{
		ArrayList<Vector3d> velocities = new ArrayList<Vector3d>();
		state.velocity.forEach(vector -> velocities.add(vector));
		velocities.add(velocity);
		
		ArrayList<Vector3d> positions = new ArrayList<Vector3d>();
		state.position.forEach(vector -> positions.add(vector));
		positions.add(position);
		
		return new State(velocities, positions, state.time);
	}
	
	protected static Vector3d getProbePosition(State state)
	{
		int index = state.position.size() -1;
		return state.position.get(index);
	}
	
	protected static Vector3d getProbeVelocity(State state)
	{
		int index = state.velocity.size() -1;
		return state.velocity.get(index);
	}
	
	protected static double[] addMassToEnd(double[] array, double value)
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
