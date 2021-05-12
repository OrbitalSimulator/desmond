package src;

import java.io.IOException;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.univ.CelestialBody;
import src.visu.SetupMenu;

public class App 
{
	public static void main(String[] args)
    {
		SimulationSettings settings ;
		try 
		{
			settings= SettingsFileManager.load();
			CelestialBody[] U= settings.celestialBodies;
			new SetupMenu(U);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
}
