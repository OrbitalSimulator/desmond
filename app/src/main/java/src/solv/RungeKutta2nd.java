package src.solv;

import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

public abstract class RungeKutta2nd extends ODESolver
{   
	/**
     * Update rule for one step.
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {    	
    	Rate a = (Rate) f.call(0, y);		// f' at left endpoint
    	Rate b = (Rate) f.call(t, y);		// f' at right endpoint
    	Rate rate = a.average(b);			// f' average
    	return y.addMul(h, rate);
    }
    
    public State step(ODEFunctionInterface f, double t, State y, double h)
    {    	
    	Rate a = (Rate) f.call(0, y);		// f' at left endpoint
    	Rate b = (Rate) f.call(t, y);		// f' at right endpoint
    	Rate rate = a.average(b);			// f' average
    	return (State) y.addMul(h, rate);
    }
}
