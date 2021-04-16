package src;

import src.peng.Simulation;
import src.peng.Vector3d;

public class App 
{
    // TODO APP add GUI startup and selection implementation
	
	public static void main(String[] args) 
    {
		runSimulation();
    }

	//TODO APP Sort out simulation vs ephemeris selection

	public static void runSimulation()
	{
		double tf = 31556926.0;
		double h = 1000.0;
			 
		Simulation sim = new Simulation();
		sim.trajectory(new Vector3d(-1.471000001603588e+11,  -2.860000000266412e+10,   8.278180000000080e+06), new Vector3d(
				22021.0193300029,
				-57508.4276505118,
				-857.448354683806), tf, h);
		System.out.println("done");
	}

}
