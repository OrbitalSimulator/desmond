package src.test;

import org.junit.jupiter.api.Test;
import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
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
