package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.config.DataFileManager;
import src.config.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.solv.RungeKutta4th;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Universe
{
    public CelestialBody[] startVariables; 
    public CelestialBody[][] universe;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public int noOfSteps;
	public double stepSize;
	public int stepSizeNanoSec;
	public double[] masses;
	public String[] wayPoints;
	
    public Universe(SimulationSettings settings)
    {
    	startTime = settings.startTime;
    	endTime = settings.endTime;
    	noOfSteps = settings.noOfSteps;
    	startVariables = settings.celestialBodies;
		stepSize = settings.stepSize;
		stepSizeNanoSec = (int) (stepSize*1E9);
     	masses = new double[startVariables.length];
    	for(int i = 0; i < startVariables.length; i++)
    	{
    		masses[i] = startVariables[i].mass;
    	}
    	
    	universe = new CelestialBody[startVariables.length][noOfSteps+1];
    	try
		{
    		universe = DataFileManager.load(settings);
		}
		catch (Exception e)
		{
			System.out.println("Unable to load config file");
			System.out.print("Creating new universe ...");
			universe = generateNewUniverse();
			System.out.println(" Done");
			System.out.print("Saving to file ...");
			saveToFile();
			System.out.print(" Done");
     	}
    }
     
    private CelestialBody[][] generateNewUniverse()
    {
		StateInterface initialState = convertToState(startVariables);
		ODEFunctionInterface function = new NewtonGravityFunction(masses);
		RungeKutta4th solver = new RungeKutta4th();
		StateInterface[] states = solver.solve(function, initialState, stepSize*noOfSteps, stepSize);			
		return convertToCelestialBody(states);														
    }
    
    public State convertToState(CelestialBody[] bodies)
    {
        ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();
        ArrayList<Vector3d> position = new ArrayList<Vector3d>();

        for(int i = 0; i < bodies.length; i++)
        {
            velocity.add(bodies[i].velocity);
            position.add(bodies[i].location);
        }
        return new State(velocity, position);
    }
        
    public CelestialBody[][] convertToCelestialBody(StateInterface[] stateInterfaces)
    {  	
    	State[] states = (State[]) stateInterfaces;
    	CelestialBody[][] bodies = new CelestialBody[universe.length][universe[0].length];
    	for(int i = 0; i < states.length; i++)
        {            
            LocalDateTime dateTime = startTime;
    		for(int j = 0; j < states[i].velocity.size(); j++)
            {
    			bodies[j][i] = startVariables[j].updateCopy(states[i].position.get(j),
                										states[i].velocity.get(j), 
                						   				dateTime);
            }
            dateTime.plusNanos((long) stepSizeNanoSec);
        }
    	return bodies;
    }
    
    public CelestialBody[] convertToCelestialBody(StateInterface stateInterfaces)
    {  	
    	State states = (State) stateInterfaces;
    	CelestialBody[] bodies = new CelestialBody[states.position.size()];
        LocalDateTime dateTime = startTime;
  		for(int i = 0; i < states.velocity.size(); i++)
        {
   			bodies[i] = startVariables[i].updateCopy(states.position.get(i),
                									 states.velocity.get(i), 
                						   			 dateTime);
   		}
        dateTime.plusNanos((long) stepSizeNanoSec);
    	return bodies;
    }
    
    public State getStateAt(int timeStep)
    {
    	return convertToState(universe[timeStep]);
    }
    
    public void setStateAt(int timeStep, StateInterface state)
    {
    	universe[timeStep] = convertToCelestialBody(state);
    }
    
    public void saveToFile()
    {
    	DataFileManager.overwrite(universe);
    }
}
