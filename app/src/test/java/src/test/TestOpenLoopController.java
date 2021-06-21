package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import src.land.LandingController;
import src.land.OpenLoopController;
import src.peng.Vector3d;

public class TestOpenLoopController {

	/*
	 * REP. INV.:
	 * Height should be > radius
	 * 
	 * PARTITIONS:						Above				  |	Below
	 * Left-hand side of planet			point = -ve,0 > height|	point = -ve,0 < height
	 * Right-hand side of planet		point = +ve,0 > height|	point = +ve,0 < height
	 * Centre above planet				point = 0,+ve > height| point = 0,+ve < height
	 * Centre below planet				point = 0,-ve > height|	point = 0,-ve < height
	 * 
	 * Special cases:
	 * Height of point == height, --> false
	 * Height of point, impact() is true, --> true
	 */
	
	@Test 
	void testAboveHeightRHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(50000,0,0);
		assertEquals(!controller.belowHeight(point),true);
	}
	
	@Test 
	void testAboveHeightLHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(-50000,0,0);
		assertEquals(!controller.belowHeight(point),true);
	}
	
	@Test 
	void testAboveHeightCentreTop() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,60000,0);
		assertEquals(!controller.belowHeight(point),true);
	}
	
	@Test 
	void testAboveHeightCentreBottom() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,-60000,0);
		assertEquals(!controller.belowHeight(point),true);
	}
	
	@Test 
	void testBelowHeightRHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(5000,0,0);
		assertEquals(controller.belowHeight(point),true);
	}
	
	@Test 
	void testBelowHeightLHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(-5000,0,0);
		assertEquals(controller.belowHeight(point),true);
	}
	
	@Test 
	void testBelowHeightCentreTop() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,6000,0);
		assertEquals(controller.belowHeight(point),true);
	}
	
	@Test 
	void testBelowHeightCentreBottom() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,-6000,0);
		assertEquals(controller.belowHeight(point),true);
	}
	
	@Test
	void testHeightsEqual() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,40000,0);
		assertEquals(!controller.belowHeight(point),true);
	}
	
	@Test
	void testImpactTrue() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(100,100,0);
		assertEquals(controller.belowHeight(point),true);
	}
	
	@Test
	void logFallFromOrbit() 
	{
		OpenLoopController oc = new OpenLoopController();
		
		Vector3d landerPos = new Vector3d(1E7,0,100);
		Vector3d landerVel = new Vector3d(0,0,0);
		double landerMass = 6e3;
		Vector3d titanPos = new Vector3d(0,0,0);
		Vector3d titanVel = new Vector3d(0,0,0);
		double titanMass = 1.34553e23;
		double titanRadius = 2575.5e3;
		
		oc.plotTrajectory(landerPos, landerVel, landerMass, titanPos, titanVel, titanMass, titanRadius);
	}
}
