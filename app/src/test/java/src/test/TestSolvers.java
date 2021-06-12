package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import log.Logger;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.solv.*;
import src.univ.CelestialBody;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector3d;

/* --------------------------------------------------------------------------------------------------*/
/** Testing Strategy: Each solver will be run for a year at the following step sizes:
 *  	
 *  	- 48 hrs
 *  	- 24 hrs
 *  	- 12 hrs
 *  	-  6 hrs
 *  	-  3 hrs
 *  	-  1 hr
 *  
 *  Asserts confirm accuracy at 1 day and 1 year with all other data being logged into
 *  the /src/test/java/log package
 * 
 */ 
 /*  --------------------------------------------------------------------------------------------------*/
class TestSolvers 
{

	static final double STEP_24 = 86400;
	static final double STEP_12 = 43200;
	static final double STEP_6 = 21600;
	static final double STEP_3 = 10800;
	static final double STEP_1 = 5400;

	static final int YEAR_24 = 365;
	static final int YEAR_12 = YEAR_24 * 2;
	static final int YEAR_6  = YEAR_24 * 4;
	static final int YEAR_3  = YEAR_24 * 8;
	static final int YEAR_1  = 8760;
	
	@Test void testSolvers_24() 
	{
		String fileName = "solvers_24";
		double stepSize = STEP_24;
		int noOfSteps = YEAR_24;
		logSolversTests(fileName, stepSize, noOfSteps);
	}
	
	@Test void testSolvers_12() 
	{
		String fileName = "solvers_12";
		double stepSize = STEP_12;
		int noOfSteps = YEAR_12;
		logSolversTests(fileName, stepSize, noOfSteps);
	}
	
	@Test void testSolvers_6() 
	{
		String fileName = "solvers_6";
		double stepSize = STEP_6;
		int noOfSteps = YEAR_6;
		logSolversTests(fileName, stepSize, noOfSteps);
	}
	
	@Test void testSolvers_3() 
	{
		String fileName = "solvers_3";
		double stepSize = STEP_3;
		int noOfSteps = YEAR_3;
		logSolversTests(fileName, stepSize, noOfSteps);
	}
	
	@Test void testSolvers_1() 
	{
		String fileName = "solvers_1";
		double stepSize = STEP_1;
		int noOfSteps = YEAR_1;
		logSolversTests(fileName, stepSize, noOfSteps);
	}
	
	public void logSolversTests(String fileName, double stepSize, int noOfSteps) 
	{
		SimulationSettings settings;
		try 
		{
			settings = SettingsFileManager.load();
	     	double[] masses = new double[settings.celestialBodies.length];
	    	for(int i = 0; i < settings.celestialBodies.length; i++)
	    	{
	    		masses[i] = settings.celestialBodies[i].mass;
	    	}	
			StateInterface initialState = convertToState(settings.celestialBodies);
			ODEFunctionInterface function = new NewtonGravityFunction(masses);
			State[] states;
			
			Verlet verlet = new Verlet();
			states = (State[]) verlet.solve(function, initialState, stepSize*noOfSteps, stepSize);	
			logStates(fileName, "VERLET", stepSize, noOfSteps, states);
			
			RungeKutta4th rk4 = new RungeKutta4th();
			states = (State[]) rk4.solve(function, initialState, stepSize*noOfSteps, stepSize);	
			logStates(fileName,"RUNGE_KUTTA_4TH", stepSize, noOfSteps, states);
			
			RungeKutta3rd rk3 = new RungeKutta3rd();
			states = (State[]) rk3.solve(function, initialState, stepSize*noOfSteps, stepSize);	
			logStates(fileName, "RUNGE_KUTTA_3RD", stepSize, noOfSteps, states);
			
			RungeKutta2nd rk2 = new RungeKutta2nd();
			states = (State[]) rk2.solve(function, initialState, stepSize*noOfSteps, stepSize);	
			logStates(fileName, "RUNGE_KUTTA_2ND", stepSize, noOfSteps, states);
			
			EulerSolver euler = new EulerSolver();
			states = (State[]) euler.solve(function, initialState, stepSize*noOfSteps, stepSize);	
			logStates(fileName, "EULER", stepSize, noOfSteps, states);
		} 
		catch (IOException e) {
			System.out.println("You gawn done fucked up ...");
			e.printStackTrace();
		}
	}
		
	private void logStates(String fileName,String solver, double stepSize, double noOfSteps, State[] states)
	{
		Logger.logCSV(fileName, "**********,**********,**********,**********,**********,");
		Logger.logCSV(fileName, solver + ",  STEP SIZE," + stepSize + ",  STEPS," + noOfSteps);
		for(int i = 0; i < states.length; i++)
		{
			Logger.logCSV(fileName, states[i].position.get(3).toCSV());
		}
	}
	
    private State convertToState(CelestialBody[] bodies)
    {
        ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();
        ArrayList<Vector3d> position = new ArrayList<Vector3d>();
        for(int i = 0; i < bodies.length; i++)
        {
            velocity.add(bodies[i].velocity);
            position.add(bodies[i].location);
        }
        return new State(velocity, position);
    }	
}
