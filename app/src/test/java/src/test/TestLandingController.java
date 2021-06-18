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
