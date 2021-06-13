package src.test;

import org.junit.jupiter.api.Test;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.traj.NewtonRaphson;
import src.peng.Vector3d;
import src.univ.Universe;
import log.Logger;

import java.io.IOException;

public class TestNewtonRaphson
{
    private Vector3d[] velocities = {new Vector3d(0,0,0), new Vector3d(5000,-5000,0), new Vector3d(10000,-10000,0),
                                    new Vector3d(20000,-20000,0), new Vector3d(30000,-30000,0)};
    private double[] timeFrames = {3.154e7, 6.307e7, 9.461e7, 1.261e8, 1.577e8, 1.892e8};

    @Test
    void executeAllLogging()
    {
        String fileName = "exp_";
        String loggingDataHeadings = "Initial Velocity, Iterations, Optimal Velocity, Timeframe, Delta, Velocity at Target";

        for(Vector3d velocity: velocities)
        {
            fileName += velocity.toString();
            Logger.logCSV(fileName, loggingDataHeadings);

            for(double time: timeFrames)
            {
                logIterationData(fileName, velocity, time);
            }
            fileName = "exp_";
        }
    }

    @Test
    void executeSingleLogging()
    {
        double time = 3.154e7;
        Vector3d initialVelocity = new Vector3d(15000,-15000,0);
        String fileName = "exp_" + initialVelocity.toString();
        String loggingDataHeadings = "Initial Velocity, Iterations, Optimal Velocity, Timeframe, Delta, Velocity at Target";

        Logger.logCSV(fileName, loggingDataHeadings);
        logIterationData(fileName, initialVelocity, time);
    }

    private void logIterationData(String fileName, Vector3d startingVelocity, double timeFrame)
    {
        SimulationSettings settings;
        String loggingData = "";
        try
        {
            settings = SettingsFileManager.load();
            Universe universe = new Universe(settings);
            try
            {
                NewtonRaphson nr = new NewtonRaphson(universe, 3, 8, settings, 0, timeFrame, startingVelocity);
                Vector3d optimalVelocity = nr.newtonRaphsonIterativeMethod();

                int iterations = nr.getIteration();
                double delta = nr.getDelta();
                Vector3d velocityAtTarget = nr.getVelocityAtTarget();
                loggingData += (startingVelocity.toString() +", " + String.valueOf(iterations) + ", " + optimalVelocity.toString() + ", " + String.valueOf(timeFrame) + ", " + delta + ", " + velocityAtTarget.toString());
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
