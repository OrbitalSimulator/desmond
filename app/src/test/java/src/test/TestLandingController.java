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
	
	@Test void testImpact()
	{
		// TODO (Jerome) 4hrs
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
		Vector3d expectedDrag = new Vector3d(272.15,1088.5854,0);
		assertEquals(expectedDrag.getX(),drag.getX());
		assertEquals(expectedDrag.getY(),drag.getY());
		assertEquals(expectedDrag.getZ(),drag.getZ());
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
		Vector3d expectedDrag = new Vector3d(272.15,1088.5854,0);
		assertEquals(expectedDrag.getX(),drag.getX());
		assertEquals(expectedDrag.getY(),drag.getY());
		assertEquals(expectedDrag.getZ(),drag.getZ());
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
