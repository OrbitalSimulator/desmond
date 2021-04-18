package src.peng;

import src.univ.Universe;

public class Simulation 
{	
	/*
	 * Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
	 *
	 * @param   p0      the starting position of the probe, relative to the earth's position.
	 * @param   v0      the starting velocity of the probe, relative to the earth's velocity.
	 * @param   ts      the times at which the states should be output, with ts[0] being the initial time.
	 * @return  an array of size ts.length giving the position of the probe at each time stated, 
	 *          taken relative to the Solar System barycentre.
	 */
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts)
	{
		/* Build */
		Universe universe = new Universe(p0, v0);										//Initialise the universe
		StateInterface initialState = universe.initialState();							//Generate the initial state of the universe
		ODEFunctionInterface funct = new NewtonGravityFunction(universe.getMasses());	//Initialise ODEFunctionInterface - contains call method

		/* Compute */
		RungeKutta4th solver = new RungeKutta4th();		 								//Call physics engine to determine intermediate states of the system.
		StateInterface[] results = solver.solve(funct, initialState, ts);				//Record intermediate states
		universe.update(results);														//Convert the obtained results to CelestialBodies for visualisation display

		/* Output */
		Vector3dInterface[] ret = output(results);
		return ret; 															//Returns an array of the probes locations
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
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h)
	{
		/* Build */								
		Universe universe = new Universe(p0, v0);										//Initialise the universe
		StateInterface initialState = universe.initialState(); 							//Generate the initial state of the universe
		ODEFunctionInterface funct = new NewtonGravityFunction(universe.getMasses());	//Initialise ODEFunctionInterface - contains call method			

		/* Compute */
		RungeKutta4th solver = new RungeKutta4th();										//Call physics engine to determine intermediate states of the system.
		StateInterface[] results = solver.solve(funct, initialState, tf, h);			//Record intermediate states
		universe.update(results);														//Convert the obtained results to CelestialBodies for visualisation display

		/* Display */
		Vector3dInterface[] ret = output(results);
		return ret;																		//Returns an array of the probes locations
	}


	//TODO Make dynamic at next stage with regard to probe position
	public Vector3d[] output(StateInterface[] states)
	{
		Vector3d[] output = new Vector3d[states.length];				//Instantiate Vector array to length of states

		int index = 0;     
		for(int i=0; i< states.length; i++) 							//Iterates through the states
		{  
			State temp = (State)states[i];								//Cast the StateInterface into a state object
			int probeIndex = temp.position.size() -1;					//Access each state and derive the probe location.
			output[index] = temp.position.get(probeIndex);				//determine last element of each state = probe
			index++;
		}

		return output;
	}
}
