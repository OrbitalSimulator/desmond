package src;

import java.io.IOException;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.traj.TrajectoryPlanner;
import src.univ.Universe;
import src.visu.SetupMenu;
import src.visu.Visualiser;
import src.peng.Vector3d;

public class App 
{

	public static void main(String[] args)
    {
		SimulationSettings settings;
		try 
		{
			settings = SettingsFileManager.load();
			//SetupMenu setupMenu = new SetupMenu(settings);
			//while(!setupMenu.inputComplete())
			//{
			//	Thread.sleep(1000);
			//}
			//settings = setupMenu.getSettings();
			Universe universe = new Universe(settings);
			Visualiser.getInstance().addUniverse(universe);
			TrajectoryPlanner.integratedPlot(universe, settings);
		}
		catch(IOException e){
			e.printStackTrace();
		} 
		//catch (InterruptedException e){
		//	e.printStackTrace();
		//}
    }


}
