
package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.peng.Simulation;
import src.peng.Vector3d;
import src.peng.Vector3dInterface;

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
    	sim.DISPLAY_VISUALISER = false;
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
		sim.DISPLAY_VISUALISER = false;
		Vector3dInterface[] container = sim.trajectory(
			new Vector3d(-1.471000001603588e+11,  -2.860000000266412e+10,   8.278180000000080e+06),
			new Vector3d(22021.0193300029, -57508.4276505118, -857.448354683806), ts);
		Vector3d finalPosition = (Vector3d)container[container.length -1];								//Access final posiiton of probe
		System.out.println("Final position"+finalPosition.toString());									//Display

		/*Test*/
		assertEquals(new Vector3d(8.569328863702513E11, -1.2493022716897788E12, -1.838286941261663E10), finalPosition);
	}
}
