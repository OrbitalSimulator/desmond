
package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import expData.EphemerisReader;
import src.peng.Simulation;
import src.peng.Vector3d;
import src.peng.Vector3dInterface;
import src.univ.CelestialBody;
import src.visu.Visualiser;

class TestSimulation 
{

	@Test
	void testFinalTimeTraj()
    {
		/*Initialise*/
    	 double tf = 31556926.0;																		//Initialise time and step
         double h = 1000.0;

		/*Calculate*/
    	Simulation sim = new Simulation();																//Generate simulation
    	Vector3dInterface[] container = sim.trajectory(
			new Vector3d(-1.471000001603588e+11,  -2.860000000266412e+10,   8.278180000000080e+06), 
			new Vector3d( 22021.0193300029, -57508.4276505118,-857.448354683806), tf, h);
    	Vector3d finalPosition = (Vector3d)container[container.length -1];								//Access final posiiton of probe
		System.out.println("Final position"+finalPosition.toString());									//display

		/*Test*/
		assertEquals(new Vector3d(8.588236950026453E11, -1.2481403147793928E12, -1.821731228852048E10), finalPosition);
    }

	@Test
	void testTimeArrayTraj()
	{
		/*Initialise*/
		double[] ts = new double[10000];
		double targetTime = 31556926.0;
		double stepSize = targetTime/ts.length;
		for(int i=0; i<ts.length; i++)																	//Populate time array
		{
			ts[i] = stepSize*i;
		}

		/*Calculate*/
		Simulation sim = new Simulation();
		Vector3dInterface[] container = sim.trajectory(
			new Vector3d(-1.471000001603588e+11,  -2.860000000266412e+10,   8.278180000000080e+06),
			new Vector3d(22021.0193300029, -57508.4276505118, -857.448354683806), ts);
		Vector3d finalPosition = (Vector3d)container[container.length -1];								//Access final posiiton of probe
		System.out.println("Final position"+finalPosition.toString());									//Display

		/*Test*/
		assertEquals(new Vector3d(8.569328863702513E11, -1.2493022716897788E12, -1.838286941261663E10), finalPosition);
	}
	
	@Test
	void testAgainstEphemeris()
	{
		int noOfCB = 11;
		
		// Find all files that start with "Ephemeris_" and end with ".txt"
		String[] paths = new String[11];
		for(int i = 0; i < noOfCB; i++)
		{
			
			StringBuilder s =  new StringBuilder();
			s.append("/src/test/java/expData/Ephemeris_");
			s.append(i);
			s.append(".txt");
			paths[i] = s.toString();
		}
		
		EphemerisReader er = new EphemerisReader(paths[0]);
		CelestialBody[] temp = er.getOrbit();
		CelestialBody[][] U = new CelestialBody[noOfCB][temp.length];
		U[0] = temp;
			
		// Read all of the files and add the array to the list
		EphemerisReader[] ers = new EphemerisReader[paths.length];
		for(int i = 1; i < U.length; i++)
		{
			er = new EphemerisReader(paths[i]);
			U[i] = er.getOrbit(); 
		}
		
		Visualiser v = new Visualiser(U);
	}
}
