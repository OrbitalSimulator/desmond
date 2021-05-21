package src;

import java.io.IOException;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.traj.TrajectoryPlanner;
import src.univ.Universe;
import src.visu.SetupMenu;
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
			Visualiser.getInstance().addUniverse(universe.universe);
			TrajectoryPlanner.simplePlot(universe, settings);
			TrajectoryPlanner.plotRoute(universe, settings);

		}
		catch(IOException e){
			e.printStackTrace();
		}
    }


}
