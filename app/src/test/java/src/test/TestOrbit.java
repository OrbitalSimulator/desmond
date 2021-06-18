package src.test;

import org.junit.jupiter.api.Test;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3d;
import src.traj.OrbitController;
import src.univ.Universe;
import log.Logger;

import java.io.IOException;

public class TestOrbit
{
    private int target = 8;
    private String fileName = "exp_linearOrbitAlg";
    private String loggingHeaders = "Velocity_x, error";

    @Test
    void logLinearClimbingAlg()
    {
        SimulationSettings settings;
        try
        {
            settings = SettingsFileManager.load();
            settings.stepSize = 12;
            settings.noOfSteps = 60000;
            Universe universe = new Universe(settings);
            OrbitController.setLogActive();
            OrbitController.visualizerOff();

            OrbitController oc = new OrbitController(universe, target, settings);
            double optimumVelocityScaler = oc.linearClimbing(universe, target, settings);
            Vector3d optimumVelocity = oc.optimumVelocityScalerToVector(optimumVelocityScaler);
            Vector3d[] trajectory = oc.planRoute(optimumVelocity, target, universe);

            double[] errorLog = oc.getErrorCollection();
            double[] velocityLog = oc.getVelocityCollection();
            Logger.logCSV(fileName, loggingHeaders);

            //for(int i = 0; i < errorLog.length; i++)
            //{
            //    String currentIterationData = String.valueOf(errorLog[i]) + ", " + String.valueOf(velocityLog[i]);
             //   Logger.logCSV(fileName, currentIterationData);
            //}

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    void logLinearClimbingAtOrbitalPosition()
    {
        SimulationSettings settings;
        try
        {
            settings = SettingsFileManager.load();
            settings.stepSize = 15;
            settings.noOfSteps = 60000;
            Universe universe = new Universe(settings);
            OrbitController.setLogActive();
            OrbitController.visualizerOff();
            OrbitController oc = new OrbitController(universe, target, settings);

            double[] errorLog = oc.getErrorCollection();
            double[] velocityLog = oc.getVelocityCollection();
            Logger.logCSV(fileName, loggingHeaders);

            for(int i = 0; i < errorLog.length; i++)
            {
                String currentIterationData = String.valueOf(errorLog[i]) + ", " + String.valueOf(velocityLog[i]);
                Logger.logCSV(fileName, currentIterationData);
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
