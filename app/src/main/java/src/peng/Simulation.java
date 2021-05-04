package src.peng;

import java.time.LocalDateTime;

import src.config.SettingsFileManager;
import src.config.SimulationSettings;
import src.univ.CelestialBody;
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
		SimulationSettings settings;
		try {
			settings = SettingsFileManager.load();
			settings.noOfSteps = (int) ((ts[1] - ts[0]) * 1E9);
			settings.probeStartPosition = probeStartPosition;
			settings.probeStartVelocity = probeStartVelocity;
			
			// Load or Create the universe from settings
			Universe universe = new Universe(settings); 								
			ODEFunctionInterface funct = new NewtonGravityFunction(universe.masses);
			
			// Add the start parameters of the probe
			
			
			// Call a single step of the solver to get the 
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
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
	//	/* Build */								
	//	int stepSizeNs = (int) (h*1E9);
	//	Universe universe = new Universe(probeStartPosition, probeStartVelocity, stepSizeNs);
	//	StateInterface initialState = universe.initialState(); 							//Generate the initial state of the universe
	//	ODEFunctionInterface funct = new NewtonGravityFunction(universe.getMasses());	//Initialise ODEFunctionInterface - contains call method			
    //
	//	/* Compute */
	//	RungeKutta4th solver = new RungeKutta4th();										//Call physics engine to determine intermediate states of the system.
	//	StateInterface[] results = solver.solve(funct, initialState, tf, h);			//Record intermediate states
	//	universe.update(results);														//Convert the obtained results to CelestialBodies for visualisation display
    //
	//	/* Display */
	//	Vector3dInterface[] trajectory = output(results);
		return null;									
	}

	public Vector3d[] output(StateInterface[] states)
	{
		Vector3d[] output = new Vector3d[states.length];				//Instantiate Vector array to length of states

		int index = 0;     
		for(int i=0; i< states.length; i++) 							
		{  
			State temp = (State)states[i];								//Cast the StateInterface into a state object
			int probeIndex = temp.position.size() -1;					//Access each state and derive the probe location.
			output[index] = temp.position.get(probeIndex);				//determine last element of each state = probe
			index++;
		}
		return output;
	}
}
