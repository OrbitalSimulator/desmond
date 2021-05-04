package solv;

import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

public class RungeKutta3rd 
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
    	Rate k1 = (Rate) f.call(t, y);								// k1 = h * f(t0,w0);
    	Rate k2 = (Rate) f.call(t+h/3, y.addMul(h/3,k1));			// k2 = h * f((t0 + (h/3)) , (w0 + (k1/3))); 
    	Rate k3 = (Rate) f.call((t+2*h/3), y.addMul(2/3*h, k2));	// k3 = h * f((t0 + (2*h/3)) , (w0 + (2*k2/3)));  
    	
    	Rate dydt = k3.scale(3);									// w1 = w0 + 1/4 * ( k1 + (3*k3));
     	dydt = k1.add(dydt);								 		// This is abbreviated to w0 + h * dydt 
    	dydt = dydt.scale(1/4);  	    					
    	return (State) y.addMul(h,dydt);
    }
}
