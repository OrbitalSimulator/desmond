package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.conf.DataFileManager;
import src.conf.SimulationSettings;
import src.peng.NewtonGravityFunction;
import src.peng.ODEFunctionInterface;
import src.peng.State;
import src.solv.Verlet;
import src.visu.Visualiser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;

public class Universe
{
    private boolean SAVE_TO_FILE = false;
	
	public CelestialBody[][] U;
	public double[] masses;
    
    private Verlet solver = new Verlet();
    private CelestialBody[] startVariables; 
	private LocalDateTime startTime;
	private int noOfSteps;
	private double stepSize;
	
    private ArrayList<Vector3d[]> permTrajectories = new ArrayList<Vector3d[]>();
    private ArrayList<Vector3d[]> tempTrajectories = new ArrayList<Vector3d[]>();
	private Stack<Vector3d[]> tempStack = new Stack<Vector3d[]>();
	private Stack<Vector3d[]> permStack = new Stack<Vector3d[]>();
	private boolean purgeTempTrajs = false;
	
	// ----- Universe Construction -----
	
    public Universe(SimulationSettings settings)
    {
    	constructor(settings);
    }
    
    public Universe(SimulationSettings settings, boolean save)
    {
    	SAVE_TO_FILE = save;
    	constructor(settings);
    }
        
    private void constructor(SimulationSettings settings)
    {
    	startTime = settings.startTime;
    	noOfSteps = settings.noOfSteps;
    	startVariables = settings.celestialBodies;
    	stepSize = settings.stepSize;
     	masses = new double[startVariables.length];
    	for(int i = 0; i < startVariables.length; i++)
    	{
    		masses[i] = startVariables[i].mass;
    	}
    	
    	U = new CelestialBody[startVariables.length][noOfSteps+1];
    	try
		{
    		System.out.print("Loading from file ...");
    		U = DataFileManager.load(settings);
    		System.out.println(" Done");
		}
		catch (Exception e)
		{
			System.out.println("Unable to load config file");
			U = generateNewUniverse();
				
			if(SAVE_TO_FILE)	
				saveToFile();
     	}
    }
         
    private CelestialBody[][] generateNewUniverse()
    {
    	System.out.print("Creating new U ...");
    	StateInterface initialState = convertToState(startVariables);
		ODEFunctionInterface function = new NewtonGravityFunction(masses);
		StateInterface[] states = solver.solve(function, initialState, stepSize*noOfSteps, stepSize);		
		System.out.println(" Done");
		return convertToCelestialBody(states);														
    }
       
    // ----- State and CelestialBody conversions -----
    
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
    	CelestialBody[][] bodies = new CelestialBody[U.length][U[0].length];
    	LocalDateTime dateTime = startTime;
    	for(int i = 0; i < states.length; i++)
        {            
    		for(int j = 0; j < states[i].velocity.size(); j++)
            {
    			bodies[j][i] = startVariables[j].updateCopy(states[i].position.get(j),
                										    states[i].velocity.get(j), 
                						   				    dateTime);
            }
            dateTime = dateTime.plusSeconds((long) stepSize);
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
   			dateTime = dateTime.plusSeconds((long) stepSize);
   		}
    	return bodies;
    }
    
    public State getStateAt(int timeStep)
    {
        ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();
        ArrayList<Vector3d> position = new ArrayList<Vector3d>();

        for(int i = 0; i < U.length; i++)
        {
            velocity.add(U[i][timeStep].velocity);
            position.add(U[i][timeStep].location);
        }
        return new State(velocity, position);
    }
    
    public void setStateAt(int timeStep, StateInterface state)
    {
    	U[timeStep] = convertToCelestialBody(state);
    }
    
    // ----- Merging Universes -----
    
    public void append(Universe other)
    {
    	this.permTrajectories.addAll(other.getPermTrajectories());
    	this.tempTrajectories.addAll(other.getTempTrajectories());
    	U = resizeUniverse(other.noOfSteps);
    	importUniverse(other.U);
    }
        
    private CelestialBody[][] resizeUniverse(int extraStepsNeeded)
    {
    	int newLength = U.length + extraStepsNeeded;
    	int noOfPlanets = U.length;
    	CelestialBody[][] resizedUniverse = new CelestialBody[noOfPlanets][newLength];
    	
    	for(int i = 0; i < U.length; i++)
    	{
    		for(int j = 0; j < U[i].length; j++)
    		{
    			resizedUniverse[j][i] = U[j][i];
    		}
    	}
    	
    	return resizedUniverse;
    }
    
    private void importUniverse(CelestialBody[][] other)
    {
    	int offset = noOfSteps + 1;
    	noOfSteps = this.noOfSteps + other[0].length;
    	for(int i = 0; i < U.length; i++)
    	{
    		for(int j = offset; j < U[i].length; j++)
    		{
    			U[i][j] = other[i][j - offset];
    		}
    	}
    }
     
    // ----- Trajectory Handling -----
        
    public void addTempTrajectory(Vector3d[] trajectory)
    {
    	tempStack.add(trajectory);
    	Visualiser.getInstance().update();
    }
    
    public ArrayList<Vector3d[]> getTempTrajectories()
    {
    	if(purgeTempTrajs)
    	{
    		tempStack.clear();
    		tempTrajectories.clear();
    		purgeTempTrajs = false;
    	}
    	
    	while(!tempStack.empty())
    	{
    		tempTrajectories.add(tempStack.pop());
    	}
    	return tempTrajectories;
    }
    
    public void clearTempTrajectories()
    {
    	purgeTempTrajs = true;
    }
    
    public void addPermTrajectory(Vector3d[] trajectory)
    {
    	permStack.add(trajectory);
    	Visualiser.getInstance().update();
    }
    
    public ArrayList<Vector3d[]> getPermTrajectories()
    {
    	while(!permStack.empty())
    	{
    		permTrajectories.add(permStack.pop());
    	}
    	return permTrajectories;
    }
    
    // ----- Saving -----
        
    public void saveToFile()
    {
    	System.out.print("Saving to file ...");
    	DataFileManager.overwrite(U);
		System.out.println(" Done");
    }
    
    public void setSaveToFile(boolean b)
    {
    	SAVE_TO_FILE = b;
    }
    
}
