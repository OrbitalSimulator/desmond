package src.solv;

import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

public class RungeKutta4th extends ODESolver implements ODESolverInterface
{      
	/**
     * Update rule for one step.
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    public State step(ODEFunctionInterface f, double t, State y, double h)
    {    	
    	Rate k1 = (Rate) f.call(t, y);								// k1 = h * f(t0,w0);
    	Rate k2 = (Rate) f.call((t+(h/2)), y.addMul(1/2,k1));		// k2 = h * f((t0 + (h/2)) , (w0 + (k1/2)));    
    	Rate k3 = (Rate) f.call((t+(h/2)), y.addMul((1/2*h), k2));	// k3 = h * f((t0 + (h/2)) , (w0 + (k2/2))); 
    	Rate k4 = (Rate) f.call((t+h), y.addMul(h, k3));			// k4 = h * f((t0 + h) , (w0 + k3));
    	
    	Rate dydt = k4;
    	dydt = dydt.add(k3.scale(2));								// w1 = w0 + 1/6 * ( k1 + (2*k2) + (2*k3) + k4);
     	dydt = dydt.add(k2).scale(2);								// ^ This is abbreviated to w0 + h * dydt
     	dydt = dydt.add(k1);
    	dydt = dydt.scale(1/6);  	    					
    	return (State) y.addMul(h,dydt);
    }
    
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {    	
    	Rate k1 = (Rate) f.call(t, y);								// k1 = h * f(t0,w0);
    	Rate k2 = (Rate) f.call((t+(h/2)), y.addMul(1/2,k1));		// k2 = h * f((t0 + (h/2)) , (w0 + (k1/2)));    
    	Rate k3 = (Rate) f.call((t+(h/2)), y.addMul((1/2*h), k2));	// k3 = h * f((t0 + (h/2)) , (w0 + (k2/2))); 
    	Rate k4 = (Rate) f.call((t+h), y.addMul(h, k3));			// k4 = h * f((t0 + h) , (w0 + k3));
    	
    	Rate dydt = k4;
    	dydt = dydt.add(k3.scale(2));								// w1 = w0 + 1/6 * ( k1 + (2*k2) + (2 * k3) + k4);
     	dydt = dydt.add(k2).scale(2);								// ^ This is abbreviated to w0 + h * dydt
     	dydt = dydt.add(k1);
    	dydt = dydt.scale(1/6);  	    					
    	return (State) y.addMul(h,dydt);
    }
}
