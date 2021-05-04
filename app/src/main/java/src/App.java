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
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
}
