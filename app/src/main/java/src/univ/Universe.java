package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.peng.Vector3dInterface;
import src.config.ConfigFileManager;
import src.config.DataFileManager;
import src.peng.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Universe
{
    public  CelestialBody[] U; 
    public  CelestialBody[][] U2;
    private int stepSize;
    private LocalDate probeStartDate = LocalDate.parse("2021-04-01");
    private LocalTime probeStartTime = LocalTime.parse("00:00:00");
   
    public Universe(Vector3dInterface probeStartPosition, Vector3dInterface probeStartVelocity, int stepInNanoSecs)
    {
    	stepSize = stepInNanoSecs;
    	try
		{
			ConfigFileManager config = new ConfigFileManager();
			CelestialBody[] temp = config.load("UniverseConfig");
			U = new CelestialBody[temp.length+1];
			for(int i = 0; i < temp.length; i++)
			{
				U[i] = temp[i];
			}
			U[U.length-1] = new CelestialBody((Vector3d)probeStartPosition,
							    (Vector3d)probeStartVelocity,
							    15000,
							    700,
							    "Probe",
							    "/src/main/java/misc/craftIcon.png",
							    "/src/main/java/misc/craftIcon.png",
							    LocalDateTime.of(probeStartDate, probeStartTime));
		}
		catch (Exception e)
		{
			System.out.println("Unable to load config file");
			e.printStackTrace();
		}
    }
 
    public Universe(CelestialBody[] U)
    {
    	this.U = U;
    }
    
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
    	LocalDate date = probeStartDate;
    	LocalTime time = probeStartTime;
    	LocalDateTime dateTime = LocalDateTime.of(date, time);
    	
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
    
    public void save()
    {
    	DataFileManager fileMngr = new DataFileManager();
    	for(int i = 0; i < U.length; i++)
    	{
    		fileMngr.overwrite(U2[i]);
    	}
    }
}
