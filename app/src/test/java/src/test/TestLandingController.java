package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.land.LandingController;
import src.peng.Vector3d;

class TestLandingController 
{

	@Test
	void testOnlyLanderFall() 
	{
		LandingController lc = new LandingController();
		
		Vector3d landerPos = new Vector3d(1E5,0,100);
		Vector3d landerVel = new Vector3d(0,0,0);
		double landerMass = 6e3;
		Vector3d titanPos = new Vector3d(0,0,0);
		Vector3d titanVel = new Vector3d(0,0,0);
		double titanMass = 1.34553e23;
		double titanRadius = 2575.5e3;
		
		lc.plotTrajectory(landerPos, landerVel, landerMass, titanPos, titanVel, titanMass, titanRadius);
		
	}
	/**
	 * 	Testing Strategy:
	 *  	|  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
	 *  x   | >0 | <0 | >0 | <0 |  0 |  0 |  0 |  0 | >0 | >0 | >0 | >0 | <0 | <0 | <0 | <0 |
	 *  y   |  0 |  0 |  0 |  0 | >0 | <0 | >0 | <0 | <0 | >0 | <0 | >0 | <0 | >0 | <0 | >0 |
	 * d<=r |  T |  T |  F |  F |  T |  T |  F |  F |  T |  T |  F |  F |  T |  T |  F |  F |
	 */
	// TODO (Jerome) 4hrs
	@Test void testImpact1()
	{
		Vector3d v = new Vector3d(35.0, 0.0, 0.0);
		double r = 50.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact2()
	{
		Vector3d v = new Vector3d(-20, 0.0, 0.0);
		double r = 20.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact3()
	{
		Vector3d v = new Vector3d(60.0, 0.0, 0.0);
		double r = 45.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact4()
	{
		Vector3d v = new Vector3d(-40.0, 0.0, 0.0);
		double r = 30.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact5()
	{
		Vector3d v = new Vector3d(0.0, 60, 0.0);
		double r = 60.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact6()
	{
		Vector3d v = new Vector3d(0.0, -20.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	
	@Test void testImpact7()
	{
		Vector3d v = new Vector3d(0.0, 35.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact8()
	{
		Vector3d v = new Vector3d(0.0, -40.0, 0.0);
		double r = 30.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact9()
	{
		Vector3d v = new Vector3d(50.0, -30.0, 0.0);
		double r = 58.30951895;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact10()
	{
		Vector3d v = new Vector3d(15.0, 20.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact11()
	{
		Vector3d v = new Vector3d(30.0, -15.0, 0.0);
		double r = 30.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact12()
	{
		Vector3d v = new Vector3d(31.0, 46.0, 0.0);
		double r = 40.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact13()
	{
		Vector3d v = new Vector3d(-20.0, -15.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact14()
	{
		Vector3d v = new Vector3d(-30.0, 20.0, 0.0);
		double r = 37;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), true);
	}
	@Test void testImpact15()
	{
		Vector3d v = new Vector3d(-40.0, -30.0, 0.0);
		double r = 30;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	@Test void testImpact16()
	{
		Vector3d v = new Vector3d(-20.0, 60.0, 0.0);
		double r = 60;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, r), false);
	}
	
	@Test void testDrag()
	{
		// TODO (Sam) 2hrs
	}
	
	@Test void testNormalise()
	{
		// TODO (Alp) 1hr
	}
	
	@Test void testRemoveZDimension()
	{
		// TODO (Alp) 30
	}

}
