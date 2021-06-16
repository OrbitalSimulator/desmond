package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

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
import src.univ.CelestialBody;

public class TestNewtonGravity2d {

//	public static void main(String[] args) {
	@Test 
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
		LanderSettings settings = new LanderSettings(bodyLoc,lander,start,end,1,1,way);
		
		ODEFunctionInterface grav = new NewtonGravity2d(settings);
		
		RateInterface result = grav.call(1, state);
		Rate2d res = (Rate2d) result;
		
		System.out.println(res.toString());

	    assertEquals(new Vector2d(-0.164999999998,0).getX(), res.velocityChange.get(0).getX(),0.0000001);
	    assertEquals(new Vector2d(-0.164999999998,0).getX(), res.positionChange.get(0).getX(),0.0000001);

	}
}
