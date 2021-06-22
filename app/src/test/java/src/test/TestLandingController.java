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
		double landerMass = 6e5;
		Vector3d titanPos = new Vector3d(0,0,0);
		Vector3d titanVel = new Vector3d(0,0,0);
		double titanMass = 1.34553e23;
		double titanRadius = 2575.5e3;
		
		lc.plotTrajectory(landerPos, landerVel, landerMass, titanPos, titanVel, titanMass, titanRadius);
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
		Vector3d position = new Vector3d(); 
		Vector3d drag = controller.calculateDrag(velocity,position, 1, 1);			//placeholder radius value
		Vector3d expectedDrag = new Vector3d(0.017,0.0352,-0);
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
		Vector3d position = new Vector3d();
		Vector3d drag = controller.calculateDrag(velocity,position, 1, 1);
		Vector3d expectedDrag = new Vector3d(-0.017,-0.035,0);
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
		Vector3d position = new Vector3d();
		Vector3d drag = controller.calculateDrag(velocity,position, 1, 1);
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

	@Test void testPressureAt() {
		LandingController control = new LandingController();
		Vector3d position = new Vector3d(0,2e25,0);
		System.out.println(control.airPressureAt(position));
	}

	
	/*
	 * PARTITIONS:
	 * 	Out of the atmosphere's range
	 * 	In the atmoshpere's range
	 * 	Touching the radius
	 * 	On/inside the planet 
	 * 	Just above halfway
	 * 	Just below halfway
	 */
	@Test void testAirPresScalingAboveMax() {
		LandingController control = new LandingController();
		Vector3d position = new Vector3d(0,2e25,0);
		System.out.println(control.airPressureScaling(position,1000));
	}
	
	@Test void testAirPresScalingZero() {
		LandingController control = new LandingController();
		Vector3d position = new Vector3d(0,10,0);
		System.out.println(control.airPressureScaling(position,10));
	}
	
	@Test void testAirPresScalingBelowZero() {
		LandingController control = new LandingController();
		Vector3d position = new Vector3d(0,9,0);
		System.out.println(control.airPressureScaling(position,10));
	}
	
	@Test void testAirPresScalingHalfway() {								
		LandingController control = new LandingController();
		Vector3d position = new Vector3d(0,500000,0);
		System.out.println(control.airPressureScaling(position,200000));
	}

}
