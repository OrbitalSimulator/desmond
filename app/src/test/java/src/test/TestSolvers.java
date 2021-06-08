package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import log.TestLogger;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.solv.*;
import src.univ.CelestialBody;
import src.univ.Universe;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.ExponentialFunction;
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
		TestLogger.log(fileName, "**********,**********,**********,**********,**********,");
		TestLogger.log(fileName, solver + ",  STEP SIZE," + stepSize + ",  STEPS," + noOfSteps);
		for(int i = 0; i < states.length; i++)
		{
			TestLogger.log(fileName, states[i].position.get(3).toCSV());
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
    
    // --------------------------  ACCURACY TESTING -------------------------- //
    
	static final boolean SAVE_TO_FILE = false;
	
	static final double STEP_DAY = 86400;
	static final double STEP_HOUR = 3600;
	static final double STEP_MIN = 60;
	static final double STEP_SEC = 1;
	
	static final double EARTH_ONE_DAY_X = -1.467016284491896E11;
	static final double EARTH_ONE_DAY_Y = -3.113774419524897E10;
	static final double EARTH_ONE_DAY_Z =  8.336289095385000E06;
	
	static final double EARTH_ONE_YEAR_X = -1.477128501379751E11;
	static final double EARTH_ONE_YEAR_Y = -2.821059740389257E10;
	static final double EARTH_ONE_YEAR_Z =  2.031783034570143E07;
	
    static final double DAY_ACCURACY = 974; 
    static final double YEAR_ACCURACY = 5.733198703E09; 
    
	
	@Test void testOneDay() 
	{	
		State finalState = setupOneDayTest();
		double x = finalState.position.get(3).getX();
		double y = finalState.position.get(3).getY();
		double z = finalState.position.get(3).getZ();
		assertEquals(EARTH_ONE_DAY_X, x, DAY_ACCURACY);
		assertEquals(EARTH_ONE_DAY_Y, y, DAY_ACCURACY);
		assertEquals(EARTH_ONE_DAY_Z, z, DAY_ACCURACY);
	}
	
	@Test void testOneYear() 
	{	
		State finalState = setupOneYearTest();
		double x = finalState.position.get(3).getX();
		double y = finalState.position.get(3).getY();
		double z = finalState.position.get(3).getZ();
		assertEquals(EARTH_ONE_YEAR_X, x, YEAR_ACCURACY);
		assertEquals(EARTH_ONE_YEAR_Y, y, YEAR_ACCURACY);
		assertEquals(EARTH_ONE_YEAR_Z, z, YEAR_ACCURACY);
	}
		
	private State setupOneDayTest()
	{
		int dayInSeconds = 60*60*24;
		SimulationSettings settings = generateSettings(STEP_SEC, dayInSeconds);
		Universe universe = new Universe(settings, SAVE_TO_FILE);
		return universe.getStateAt(dayInSeconds);
	}
	
	private State setupOneYearTest()
	{
		int yearInHours = 365*24;
		SimulationSettings settings = generateSettings(STEP_HOUR, yearInHours);
		Universe universe = new Universe(settings, SAVE_TO_FILE);
		return universe.getStateAt(yearInHours);
	}
	
	private SimulationSettings generateSettings(double stepSize, int numberOfSteps)
	{
		try 
		{
			SimulationSettings settings = SettingsFileManager.load();
			settings.noOfSteps = numberOfSteps;
			settings.stepSize = stepSize;
			return settings;
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		return null;
	}
}
