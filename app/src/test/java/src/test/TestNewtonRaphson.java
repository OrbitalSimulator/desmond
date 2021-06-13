package src.test;

import org.junit.jupiter.api.Test;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.traj.NewtonRaphson;
import src.traj.TrajectoryPlanner;
import src.peng.Vector3d;
import src.univ.Universe;
import src.visu.Visualiser;
import log.Logger;

import java.io.IOException;

public class TestNewtonRaphson
{
    private Vector3d vector0 = new Vector3d(0,0,0);
    private Vector3d vector1 = new Vector3d(5000,-5000,0);
    private Vector3d vector2 = new Vector3d(10000,-10000,0);
    private Vector3d vector3 = new Vector3d(20000,-20000,0);
    private Vector3d vector4 = new Vector3d(30000,-30000,0);
    private double timeFrame = 3.154e7;

    @Test
    void testVector0()
    {
        logIterationData("exp_vector(0,0,0)", vector0);
    }

    @Test
    void testVector1()
    {
        logIterationData("exp_vector(5000,-5000,0)", vector1);
    }

    @Test
    void testVector2()
    {
        logIterationData("exp_vector(10000,-10000,0)", vector2);
    }

    @Test
    void testVector3()
    {
        logIterationData("exp_vector(20000,-20000,0)", vector3);
    }

    @Test
    void testVector4()
    {
        logIterationData("exp_vector(30000,-30000,0)", vector4);
    }

    private void logIterationData(String fileName, Vector3d startingVelocity)
    {
        SimulationSettings settings;
        String loggingData = "";
        try
        {
            settings = SettingsFileManager.load();
            Universe universe = new Universe(settings);
            Visualiser.getInstance().addUniverse(universe);
            try
            {
                NewtonRaphson nr = new NewtonRaphson(universe, 3, 8, settings, 0, timeFrame, startingVelocity);
                Vector3d optimalVelocity = nr.newtonRaphsonIterativeMethod();
                Vector3d[] trajectory = nr.planRoute(optimalVelocity);
                int iterations = nr.getIteration();
                loggingData += ("Iterations: " + String.valueOf(iterations) + " Optimal Velocity: " + optimalVelocity.toString() + " TimeFrame: " + String.valueOf(timeFrame));
            }
            catch(RuntimeException e)
            {
                loggingData += "Non-convergence";
            }

            Logger.logCSV(fileName, loggingData);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
