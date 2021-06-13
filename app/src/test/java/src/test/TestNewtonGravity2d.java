package src.test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.land.LanderSettings;
import src.land.LandingModule;
import src.peng.NewtonGravity2d;
import src.peng.Rate2d;
import src.peng.RateInterface;
import src.peng.State2d;
import src.peng.StateInterface;
import src.peng.Vector2d;

public class TestNewtonGravity2d {

	@Test 
	void testNewtonGravityFunc2d() {
		
		ArrayList<Vector2d> arrVelo = new ArrayList<Vector2d>();
		ArrayList<Vector2d> arrPos = new ArrayList<Vector2d>();

		Vector2d initialVelo = new Vector2d(0,0);
		Vector2d initialPos = new Vector2d(20,0);
		arrVelo.add(initialVelo);
		arrPos.add(initialPos);
		
		Vector2d bodyLoc = new Vector2d(0,0);
		LocalDateTime start = LocalDateTime.of(2019, Month.JANUARY, 1, 14, 33, 48);
		LocalDateTime end = LocalDateTime.of(2019, Month.JANUARY, 28, 14, 33, 48);
		String[] way = null;
		
		LandingModule lander = new LandingModule(2,2,20,10,initialPos,initialVelo);
		LanderSettings settings = new LanderSettings(bodyLoc,lander,start,end,100,1,way);
		
		NewtonGravity2d grav = new NewtonGravity2d(settings);
		StateInterface state = new State2d(arrVelo,arrPos);
		
		RateInterface result = grav.call(1, state);
		Rate2d res = (Rate2d) result;
		
		System.out.println(res.toString());

//		assertEquals();
	}
}
