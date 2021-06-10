package src.peng;

import java.util.ArrayList;

public class State2d implements StateInterface
{
    public ArrayList<Vector2d> velocity = new ArrayList<Vector2d>();
    public ArrayList<Vector2d> position = new ArrayList<Vector2d>();
    public double time;
    public double direction;

    /**
     * State constructor (No time parameter)
     * @param vel The velocity vector
     * @param pos The position vector
     */
    public State2d(ArrayList<Vector2d> velocity, ArrayList<Vector2d> position)
    {
        this.velocity = velocity;
        this.position = position;
        this.time = 0;
        this.direction = 0;
    }

    /**
     * State constructor (Time parameter)
     * @param vel The velocity vector
     * @param pos The position vector
     * @param t The time quantity of the current state
     */
    public State2d(ArrayList<Vector2d> velocity, ArrayList<Vector2d> position, double time)
    {
        this.velocity = velocity;
        this.position = position;
        this.time = time;
    }
    
    /**
     * State constructor (Time parameter)
     * @param vel The velocity vector
     * @param pos The position vector
     * @param t The time quantity of the current state
     */
    public State2d(ArrayList<Vector2d> velocity, ArrayList<Vector2d> position, double time, double direction)
    {
        this.velocity = velocity;
        this.position = position;
        this.time = time;
        this.direction = direction;
    }

    /**
     * Modifies state to yield the next sequential state
     * @param step The time step
     * @param rate The rate of change
     * @return The next sequential state
     */
    public StateInterface addMul(double step, RateInterface rate)     //Essentially carry out step yi+1 = yi + hif(ti, yi)
    {
        Rate2d changeInfo = (Rate2d)rate;                                 //Cast RateInterface into rate

        ArrayList<Vector2d> v = new ArrayList<Vector2d>();            //Initialise new ArrayLists to aid in construction of resultant StateInterface
        ArrayList<Vector2d> p = new ArrayList<Vector2d>();

        for(int i=0; i< changeInfo.velocityChange.size(); i++)        //Iterate over Rate fields
        {
            v.add(velocity.get(i).addMul(step,changeInfo.velocityChange.get(i)));
            p.add(position.get(i).addMul(step,changeInfo.positionChange.get(i)));
        }
        double time = this.time + step;                            //Calculate increase in time:

        return new State2d(v,p,time);
    }
    
    /**
     * Adds a position and location vector change to each vector held in the state
     * @param step - The time step to be taken
     * @param rate - The rate of change
     * @return a new state as at time+step
     */
    public State2d addMultiple(double step, RateInterface rate)
    {
        //Cast RateInterface into rate
        Rate2d changeInfo = (Rate2d)rate;

        //Initialise new ArrayLists to aid in construction of resultant StateInterface
        ArrayList<Vector2d> v = new ArrayList<Vector2d>();
        ArrayList<Vector2d> p = new ArrayList<Vector2d>();

        //Iterate over Rate fields
        for(int i=0; i< changeInfo.velocityChange.size(); i++)
        {
            v.add(velocity.get(i).addMul(step,changeInfo.velocityChange.get(i)));
            p.add(position.get(i).addMul(step,changeInfo.positionChange.get(i)));
        }
        double time = this.time + step;     //Calculate increase in time:
        return new State2d(v,p,time);
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
    public State2d add(State2d state)
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
    public State2d scale(double scalar)
    {
    	ArrayList<Vector2d> vCopy = velocity;
    	ArrayList<Vector2d> pCopy = position;
    	    	
    	for(int i = 0; i < position.size(); i++)
    	{
    		vCopy.get(i).mul(scalar);
    		pCopy.get(i).mul(scalar);
    	}
    	return new State2d(vCopy, pCopy);
    }
}
