package src.solv;

import java.lang.Math;

import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.StateInterface;

public class EulerSolver extends ODESolver
{
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
        return y.addMul(h, f.call(t, y));
    }
	
	@Override
    public State step(ODEFunctionInterface f, double t, State y, double h)
    {
        return (State) y.addMul(h, f.call(t, y));
    }
}


