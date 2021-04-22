package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.peng.Vector3dInterface;
import src.config.ConfigFileManager;
import src.config.DataFileManager;
import src.config.SimulationSettings;
import src.peng.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Universe
{
    public  CelestialBody[] startVariables; 
    public  CelestialBody[][] universe;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public int noOfSteps;

	public String[] wayPoints;
	
	// If known settings are provided, run this 
    public Universe(SimulationSettings settings)
    {
    	this.startTime = settings.startTime;
    	this.endTime = settings.endTime;
    	this.noOfSteps = settings.noOfSteps;
    	this.wayPoints = settings.wayPoints;
    	this.startVariables = settings.celestialBodies;
    	
    	universe = new CelestialBody[startVariables.length][noOfSteps];
    	DataFileManager dataBase = new DataFileManager();
     	if(dataBase.query(settings))
     	{
	    	try
			{
				
				CelestialBody[] temp = config.load("UniverseConfig");
				
			}
			catch (Exception e)
			{
				System.out.println("Unable to load config file");
				e.printStackTrace();
			}
     	}
     	else
     	{
     		
     	}
    }
    
    // If a config file is loaded, run this 
    public Universe(String configFileName)
    {
    	try
		{
			ConfigFileManager config = new ConfigFileManager();
			CelestialBody[] temp = config.load("UniverseConfig");
			U = new CelestialBody[temp.length+1];
			for(int i = 0; i < temp.length; i++)
			{
				U[i] = temp[i];
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to load config file");
			e.printStackTrace();
		}
    }
    
    // purely for tests 
    public Universe(CelestialBody[] universe)
    {
    	this.universe = universe;
    }
    
    
    //public getState(int step)
    
    	// Get the CBs at the time step
    	// Convert to a state
    
    // public setState(StateInterface[] state, int step)
    
    	// take in the stateInterface and set each as a CB
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public StateInterface initialState()
    {
        ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();
        ArrayList<Vector3d> position = new ArrayList<Vector3d>();

        for(int i=0; i< U.length; i++)
        {
            velocity.add(U[i].velocity);
            position.add(U[i].location);
        }
        return new State(velocity, position);
    }
    
    public double[] getMasses()
    {
    	double[] masses = new double[U.length];
    	for(int i = 0; i < masses.length; i++)
    	{
    		masses[i] = U[i].mass;
    	}
    	return masses;
    }
        
    public void update(StateInterface[] States)
    {
    	//LocalDateTime dateTime = LocalDateTime.of(date, time);
    	
    	U2 = new CelestialBody[U.length][States.length];
    	for(int i=0; i< States.length; i++)
        {
            //Cast each object into a State object
            State currentState = (State)States[i];

            //Iterate through each planet contained in a single state
            for(int j=0; j< currentState.velocity.size(); j++)
            {
                U2[j][i] = U[j].updateCopy(currentState.position.get(j),
                						   currentState.velocity.get(j), 
                						   dateTime);
            }
            dateTime.plusNanos(stepSize);
        }
    	save();
    }
    
    public void saveToFile()
    {
    	DataFileManager fileMngr = new DataFileManager();
    	for(int i = 0; i < U.length; i++)
    	{
    		fileMngr.overwrite(U2[i]);
    	}
    }
}
