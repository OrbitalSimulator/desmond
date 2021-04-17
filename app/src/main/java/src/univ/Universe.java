package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.peng.Vector3dInterface;
import src.peng.State;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

//Class containing universal constants
public class Universe
{
    public static final boolean DEBUG = false;

    public  CelestialBody[] U; 
    public  CelestialBody[][] U2;
   
    public Universe(Vector3dInterface p0, Vector3dInterface v0)
    {

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
                U2[j][i] = U[j].copyOf(temp.position.get(j),temp.velocity.get(j), new DTG());	//TODO (Leon) DTG as ms to be able to convert here
            }
        }
    }
}
