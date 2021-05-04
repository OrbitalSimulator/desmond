package src.peng;

import java.lang.Math;

public class EulerSolver implements ODESolverInterface
{
	public static final boolean DEBUG = false;
	
	/**
     * Update rule for one step.
     * Essentially adding the calculated changes (In acceleration, position)
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {
        return y.addMul(h, f.call(t, y));
    }

    /**
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h)
    {
        StateInterface[] results = new StateInterface[(int)Math.ceil((tf/h)+1)];	// Instantiate new StateInterface array of size Round(tf/h)+1
        int index = 0;																// Index to track additions to results
        results[index] = y0;														// Record initial state within results:
        index++;
        StateInterface currentState = y0;											// State variable to allow updates within the loop
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
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts)
    {
        StateInterface[] results = new StateInterface[ts.length];	//Instantiate new StateInterface array of size of ts.length
        int index = 0;												//Index to track additions to results
        results[index] = y0;										//Record initial state within results:
        index++;
        StateInterface currentState = y0;							//State variable to allow updates within the loop
        double t = ts[0];											//Instantiate starting time at t=ts[0];
        
        if(DEBUG)
        {
            System.out.println("ts length"+ ts.length);
            System.out.println("Starting time: "+ t);
            System.out.println("End time: " + ts[ts.length-1]);
        }

        //While the current time is less that the last specified time in ts
        while(t< ts[ts.length-1])
        {    
            double h = ts[index] - ts[index-1];						//Determine step size
            if(DEBUG) System.out.println("Time step: "+ h);
            currentState = step(f, t, currentState, h);				//Determine new current state through a single step of the Euler method
            results[index] = currentState;							//Add new state to the results array
            index++;												//Increment index
            t += h;													//Increment the current time by the step
        }
        return results;
    }
}

