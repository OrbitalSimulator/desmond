package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JFrame;

import src.land.LanderObject;
import src.land.LandingController;
import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.visu.LandingVisualiser;

class TestLandingVisualiser {

	@Test
	void test() {
//		try 
//		{
//			LandingController lc = new LandingController();
//	        
//	        Vector3d landerPos = new Vector3d(1E7,0,0);
//	        Vector3d landerVel = new Vector3d(0,700,0);
//	        double landerMass = 6e3;
//	        Vector3d titanPos = new Vector3d(0,0,0);
//	        Vector3d titanVel = new Vector3d(0,0,0);
//	        double titanMass = 1.34553e23;
//	        double titanRadius = 2575.5e3;
//	        
//	        ArrayList<LanderObject> trajectory = lc.plotTrajectory(landerPos, landerVel, landerMass, titanPos, titanVel, titanMass, titanRadius);
//	        
//			JFrame testWindow = new JFrame();
//			LandingVisualiser landingVisualiser = new LandingVisualiser();
//			testWindow.add(landingVisualiser);
//			testWindow.setSize(new Dimension(900, 600));
//			testWindow.setVisible(true);
//			landingVisualiser.addPlanet(new CelestialBody(new Vector3d(0.0, 700.0, 0.0), new Vector3d(0.0,0.0,0.0), 10.0, 2575500, "floor", "titanScaled.png", "", LocalDateTime.now()));
//			
//			landingVisualiser.addObjectList(trajectory);
//			Thread.sleep(1000);
//
//		}
//		catch (InterruptedException e){
//			e.printStackTrace();
//		}
	}

}
