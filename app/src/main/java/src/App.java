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
			Vector3d startingVelocity = new Vector3d(0,0,0);
			TrajectoryPlanner.newtonRaphsonPlot(universe, 3, 8, settings, 0, 3.154e7, startingVelocity);
		}
		catch(IOException e){
			e.printStackTrace();
		} 
		//catch (InterruptedException e){
		//	e.printStackTrace();
		//}
    }


}
