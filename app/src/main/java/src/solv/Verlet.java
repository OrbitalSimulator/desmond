package src.solv;

import src.peng.Vector3d;
import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;

import java.util.ArrayList;

public class Verlet
{
    /**
     * Verlet method to calculate next position.
     * @param pos Current position of celestial body
     * @param vel Current velocity of celestial body
     * @param acc Current acceleration of celestial body
     * @param dt The step size to the next point in time.
     * @return Subsequent position of the celestial body.
     * Equation: x(t+dt) = x(t)+v(t)+0.5*dt*dt*a(t)
     */
    public Vector3d verletPosition(Vector3d pos, Vector3d vel, Vector3d acc, double dt)
    {
        Vector3d p1= pos.add(vel.mul(dt));                                                                              //Calculate x(t)+v(t)*dt
        return p1.add(acc.mul((0.5 * dt *dt)));                                                                         //Calculate 0.5*dt*dt*a(t)
    }

    /**
     * Verlet method to calculate the next velocity
     * @param vel The current velocity of the celestial body
     * @param currentAcc The current acceleration of the celestial body
     * @param nextAcc The acceleration of the celestial body at (t+step)
     * @param dt The step size to the next point in time
     * @return Subsequent velocity of the celestial body
     * Equation: v(t)+0.5*(a(t)+a(t+dt))*dt
     */
    public Vector3d verletVelocity(Vector3d vel, Vector3d currentAcc, Vector3d nextAcc, double dt)
    {
        Vector3d accSum = currentAcc.add(nextAcc);                                                                      //Calculate a(t)+a(t+dt)
        return vel.add(accSum.mul(0.5*dt));                                                                             //Calculate v(t)+0.5*dt*accSum
    }

    /**
     * Update rule for one step using the Verlet method
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    public State step(ODEFunctionInterface f, double t, State y, double h)
    {
        /*Calculate essential values*/
        Rate change = (Rate) f.call(t,y);                                                                               //Determine current state rate
        int noCelestialBodies = y.velocity.size();                                                                      //calculate number of celestial bodies in universe

        /*Calculate next position*/
        ArrayList<Vector3d> nextPos = new ArrayList<Vector3d>(noCelestialBodies);
        for(int i=0; i< noCelestialBodies; i++)
        {
            nextPos.add(verletPosition(y.position.get(i), y.velocity.get(i), change.velocityChange.get(i), h));         //Calculate updated positions
        }

        /*Derive a(t+h)*/
        ArrayList<Vector3d> velTemp = new ArrayList<Vector3d>(noCelestialBodies);
        for(int i=0; i< noCelestialBodies; i++)                                                                         //Populate the interim velocities
        {
            velTemp.add(y.velocity.get(i).addMul(h, change.velocityChange.get(i)));                                     //Calculate next Velocity based on current, and the acceleration
        }
        State nextState = new State(velTemp, nextPos);                                                                  //Create interim state for a(t+dt) calculation
        Rate nextChange = (Rate) f.call(t+h, nextState);                                                                //Calculate rate at interim state

        /*Calculate next velocity*/
        ArrayList<Vector3d> nextVel = new ArrayList<Vector3d>();                                                        //Determine acc(t+dt) for ech celestial body
        for(int i=0; i< noCelestialBodies; i++)
        {
            Vector3d nextAcc = nextChange.velocityChange.get(i);                                                        //Get nextAcc value
            nextVel.add(verletVelocity(y.velocity.get(i), change.velocityChange.get(i), nextAcc, h));                //Calculate next velocity
        }

        return new State(nextVel, nextPos);                                                                             //Return nextState
    }

    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {
        /*Calculate essential values*/
        Rate change = (Rate) f.call(t,y);                                                                               //Determine current state rate
        int noCelestialBodies = (State)y.velocity.size();                                                                      //calculate number of celestial bodies in universe

        /*Calculate next position*/
        ArrayList<Vector3d> nextPos = new ArrayList<Vector3d>(noCelestialBodies);
        for(int i=0; i< noCelestialBodies; i++)
        {
            nextPos.add(verletPosition(y.position.get(i), y.velocity.get(i), change.velocityChange.get(i), h));         //Calculate updated positions
        }

        /*Derive a(t+h)*/
        ArrayList<Vector3d> velTemp = new ArrayList<Vector3d>(noCelestialBodies);
        for(int i=0; i< noCelestialBodies; i++)                                                                         //Populate the interim velocities
        {
            velTemp.add(y.velocity.get(i).addMul(h, change.velocityChange.get(i)));                                     //Calculate next Velocity based on current, and the acceleration
        }
        State nextState = new State(velTemp, nextPos);                                                                  //Create interim state for a(t+dt) calculation
        Rate nextChange = (Rate) f.call(nextState, t+h);                                                                //Calculate rate at interim state

        /*Calculate next velocity*/
        ArrayList<Vector3d> nextVel = new ArrayList<Vector3d>();                                                        //Determine acc(t+dt) for ech celestial body
        for(int i=0; i< noCelestialBodies; i++)
        {
            Vector3d nextAcc = nextChange.velocityChange.get(i);                                                        //Get nextAcc value
            nextVel.add(verletVelocity(y.velocity.get(i), change.velocityChange.get(i), nextAcc, step));                //Calculate next velocity
        }

        return new State(nextVel, nextPos);                                                                             //Return nextState
    }
}
