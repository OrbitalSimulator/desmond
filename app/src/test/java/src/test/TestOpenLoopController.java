package src.test;

import org.junit.jupiter.api.Test;

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
		assert(!controller.belowHeight(point));
	}
	
	@Test 
	void testAboveHeightLHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(-50000,0,0);
		assert(!controller.belowHeight(point));
	}
	
	@Test 
	void testAboveHeightCentreTop() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,60000,0);
		assert(!controller.belowHeight(point));
	}
	
	@Test 
	void testAboveHeightCentreBottom() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,-60000,0);
		assert(!controller.belowHeight(point));
	}
	
	@Test 
	void testBelowHeightRHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(5000,0,0);
		assert(controller.belowHeight(point));
	}
	
	@Test 
	void testBelowHeightLHSide() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(-5000,0,0);
		assert(controller.belowHeight(point));
	}
	
	@Test 
	void testBelowHeightCentreTop() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,6000,0);
		assert(controller.belowHeight(point));
	}
	
	@Test 
	void testBelowHeightCentreBottom() {
		
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,-6000,0);
		assert(controller.belowHeight(point));
	}
	
	@Test
	void testHeightsEqual() {
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(0,40000,0);
		assert(!controller.belowHeight(point));
	}
	
	@Test
	void testImpactTrue() {
		OpenLoopController controller = new OpenLoopController();
		Vector3d point = new Vector3d(100,100,0);
		assert(controller.belowHeight(point));
	}
}
