package src.solv;

import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.StateInterface;

public class ODESolver implements ODESolverInterface
{
	/**
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h)
    {      	
    	State[] results = new State[(int)Math.ceil((tf/h)+1)];						// Instantiate new StateInterface array of size Round(tf/h)+1
        int index = 0;																// Index to track additions to results
        State currentState = (State) y0;											// State variable to allow updates within the loop
        results[index++] = currentState;											// Record initial state within results:
        double t = 0;																// Instantiate starting time at t=0;

        while(t < tf)
        {
            currentState = step(f, t, currentState, h);								// Determine new current state through a single step of the Euler method
            results[index] = currentState;											// Add new state to the results array
            index++;
            t += h;																	// Increment the current time by the step
        }
        return results;																// return state array
    }

    /**
     * Solve the differential equation by taking multiple steps.
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts)
    {
    	State[] results = new State[ts.length];						//Instantiate new StateInterface array of size of ts.length
        int index = 0;												//Index to track additions to results
        State currentState = (State) y0;
        results[index++] = currentState;							//Record initial state within results:
        double t = ts[0];											//Instantiate starting time at t=ts[0];
        
        //While the current time is less that the last specified time in ts
        while(t< ts[ts.length-1])
        {    
            double h = ts[index] - ts[index-1];						//Determine step size
            currentState = step(f, t, currentState, h);				//Determine new current state through a single step of the Euler method
            results[index] = currentState;							//Add new state to the results array
            index++;												//Increment index
            t += h;													//Increment the current time by the step
        }
        return results;
    }
    /**
     * Update rule for one step.
     * Essentially adding the calculated changes (In acceleration, position)
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) 
	{
		return null; // Implementation required by specific solvers
	}
	
    public State step(ODEFunctionInterface f, double t, State y, double h)
    {
    	return null; // Implementation required by specific solvers
    }
}
