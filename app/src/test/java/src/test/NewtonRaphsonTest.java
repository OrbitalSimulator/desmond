package src.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.traj.NewtonRaphson;
import src.traj.TrajectoryPlanner;
import src.univ.Universe;
import src.visu.SetupMenu;
import src.visu.Visualiser;

import java.io.IOException;

public class NewtonRaphsonTest
{
    @Test
    void newtonRaphsonStepThrough()
    {
        SimulationSettings settings;
        try
        {
            settings = SettingsFileManager.load();
            Universe universe = new Universe(settings);
            Visualiser.getInstance().addUniverse(universe);
            TrajectoryPlanner.newtonRaphsonPlot(universe, 3, 8, settings);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
