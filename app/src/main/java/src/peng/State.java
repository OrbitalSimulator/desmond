package src.peng;

import java.util.ArrayList;

public class State implements StateInterface
{
    public ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();
    public ArrayList<Vector3d> position = new ArrayList<Vector3d>();
    public double time;

    /**
     * State constructor (No time parameter)
     * @param vel The velocity vector
     * @param pos The position vector
     */
    public State(ArrayList<Vector3d> velocity, ArrayList<Vector3d> position)
    {
        this.velocity = velocity;
        this.position = position;
        this.time = 0;
    }

    /**
     * State constructor (Time parameter)
     * @param vel The velocity vector
     * @param pos The position vector
     * @param t The time quantity of the current state
     */
    public State(ArrayList<Vector3d> velocity, ArrayList<Vector3d> position, double time)
    {
        this.velocity = velocity;
        this.position = position;
        this.time = time;
    }

    /**
     * Modifies state to yield the next sequential state
     * @param step The time step
     * @param rate The rate of change
     * @return The next sequential state
     */
    public StateInterface addMul(double step, RateInterface rate)     //Essentially carry out step yi+1 = yi + hif(ti, yi)
    {
        Rate changeInfo = (Rate)rate;                                 //Cast RateInterface into rate

        ArrayList<Vector3d> v = new ArrayList<Vector3d>();            //Initialise new ArrayLists to aid in construction of resultant StateInterface
        ArrayList<Vector3d> p = new ArrayList<Vector3d>();

        for(int i=0; i< changeInfo.velocityChange.size(); i++)        //Iterate over Rate fields
        {
            v.add(velocity.get(i).addMul(step,changeInfo.velocityChange.get(i)));
            p.add(position.get(i).addMul(step,changeInfo.positionChange.get(i)));
        }
        double time = this.time + step;                            //Calculate increase in time:

        return new State(v,p,time);
    }
    
    /**
     * Adds a position and location vector change to each vector held in the state
     * @param step - The time step to be taken
     * @param rate - The rate of change
     * @return a new state as at time+step
     */
    public State addMultiple(double step, RateInterface rate)
    {
        //Cast RateInterface into rate
        Rate changeInfo = (Rate)rate;

        //Initialise new ArrayLists to aid in construction of resultant StateInterface
        ArrayList<Vector3d> v = new ArrayList<Vector3d>();
        ArrayList<Vector3d> p = new ArrayList<Vector3d>();

        //Iterate over Rate fields
        for(int i=0; i< changeInfo.velocityChange.size(); i++)
        {
            v.add(velocity.get(i).addMul(step,changeInfo.velocityChange.get(i)));
            p.add(position.get(i).addMul(step,changeInfo.positionChange.get(i)));
        }
        double time = this.time + step;     //Calculate increase in time:
        return new State(v,p,time);
    }

    /**
     * Displays State in String format
     * @return String representing state object
     */
    public String toString()
    {
        String sum = "";
        for(int i=0; i< velocity.size(); i++)
        {
            sum += "\n(V: "+ velocity.get(i).toString() + " |P: "+ position.get(i).toString()+ " | Time: "+ time + "),";
        }
        return sum;
    }
    
    /**
     * Displays State in String format
     * @return String representing state object
     */
    public String toCSV()
    {
        String sum = "";
        for(int i=0; i< velocity.size(); i++)
        {
            sum += "\nV:,"+ velocity.get(i).toCSV() + ",P:"+ position.get(i).toCSV()+ ",";
        }
        return sum;
    }
    
    
    /**
     * Adds the argument with this state and returns the answer
     * @param state to be addd
     * @return sum of the two states
     */
    public State add(State state)
    {
    	for(int i = 0; i < position.size(); i++)
    	{
    		state.position.get(i).add(this.position.get(i));
    		state.velocity.get(i).add(this.velocity.get(i));
    	}
    	return state;
    }
    
    /**
     * Multiplies this class by the scalar argument
     * @param scalar
     * @return a scaled copy of this class
     */
    public State scale(double scalar)
    {
    	ArrayList<Vector3d> vCopy = velocity;
    	ArrayList<Vector3d> pCopy = position;
    	    	
    	for(int i = 0; i < position.size(); i++)
    	{
    		vCopy.get(i).mul(scalar);
    		pCopy.get(i).mul(scalar);
    	}
    	return new State(vCopy, pCopy);
    }
}
