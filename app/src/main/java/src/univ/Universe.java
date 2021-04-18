package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.peng.Vector3dInterface;
import src.data.ConfigFileManager;
import src.data.DataFileManager;
import src.peng.State;

import java.util.ArrayList;

public class Universe
{
    public  CelestialBody[] U; 
    public  CelestialBody[][] U2;
   
    public Universe(Vector3dInterface p0, Vector3dInterface v0)
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
			U[U.length-1] = new CelestialBody((Vector3d)p0,
							    (Vector3d)v0,
							    15000,
							    700,
							    "Probe",
							    "/src/main/java/misc/craftIcon.png",
							    "/src/main/java/misc/craftIcon.png",
							    new DTG());
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
        U2 = new CelestialBody[U.length][States.length];
    	for(int i=0; i< States.length; i++)
        {
            //Cast each object into a State object
            State temp = (State)States[i];

            //Iterate through each planet contained in a single state
            for(int j=0; j< temp.velocity.size(); j++)
            {
                U2[j][i] = U[j].updateCopy(temp.position.get(j),temp.velocity.get(j), new DTG());	//TODO (Leon) DTG as ms to be able to convert here
            }
        }
    	save();
    }
    
    public void save()
    {
    	DataFileManager fileMngr = new DataFileManager();
    	for(int i = 0; i < U.length; i++)
    	{
    		fileMngr.save(U2[i]);
    	}
    }
}
