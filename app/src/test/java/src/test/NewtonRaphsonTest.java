package src.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.traj.NewtonRaphson;
import src.univ.Universe;
import src.visu.SetupMenu;

import java.io.IOException;

public class NewtonRaphsonTest
{
    /*Test fires prove directly upward from Earth, therefore the closest distance
     *to the target (The sun, is the initial probe launch position
     */
    @Test
    void testClosestPoint()
    {
        try
        {
            SimulationSettings settings = SettingsFileManager.load();
            SetupMenu setupMenu = new SetupMenu(settings);
            Universe universe = new Universe(settings);
            NewtonRaphson nr = new NewtonRaphson(universe, 3, 0, settings);
            Vector3d[] trajectory = nr.planRoute(0,  4.73e7, new Vector3d(0, -100000, 0));
            Vector3d closestPoint = nr.calculateClosestPoint(trajectory);
            System.out.println("Closest Point :" + closestPoint.toString());

            assertTrue(nr.getLaunchPoint().equals(closestPoint));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
