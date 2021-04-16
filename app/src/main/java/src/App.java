package src;

import src.univ.CelestialBody;
import src.univ.Coordinate;
import src.univ.DTG;
import src.visu.Visualiser;
import src.data.EphemerisReader;
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

	public static void runEphemeris()
	{
		int noOfCB = 11;
		
		// Find all files that start with "Ephemeris_" and end with ".txt"
		String[] paths = new String[11];
		for(int i = 0; i < noOfCB; i++)
		{
			
			StringBuilder s =  new StringBuilder();
			s.append("/src/main/java/src/data/Ephemeris_");
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
		
		double x = -1.471922101663588e+11;
		double y = -2.860995816266412e+10;
		double z = 8.278183193596080e+06;
		Coordinate[] T = new Coordinate[U[0].length];
		for(int i = 0; i < U[0].length; i++)
		{
			T[i] = new Coordinate(x,y,z,new DTG());
			x += 12E7;
			y -= 13.5E7;
			z += 0;
		}
		
		Coordinate[] T2 = new Coordinate[U[0].length];
		x = -1.471922101663588e+11;
		y = -2.860995816266412e+10;
		z = 8.278183193596080e+06; 
		for(int i = 0; i < U[0].length; i++)
		{
			T2[i] = new Coordinate(x,y,z,new DTG());
			x += 12E7;
			y -= 13E7;
			z += 0;
		}
		v.addTrajectory(T);
		v.addTrajectory(T2);
	}
}
