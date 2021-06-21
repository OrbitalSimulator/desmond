package src.test;

import org.junit.jupiter.api.Test;
import src.land.ClosedLoopController;
import src.peng.Vector3d;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClosedLoopController 
{
    
	@Test
	void logFallFromOrbit() 
	{
		ClosedLoopController c = new ClosedLoopController();
		
		Vector3d landerPos = new Vector3d(1E7,0,100);
		Vector3d landerVel = new Vector3d(0,0,0);
		double landerMass = 6e5;
		Vector3d titanPos = new Vector3d(0,0,0);
		Vector3d titanVel = new Vector3d(0,0,0);
		double titanMass = 1.34553e23;
		double titanRadius = 2575.5e3;
		
		c.plotTrajectory(landerPos, landerVel, landerMass, titanPos, titanVel, titanMass, titanRadius);
	}
	
	
	@Test
    void testgetAngle()
    {
        ClosedLoopController clp= new ClosedLoopController();
        Vector3d a = new Vector3d(1,2,3);
        Vector3d b = new Vector3d(-1, -4, 6);
        Vector3d c = new Vector3d(5 , 6 , 8);
        assertEquals(34.06718675903152,clp.getAngle(a,b,c));
    }
}
