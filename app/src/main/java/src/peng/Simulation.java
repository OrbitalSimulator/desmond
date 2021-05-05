package src.peng;

import java.io.IOException;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.univ.Universe;

public class Simulation 
{		
	/*
	 * Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
	 *
	 * @param   ts      the times at which the states should be output, with ts[0] being the initial time.
	 * @return  an array of size ts.length giving the position of the probe at each time stated, 
	 *          taken relative to the Solar System barycentre.
	 */
	public Vector3dInterface[] trajectory(Vector3dInterface probeStartPosition, Vector3dInterface probeStartVelocity, double[] ts)
	{
		//TODO (Leon) this needs to be implemented to calculate at random steps ... fuck knows why

			return null;
	}

	/*
	 * Simulate the solar system with steps of an equal size.
	 * The final step may have a smaller size, if the step-size does not exactly divide the solution time range.
	 *
	 * @param   tf      the final time of the evolution.
	 * @param   h       the size of step to be taken
	 * @return  an array of size round(tf/h)+1 giving the position of the probe at each time stated, 
	 *          taken relative to the Solar System barycentre
	 */
	public Vector3dInterface[] trajectory(Vector3dInterface probeStartPosition, Vector3dInterface probeStartVelocity, double tf, double h)
	{
		SimulationSettings settings;
		try {
			settings = SettingsFileManager.load();
			settings.noOfSteps = (int) ((tf/h)+2);
			settings.stepSize = h ;
			settings.probeStartPosition = probeStartPosition;
			settings.probeStartVelocity = probeStartVelocity;
			
			Universe universe = new Universe(settings); 								
			Vector3d[] trajectory = TrajectoryPlanner.plot(universe, settings);
			return trajectory;
		} 
		catch (IOException e) 
		{
			System.out.println("Settings File Not Found ... Exiting");
			e.printStackTrace();
			return null;
		}								
	}	
	
}
