package src.solv;

import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

public class RungeKutta2nd extends ODESolver
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
       	Rate k1 = (Rate) f.call(t, y);										// k1 = h * f(t0,w0);
    	Rate k2 = (Rate) f.call(t + (h * 2/3), y.addMul((h * 2/3),k1));		// k2 = h * f((t0 + (h * 2/3)) , (w0 + (k1 * 2/3))); 
    	
    	Rate dydt = k2.scale(3);											// w1 = w0 + 1/4 * ( k1 + (3*k2));
     	dydt = k1.add(dydt);								 				// This is abbreviated to w0 + h * dydt 
    	dydt = dydt.scale(1/4);  	    					
    	return (State) y.addMul(h,dydt);
    }
    
    public State step(ODEFunctionInterface f, double t, State y, double h)
    {    	
       	Rate k1 = (Rate) f.call(t, y);										// k1 = h * f(t0,w0);
    	Rate k2 = (Rate) f.call(t + (h * 2/3), y.addMul((h * 2/3),k1));		// k2 = h * f((t0 + (h * 2/3)) , (w0 + (k1 * 2/3))); 
    	
    	Rate dydt = k2.scale(3);											// w1 = w0 + 1/4 * ( k1 + (3*k2));
     	dydt = k1.add(dydt);								 				// This is abbreviated to w0 + h * dydt 
    	dydt = dydt.scale(1/4);  	    					
    	return (State) y.addMul(h,dydt);
    }
}
