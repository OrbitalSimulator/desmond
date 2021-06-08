package src.solv;

import src.peng.Vector3d;
import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

import java.util.ArrayList;

public class Verlet extends ODESolver implements ODESolverInterface
{
    /**
     * Update rule for one step using the Verlet method
     * @param   function   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   currentState   the state
     * @param   step   the step size
     * @return  the new state after taking one step
     */
    public State step(ODEFunctionInterface function, double t, State currentState, double step)
    {
        Rate change = (Rate) function.call(t,currentState);

        ArrayList<Vector3d> nextPos = nextPositions(currentState, change, step);

        State estNextState = estimateNextState(currentState, change, step, nextPos);
        Rate nextChange = nextRate(function, estNextState, t, step);

        ArrayList<Vector3d> nextVel = nextVelocity(nextChange, currentState, change, step);

        return new State(nextVel, nextPos, t+step);
    }

    public StateInterface step(ODEFunctionInterface function, double t, StateInterface cState, double step)
    {
        State currentState = (State)cState;
        Rate change = (Rate) function.call(t,currentState);

        ArrayList<Vector3d> nextPos = nextPositions(currentState, change, step);

        State estNextState = estimateNextState(currentState, change, step, nextPos);
        Rate nextChange = nextRate(function, estNextState, t, step);

        ArrayList<Vector3d> nextVel = nextVelocity(nextChange, currentState, change, step);

        return new State(nextVel, nextPos);
    }

    /**
     * Method calculated next positions for all celestial bodies
     * @param currentState Current state of universe
     * @param change Current rate of the universe
     * @param h time step
     * @return Next positions of all celestial bodies in the universe
     */
    public ArrayList<Vector3d> nextPositions(State currentState, Rate change, double step)
    {
        ArrayList<Vector3d> nextPos = new ArrayList<Vector3d>();
        for(int i = 0; i < currentState.velocity.size(); i++)
        {
            Vector3d individualNextPos = verletPosition(currentState.position.get(i),
                                                        currentState.velocity.get(i),
                                                        change.velocityChange.get(i),
                                                        step);
            nextPos.add(individualNextPos);
        }
        return nextPos;
    }

    /**
     * Method to calculate the acceleration at th next step in time.
     * @param function the function defining the differential equation dy/dt=f(t,y)
     * @param estNExtState Estimated next state of the universe
     * @param time the time
     * @param step the step size
     * @return The rate at the next point in time from the current state
     */
    public Rate nextRate(ODEFunctionInterface function, State estNextState, double t, double step)
    {
        return (Rate)function.call(t + step, estNextState);
    }

    /**
     * Method to calculate the subsequent velocity of celestial bodies
     * @param nextChange Rate of the universe at the next point in time from current.
     * @param currentState The current state of the universe
     * @param change The current rate of the universe
     * @param step The time step
     * @return The next velocities of all celestial bodies in the universe.
     */
    public ArrayList<Vector3d> nextVelocity(Rate nextChange, State currentState, Rate change, double step)
    {
        ArrayList<Vector3d> nextVel = new ArrayList<Vector3d>();
        for(int i = 0; i < currentState.velocity.size(); i++)
        {
            Vector3d individualNextVelocity = verletVelocity(currentState.velocity.get(i),
                                                    change.velocityChange.get(i),
                                                    nextChange.velocityChange.get(i),
                                                    step);
            nextVel.add(individualNextVelocity);
        }
        return nextVel;
    }

    //Acceptable or make clearer?
    /**
     * Method to estimate the next state of the universe from current
     * @param currentState The current state of the universe
     * @param change The current rate of the universe
     * @param step The time step
     * @param nextPositions The positions of celestial bodies at the next point in time from current
     * @return The estimated next state of the universe from current.
     */
    public State estimateNextState(State currentState, Rate change, double step, ArrayList<Vector3d> nextPositions)
    {
        ArrayList<Vector3d> estVelocityNextStep = new ArrayList<Vector3d>();
        for(int i = 0; i < currentState.velocity.size(); i++)
        {
            Vector3d currentVelocity = currentState.velocity.get(i);
            Vector3d currentAcc = change.velocityChange.get(i);
            estVelocityNextStep.add(currentVelocity.addMul(step, currentAcc));
        }
        return new State(estVelocityNextStep, nextPositions);
    }

    /**
     * Verlet method to calculate next position.
     * @param pos Current position of celestial body
     * @param vel Current velocity of celestial body
     * @param acc Current acceleration of celestial body
     * @param step The step size to the next point in time.
     * @return Subsequent position of the celestial body.
     * Equation: x(t+dt) = x(t)+v(t)+0.5*dt*dt*a(t)
     */
    public Vector3d verletPosition(Vector3d pos, Vector3d vel, Vector3d acc, double step)
    {
        Vector3d p1= pos.add(vel.mul(step));                                                                              //Calculate x(t)+v(t)*dt
        return p1.add(acc.mul((0.5 * step *step)));                                                                         //Calculate 0.5*dt*dt*a(t)
    }

    /**
     * Verlet method to calculate the next velocity
     * @param vel The current velocity of the celestial body
     * @param currentAcc The current acceleration of the celestial body
     * @param nextAcc The acceleration of the celestial body at (t+step)
     * @param step The step size to the next point in time
     * @return Subsequent velocity of the celestial body
     * Equation: v(t)+0.5*(a(t)+a(t+dt))*dt
     */
    public Vector3d verletVelocity(Vector3d vel, Vector3d currentAcc, Vector3d nextAcc, double step)
    {
        Vector3d accSum = currentAcc.add(nextAcc);                                                                      //Calculate a(t)+a(t+dt)
        return vel.add(accSum.mul(0.5*step));                                                                             //Calculate v(t)+0.5*dt*accSum
    }
}
