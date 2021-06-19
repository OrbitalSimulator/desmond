package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.land.LandingController;
import src.peng.Vector3d;

class TestLandingController 
{

	@Test
	void logFallFromOrbit() 
	{
		LandingController lc = new LandingController();
		
		Vector3d landerPos = new Vector3d(1E7,0,100);
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
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact2()
	{
		Vector3d v = new Vector3d(-20, 0.0, 0.0);
		double r = 20.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact3()
	{
		Vector3d v = new Vector3d(60.0, 0.0, 0.0);
		double r = 45.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact4()
	{
		Vector3d v = new Vector3d(-40.0, 0.0, 0.0);
		double r = 30.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact5()
	{
		Vector3d v = new Vector3d(0.0, 60, 0.0);
		double r = 60.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact6()
	{
		Vector3d v = new Vector3d(0.0, -20.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	
	@Test void testImpact7()
	{
		Vector3d v = new Vector3d(0.0, 35.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact8()
	{
		Vector3d v = new Vector3d(0.0, -40.0, 0.0);
		double r = 30.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact9()
	{
		Vector3d v = new Vector3d(50.0, -30.0, 0.0);
		double r = 58.30951895;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact10()
	{
		Vector3d v = new Vector3d(15.0, 20.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact11()
	{
		Vector3d v = new Vector3d(30.0, -15.0, 0.0);
		double r = 30.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact12()
	{
		Vector3d v = new Vector3d(31.0, 46.0, 0.0);
		double r = 40.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact13()
	{
		Vector3d v = new Vector3d(-20.0, -15.0, 0.0);
		double r = 25.0;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact14()
	{
		Vector3d v = new Vector3d(-30.0, 20.0, 0.0);
		double r = 37;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), true);
	}
	@Test void testImpact15()
	{
		Vector3d v = new Vector3d(-40.0, -30.0, 0.0);
		double r = 30;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	@Test void testImpact16()
	{
		Vector3d v = new Vector3d(-20.0, 60.0, 0.0);
		double r = 60;
		LandingController lc = new LandingController();
		assertEquals(lc.impact(v, new Vector3d(0,0,0), r), false);
	}
	
	/**
	 * Tests drag for positive velocity
	 * Expected to return a positive drag
	 * Values used for calculations:
	 *  - area = 1.91
	 *  - drag coefficient = 2.1
	 *  - density = 5.428
	 */
	@Test void testDragPositiveV()
	{
		LandingController controller = new LandingController();
		Vector3d velocity = new Vector3d(5,10,0);
		Vector3d drag = controller.calculateDrag(velocity);
		Vector3d expectedDrag = new Vector3d(-608.5377,-1217.0754,-0);
		assertEquals(expectedDrag.getX(),drag.getX(),0.01);
		assertEquals(expectedDrag.getY(),drag.getY(),0.01);
		assertEquals(expectedDrag.getZ(),drag.getZ(),0.01);
	}

	/**
	 * Tests drag for negative velocity
	 * Expected to return a positive drag
	 * Values used for calculations:
	 *  - area = 1.91
	 *  - drag coefficient = 2.1
	 *  - density = 5.428
	 */
	@Test void testDragNegativeV()
	{
		LandingController controller = new LandingController();
		Vector3d velocity = new Vector3d(-5,-10,0);
		Vector3d drag = controller.calculateDrag(velocity);
		Vector3d expectedDrag = new Vector3d(608.5377,1217.0754,0);
		System.out.println(drag.getX());
		System.out.println(drag.getY());
		assertEquals(expectedDrag.getX(),drag.getX(),0.01);
		assertEquals(expectedDrag.getY(),drag.getY(),0.01);
		assertEquals(expectedDrag.getZ(),drag.getZ(),0.01);
	}
	
	/**
	 * Tests drag for zero velocity
	 * Expected to return a positive drag
	 * Values used for calculations:
	 *  - area = 1.91
	 *  - drag coefficient = 2.1
	 *  - density = 5.428
	 */
	@Test void testDragZeroV()
	{
		LandingController controller = new LandingController();
		Vector3d velocity = new Vector3d(0,0,0);
		Vector3d drag = controller.calculateDrag(velocity);
		Vector3d expectedDrag = new Vector3d(0,0,0);
		assertEquals(expectedDrag.getX(),drag.getX());
		assertEquals(expectedDrag.getY(),drag.getY());
		assertEquals(expectedDrag.getZ(),drag.getZ());
	}
	
	
	@Test void testNormaliseOneNegativeOnePosiive()
	{
		LandingController control = new LandingController();
		assertEquals(control.normalise(new Vector3d(4,4,4),new Vector3d(-1,-2,-3)),new Vector3d(5,6,7));
	}
	
	@Test void testRemoveZDimension()
	{
		LandingController control = new LandingController();
		assertEquals(control.removeZDimension(new Vector3d(4,4,4)), new Vector3d(4,4,0));

	}

	@Test void testNormaliseTwoNegative()
	{
		LandingController control = new LandingController();
		assertEquals(control.normalise(new Vector3d(-40,-91,-43),new Vector3d(-100,-40,-33)),new Vector3d(60,-51,-10));
	}

	@Test void testNormaliseTwoPositive()
	{
		LandingController control = new LandingController();
		assertEquals(control.normalise(new Vector3d(100,94,45),new Vector3d(213,432,113)),new Vector3d(-113,-338,-68));
	}

}
