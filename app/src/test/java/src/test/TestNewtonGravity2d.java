package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import log.Logger;
import src.land.LanderSettings;
import src.land.LandingModule;
import src.peng.NewtonGravity2d;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.Rate2d;
import src.peng.RateInterface;
import src.peng.State2d;
import src.peng.StateInterface;
import src.peng.Vector2d;
import src.solv.EulerSolver;
import src.solv.ODESolverInterface;
import src.solv.Verlet;


public class TestNewtonGravity2d {

	private boolean DEBUG = false;
	
	@Test 
	void testNewtonGravityFunc2d() {
		
		ArrayList<Vector2d> arrVelo = new ArrayList<Vector2d>();
		ArrayList<Vector2d> arrPos = new ArrayList<Vector2d>();

		Vector2d initialVelo = new Vector2d(1,0);
		Vector2d initialPos = new Vector2d(20,0);
		arrVelo.add(initialVelo);
		arrPos.add(initialPos);				
		StateInterface state = new State2d(arrVelo,arrPos);

		ODEFunctionInterface grav = new NewtonGravity2d(10,2,2,10);
		
		RateInterface result = grav.call(1, state);
		Rate2d res = (Rate2d) result;
		
		System.out.println(res.toString());
		
		double absolute = (0.164999999998 - res.velocityChange.get(0).getX());
		double relative = absolute/res.velocityChange.get(0).getX();
		System.out.println(absolute + "     " + relative*100 + " %");

	    assertEquals(new Vector2d(0.164999999998,0).getX(), res.velocityChange.get(0).getX(),0.00000001);	//from formula
	    
	}
	
	@Test 
	void testNewtonGravityFunc2dFallingData() {
		
		ArrayList<Vector2d> arrVelo = new ArrayList<Vector2d>();
		ArrayList<Vector2d> arrPos = new ArrayList<Vector2d>();

		Vector2d initialVelo = new Vector2d(0,0);
		Vector2d initialPos = new Vector2d(0,20000);
		arrVelo.add(initialVelo);
		arrPos.add(initialPos);				
		StateInterface state = new State2d(arrVelo,arrPos);

		ODEFunctionInterface grav = new NewtonGravity2d(1.34553e23,30,100,5000);
		
		ODESolverInterface solver = new EulerSolver();			
		double endPoint = 70.4315;						//70.4315 is around the ideal for whole descent
		double time = 0;
		double stepSize = 0.0005;
		Logger.logCSV("exp_fall20", ",Velocity , ,Position, ,");
	    while(time < endPoint)
	        {
	        	Logger.logCSV("exp_fall20", ((State2d) state).toCSV());
	        	
	        	State2d nextState = (State2d) solver.step(grav, time, state, stepSize);
	            if(DEBUG)
	            {
	                System.out.println("Start State" + state.toString());
	                System.out.println("Next State" + nextState.toString());
	            }  
	            state = nextState;
	            time += stepSize;
	        }
	}
	
	@Test 
	void testNewtonGravityFunc2dOnestep() {
		
		StateInterface state = setupState();
		ODEFunctionInterface grav = new NewtonGravity2d(10,2,2,10);
		ODESolverInterface solver = new EulerSolver();	
		
		double time = 0;
	  
	    State2d nextState = (State2d) solver.step(grav, time, state, 1);
	    System.out.println(nextState.toString());
	    
	    assertEquals(0.164999999998, nextState.velocity.get(0).getY(),0.00000001);								//cv will be (1,0.164999999998)  
	    assert(new Vector2d(1,20).equals(nextState.position.get(0)));								//cp is (1,0) since velocity was (1,0)
	}

	private StateInterface setupState() {
		
		ArrayList<Vector2d> arrVelo = new ArrayList<Vector2d>();
		ArrayList<Vector2d> arrPos = new ArrayList<Vector2d>();
		Vector2d initialVelo = new Vector2d(1,0);
		Vector2d initialPos = new Vector2d(0,20);
		arrVelo.add(initialVelo);
		arrPos.add(initialPos);		
		
		return new State2d(arrVelo,arrPos);
	}
}
