package solv;

import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

public class RungeKutta2nd
{   
	/**
     * Update rule for one step.
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    public State rungeKuttaStep(ODEFunctionInterface f, double t, State y, double h)
    {    	
    	Rate a = (Rate) f.call(0, y);		// f' at left endpoint
    	Rate b = (Rate) f.call(t, y);		// f' at right endpoint
    	Rate rate = a.average(b);			// f' average
    	return y.addMultiple(h, rate);
    }
}
