package src.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.traj.NewtonRaphson;
import src.univ.Universe;
import src.visu.SetupMenu;
import src.visu.Visualiser;

import java.io.IOException;

public class NewtonRaphsonTest
{
    /*Test fires prove directly upward from Earth, therefore the closest distance
     *to the target (The sun, is the initial probe launch position
     * -Needs to be rewritten due to new NR constructor!
     */
    @Test
    void testClosestPoint()
    {
        try
        {
            SimulationSettings settings = SettingsFileManager.load();
            SetupMenu setupMenu = new SetupMenu(settings);
            Universe universe = new Universe(settings);
            NewtonRaphson nr = new NewtonRaphson(universe, 3, 0, settings, 0,  4.73e7);
            Vector3d[] trajectory = nr.planRoute(new Vector3d(0, -100000, 0));
            Vector3d closestPoint = nr.calculateClosestPoint(trajectory);
            System.out.println("Closest Point :" + closestPoint.toString());

            assertTrue(nr.getLaunchPoint().equals(closestPoint));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void nrStepThrough()
    {
        try
        {
            SimulationSettings settings = SettingsFileManager.load();
            SetupMenu setupMenu = new SetupMenu(settings);
            Universe universe = new Universe(settings);
            Visualiser.getInstance().addUniverse(universe.universe);
            NewtonRaphson nr = new NewtonRaphson(universe, 3, 8, settings, 0,  1.577e7);
            Vector3d[] trajectory = nr.getTrajectory();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
