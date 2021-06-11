package src.peng;

import java.util.ArrayList;

/**
 * Derivative of y, such that it reflects the rate of change of velocity and position
 */
public class Rate2d implements RateInterface
{
    public ArrayList<Vector2d> velocityChange = new ArrayList<Vector2d>();
    public ArrayList<Vector2d> positionChange = new ArrayList<Vector2d>();

    /**
     * Constructor
     * @param cv Change in velocity arrayList
     * @param cp Change in position arrayList
     */
    public Rate2d(ArrayList<Vector2d> cv, ArrayList<Vector2d> cp)
    {
        this.velocityChange = cv;
        this.positionChange = cp;
    }
    
    /**
     * Find the average values between this interface and the parameter
     * @param comp
     * @return the mean values between this and comp
     */
    public Rate2d average(Rate2d comp)
    {
        ArrayList<Vector2d> compareVelocity = comp.velocityChange;
        ArrayList<Vector2d> comparePosition = comp.positionChange;
    	
    	ArrayList<Vector2d> averageVelocity = new ArrayList<Vector2d>();
        ArrayList<Vector2d> averagePosition = new ArrayList<Vector2d>();
        
        for(int i = 0; i < velocityChange.size(); i++)
        {
        	Vector2d velocityDifference = velocityChange.get(i).add(compareVelocity.get(i));
        	averageVelocity.add(velocityDifference.mul(0.5));
        	
        	Vector2d positionDifference = positionChange.get(i).add(comparePosition.get(i));
        	averagePosition.add(positionDifference.mul(0.5));
        }
        
        return new Rate2d(averageVelocity, averagePosition);
    }

    /**
     * toString returns Rate as a String
     * @return String value representing Rate
     */
    public String toString()
    {
        String sum = "";
        for(int i=0; i< velocityChange.size(); i++)
        {
            sum += "(CV: "+ velocityChange.get(i).toString() + " |CP: "+ positionChange.get(i).toString()+ " ),";
        }
        return sum;
    }
    
    /**
     * Adds the argument with this state and returns the answer
     * @param state to be addd
     * @return sum of the two states
     */
    public Rate2d add(Rate2d rate)
    {
    	for(int i = 0; i < positionChange.size(); i++)
    	{
    		rate.positionChange.get(i).add(this.positionChange.get(i));
    		rate.velocityChange.get(i).add(this.velocityChange.get(i));
    	}
    	return rate;
    }
    
    /**
     * Multiplies this class by the scalar argument
     * @param scalar
     * @return a scaled copy of this class
     */
    public Rate2d scale(double scalar)
    {
    	ArrayList<Vector2d> vCopy = velocityChange;
    	ArrayList<Vector2d> pCopy = positionChange;
    	    	
    	for(int i = 0; i < positionChange.size(); i++)
    	{
    		vCopy.get(i).mul(scalar);
    		pCopy.get(i).mul(scalar);
    	}
    	return new Rate2d(vCopy, pCopy);
    }
}
