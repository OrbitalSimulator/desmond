package src;

import src.peng.Simulation;
import src.peng.Vector3d;

public class App 
{
    // TODO (Alp) APP add GUI startup and selection implementation
	// TODO (Jerome) APP add Setting File Loading
	public static void main(String[] args) 
    {
		runSimulation();
    }

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
