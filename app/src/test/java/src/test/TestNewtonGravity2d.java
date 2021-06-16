package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import log.Logger;
import src.land.LanderSettings;
import src.land.LandingModule;
import src.peng.NewtonGravity2d;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.Rate;
import src.peng.Rate2d;
import src.peng.RateInterface;
import src.peng.State;
import src.peng.State2d;
import src.peng.StateInterface;
import src.peng.Vector2d;
import src.peng.Vector3d;
import src.solv.EulerSolver;
import src.univ.CelestialBody;

public class TestNewtonGravity2d {

	private boolean DEBUG = true;
	
//	public static void main(String[] args) {
//	@Test 
	void testNewtonGravityFunc2d() {
		
		ArrayList<Vector2d> arrVelo = new ArrayList<Vector2d>();
		ArrayList<Vector2d> arrPos = new ArrayList<Vector2d>();

		Vector2d initialVelo = new Vector2d(1,0);
		Vector2d initialPos = new Vector2d(20,0);
		arrVelo.add(initialVelo);
		arrPos.add(initialPos);				
		StateInterface state = new State2d(arrVelo,arrPos);

		Vector2d bodyLoc = new Vector2d(0,0);
		LocalDateTime start = LocalDateTime.of(2019, Month.JANUARY, 1, 14, 33, 48);
		LocalDateTime end = LocalDateTime.of(2019, Month.JANUARY, 28, 14, 33, 48);		
		String[] way = null;
		
		LandingModule lander = new LandingModule(2,2,20,10,initialPos,initialVelo,10);
		LanderSettings settings = new LanderSettings(bodyLoc,lander,1,1);
		
		ODEFunctionInterface grav = new NewtonGravity2d(settings);
		
		RateInterface result = grav.call(1, state);
		Rate2d res = (Rate2d) result;
		
		System.out.println(res.toString());
		
		double absolute = (-0.164999999998 - res.velocityChange.get(0).getX());
		double relative = absolute/res.velocityChange.get(0).getX();
		System.out.println(absolute + "     " + relative*100 + " %");

	    assertEquals(new Vector2d(-0.164999999998,0).getX(), res.velocityChange.get(0).getX(),0.000000000001);	//from formula
	    assertEquals(new Vector2d(1,0).getX(), res.positionChange.get(0).getX());								//cp is (1,0) since velocity was (1,0)
	}
	
	@Test 
	void testNewtonGravityFunc2dFalling() {
		
		ArrayList<Vector2d> arrVelo = new ArrayList<Vector2d>();
		ArrayList<Vector2d> arrPos = new ArrayList<Vector2d>();

		Vector2d initialVelo = new Vector2d(1,0);
		Vector2d initialPos = new Vector2d(0,20);
		arrVelo.add(initialVelo);
		arrPos.add(initialPos);				
		StateInterface state = new State2d(arrVelo,arrPos);

		Vector2d bodyLoc = new Vector2d(0,0);
	
		LandingModule lander = new LandingModule(2,2,20,10,initialPos,initialVelo,10);
		LanderSettings settings = new LanderSettings(bodyLoc,lander,1,0.1);
		
		ODEFunctionInterface grav = new NewtonGravity2d(settings);
		
		EulerSolver solver = new EulerSolver();			//switch to verlet
		double numberOfSteps = 25;
		double time = 0;
		Logger.logCSV("exp_fall4", "Position, , ,Velocity, ,");
	    while(time < numberOfSteps)
	        {
	        	Logger.logCSV("exp_fall4", ((State2d) state).toCSV());
	        	
	        	State2d nextState = (State2d) solver.step(grav, time, state, settings.stepSize);
	            if(DEBUG)
	            {
//	                System.out.println("Start State" + state.toString());
	                System.out.println("Next State" + nextState.toString());
//	                System.out.println("Actual value: "+ Math.exp(time + settings.stepSize));
	            }
	           
	            state = nextState;
	            time += settings.stepSize;
	        }
	}
}
