package src;

import java.io.IOException;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.traj.TrajectoryPlanner;
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
			visualiser.addTrajectory(TrajectoryPlanner.plot(universe, settings));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
}
