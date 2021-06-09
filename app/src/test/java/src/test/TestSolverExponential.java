package src.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import log.Logger;
import src.peng.ExponentialFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.Vector3d;
import src.solv.EulerSolver;
import src.solv.ODESolver;
import src.solv.RungeKutta2nd;
import src.solv.RungeKutta3rd;
import src.solv.RungeKutta4th;
import src.solv.Verlet;

import java.util.ArrayList;



public class TestSolverExponential
{
    private double epsilon = 0.4;
    private double step = 0.1;
    
    private int numberOfSteps = 100;
    
    private static final boolean DEBUG = false;

    /*Test utilizes the y-value within the velocity field of a state. To represent the exponential function.
     *The position field can be ignored as within the solver this field is manipulated with by the velocity field
     *in order to determine the next position.
     */
    @Test
    void testSolverExponentialSingleStep()
    {
        /*Solver and function setup*/
        ODEFunctionInterface exponentialFunction = new ExponentialFunction();
        ODESolver solver = new Verlet();

        State currentState = createStartState();

        /*Solver test with 0.2 sec time step*/
        double time = 0;
        State nextState = solver.step(exponentialFunction, time, currentState, step);
        if(DEBUG)
        {
            System.out.println("Start State" + currentState.toString());
            System.out.println("Next State" + nextState.toString());
            System.out.println("Actual value: "+ Math.exp(time + step));
        }

        /*Test: Exponential function at time 0.1*/
        assertTrue(withinRange(nextState.velocity.get(0).getY(), Math.exp(time + step)));
    }
    
    @Test
    void testEulerExponential()
    {
    	testSolver(new EulerSolver(), "exp_euler", numberOfSteps);
    }
    
    @Test
    void testRungeKutta2Exponential()
    {
    	testSolver(new RungeKutta2nd(), "exp_RK2", numberOfSteps);
    }
    
    @Test
    void testRungeKutta3Exponential()
    {
    	testSolver(new RungeKutta3rd(), "exp_RK3", numberOfSteps);
    }
    
    @Test
    void testRungeKutta4Exponential()
    {
    	testSolver(new RungeKutta4th(), "exp_RK4", numberOfSteps);
    }
    
    @Test
    void testVerletExponential()
    {
    	testSolver(new Verlet(), "exp_verlet", numberOfSteps);
    }
    

    // -------------------------------------------------------------------------------------
    
    private void testSolver(ODESolver solver, String fileName, int numberOfSteps)
    {
        /*Solver and function setup*/
        ODEFunctionInterface exponentialFunction = new ExponentialFunction();
        
        State currentState = createStartState();

        double time = 0;
        while(time < numberOfSteps)
        {
        	 Logger.logCSV(fileName, currentState.toCSV());
        	
        	State nextState = solver.step(exponentialFunction, time, currentState, step);
            if(DEBUG)
            {
                System.out.println("Start State" + currentState.toString());
                System.out.println("Next State" + nextState.toString());
                System.out.println("Actual value: "+ Math.exp(time + step));
            }
           
            //assertTrue(withinRange(nextState.velocity.get(0).getY(), Math.exp(time + step)));

            currentState = nextState;
            time += step;
        }
    }

    private boolean withinRange(double estimatedValue, double actualValue)
    {
        if(Math.abs(estimatedValue - actualValue) < epsilon)
        {
            return true;
        }
        return false;
    }

    //Generate an initial state at time 0.
    //This included velocity field y-value holding value of e^(0)
    private State createStartState()
    {
        ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();                                                       //Current state velocity
        Vector3d startValue = new Vector3d(0, Math.exp(0),0);
        velocity.add(startValue);

        ArrayList<Vector3d> position = new ArrayList<Vector3d>();                                                       //Current state position
        position.add(new Vector3d());

        return new State(velocity, position, 0);                                                                   //Generate state at time 0.
    }

}
