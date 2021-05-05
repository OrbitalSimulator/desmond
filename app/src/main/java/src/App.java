package src;

import java.io.IOException;

import src.config.SettingsFileManager;
import src.config.SimulationSettings;
import src.peng.Simulation;
import src.peng.Vector3d;
import src.univ.Universe;
import src.visu.Visualiser;

public class App 
{
	public static void main(String[] args) 
    {
		SimulationSettings settings;
		try 
		{
			settings = SettingsFileManager.load();
			Universe universe = new Universe(settings);
			Visualiser visualiser = new Visualiser(universe.universe);
			
			int steps = 10000;
			Vector3d[] T1 = new Vector3d[steps];
			double x = -1.471922101663588e+11;
			double y = -2.860995816266412e+10;
			double z =  8.278183193596080e+06;
			for(int i = 0; i < steps; i++)
			{
				T1[i] = new Vector3d(x,y,z);
				x += 2000000;
				y += -5000000;
				z += -273;
			}
			visualiser.addTrajectory(T1);
			
			x = -1.471922101663588e+11;
			y = -2.860995816266412e+10;
			z =  8.278183193596080e+06;
			Vector3d[] T2 = new Vector3d[steps];
			for(int i = 0; i < steps; i++)
			{
				T2[i] = new Vector3d(x,y,z);
				x += 3000000;
				y += -5000000;
				z += -273;
			}
			visualiser.addTrajectory(T2);
			
			x = -1.471922101663588e+11;
			y = -2.860995816266412e+10;
			z =  8.278183193596080e+06;
			Vector3d[] T3 = new Vector3d[steps];
			for(int i = 0; i < steps; i++)
			{
				T3[i] = new Vector3d(x,y,z);
				x += 1000000;
				y += -5000000;
				z += -273;
			}
			visualiser.addTrajectory(T3);
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
}
