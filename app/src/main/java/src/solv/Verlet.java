package src.solv;

import src.peng.Vector3d;
import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

import java.util.ArrayList;
import java.util.Vector;

public class Verlet extends ODESolver implements ODESolverInterface
{
    /**
     * Update rule for one step using the Verlet method
     * @param   function   the function defining the differential equation dy/dt=f(t,y)
     * @param   time   the time
     * @param   currentState   the state
     * @param   step   the step size
     * @return  the new state after taking one step
     */
    public State step(ODEFunctionInterface function, double time, State currentState, double step)
    {
        Rate change = (Rate) function.call(time, currentState);

        ArrayList<Vector3d> nextPosition = calculateNextPositions(currentState, change, step);

        State estimatedNextState = estimateNextState(currentState, change, step, nextPosition);
        Rate nextChange = calculateNextRate(function, estimatedNextState, time, step);

        ArrayList<Vector3d> nextVelocity = calculateNextVelocity(nextChange, currentState, change, step);

        return new State(nextVelocity, nextPosition);
    }

    public StateInterface step(ODEFunctionInterface function, double time, StateInterface cState, double step)
    {
        State currentState = (State)cState;
        Rate change = (Rate) function.call(time, currentState);

        ArrayList<Vector3d> nextPosition = calculateNextPositions(currentState, change, step);

        State estimatedNextState = estimateNextState(currentState, change, step, nextPosition);
        Rate nextChange = calculateNextRate(function, estimatedNextState, time, step);

        ArrayList<Vector3d> nextVelocity = calculateNextVelocity(nextChange, currentState, change, step);

        return new State(nextVelocity, nextPosition);
    }

    /**
     * Method calculated next positions for all celestial bodies
     * @param currentState Current state of universe
     * @param change Current rate of the universe
     * @param step time step
     * @return Next positions of all celestial bodies in the universe
     */
    public ArrayList<Vector3d> calculateNextPositions(State currentState, Rate change, double step)
    {
        ArrayList<Vector3d> nextPosition = new ArrayList<Vector3d>();
        for(int i = 0; i < currentState.velocity.size(); i++)
        {
            Vector3d individualNextPos = verletPosition(currentState.position.get(i),
                                                        currentState.velocity.get(i),
                                                        change.velocityChange.get(i),
                                                        step);
            nextPosition.add(individualNextPos);
        }
        return nextPosition;
    }

    /**
     * Method to calculate the acceleration at th next step in time.
     * @param function the function defining the differential equation dy/dt=f(t,y)
     * @param estimatedNextState Estimated next state of the universe
     * @param time the time
     * @param step the step size
     * @return The rate at the next point in time from the current state
     */
    public Rate calculateNextRate(ODEFunctionInterface function, State estimatedNextState, double time, double step)
    {
        return (Rate)function.call(time + step, estimatedNextState);
    }

    /**
     * Method to calculate the subsequent velocity of celestial bodies
     * @param nextChange Rate of the universe at the next point in time from current.
     * @param currentState The current state of the universe
     * @param change The current rate of the universe
     * @param step The time step
     * @return The next velocities of all celestial bodies in the universe.
     */
    public ArrayList<Vector3d> calculateNextVelocity(Rate nextChange, State currentState, Rate change, double step)
    {
        ArrayList<Vector3d> nextVelocity = new ArrayList<Vector3d>();
        for(int i = 0; i < currentState.velocity.size(); i++)
        {
            Vector3d individualNextVelocity = verletVelocity(currentState.velocity.get(i),
                                                    change.velocityChange.get(i),
                                                    nextChange.velocityChange.get(i),
                                                    step);
            nextVelocity.add(individualNextVelocity);
        }
        return nextVelocity;
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
        ArrayList<Vector3d> estimatedVelocityNextStep = new ArrayList<Vector3d>();
        for(int i = 0; i < currentState.velocity.size(); i++)
        {
            Vector3d currentVelocity = currentState.velocity.get(i);
            Vector3d currentAcceleration = change.velocityChange.get(i);
            estimatedVelocityNextStep.add(currentVelocity.addMul(step, currentAcceleration));
        }
        return new State(estimatedVelocityNextStep, nextPositions);
    }

    /**
     * Verlet method to calculate next position.
     * @param position Current position of celestial body
     * @param velocity Current velocity of celestial body
     * @param acceleration Current acceleration of celestial body
     * @param step The step size to the next point in time.
     * @return Subsequent position of the celestial body.
     * Equation: x(t+dt) = x(t)+v(t)+0.5*dt*dt*a(t)
     */
    public Vector3d verletPosition(Vector3d position, Vector3d velocity, Vector3d acceleration, double step)
    {
        Vector3d p1= position.add(velocity.mul(step));                                                                  //Calculate x(t)+v(t)*dt
        return p1.add(acceleration.mul((0.5 * step *step)));                                                            //Calculate 0.5*dt*dt*a(t)
    }

    /**
     * Verlet method to calculate the next velocity
     * @param velocity The current velocity of the celestial body
     * @param currentAcceleration The current acceleration of the celestial body
     * @param nextAcceleration The acceleration of the celestial body at (t+step)
     * @param step The step size to the next point in time
     * @return Subsequent velocity of the celestial body
     * Equation: v(t)+0.5*(a(t)+a(t+dt))*dt
     */
    public Vector3d verletVelocity(Vector3d velocity, Vector3d currentAcceleration, Vector3d nextAcceleration, double step)
    {
        Vector3d accSum = currentAcceleration.add(nextAcceleration);                                                    //Calculate a(t)+a(t+dt)
        return velocity.add(accSum.mul(0.5*step));                                                                      //Calculate v(t)+0.5*dt*accSum
    }
}
