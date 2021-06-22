package src.test;

import org.junit.jupiter.api.Test;

import src.land.OpenLoopController;
import src.peng.Vector3d;

public class TestOpenLoopController
{

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
