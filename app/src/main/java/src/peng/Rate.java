package src.peng;

import java.util.ArrayList;

//TODO (Travis Dawson) Clean up Rate
/**
 * Derivative of y, such that it reflects the rate of change of velocity and position
 */
public class Rate implements RateInterface
{
    public ArrayList<Vector3d> velocityChange = new ArrayList<Vector3d>();
    public ArrayList<Vector3d> positionChange = new ArrayList<Vector3d>();

    /**
     * Constructor
     * @param cv Change in velocity arrayList
     * @param cp Change in position arrayList
     */
    public Rate(ArrayList<Vector3d> cv, ArrayList<Vector3d> cp)
    {
        this.velocityChange = cv;
        this.positionChange = cp;
    }
    
    /**
     * Find the average values between this interface and the parameter
     * @param comp
     * @return the mean values between this and comp
     */
    public Rate average(Rate comp)
    {
        ArrayList<Vector3d> compareVelocity = comp.velocityChange;
        ArrayList<Vector3d> comparePosition = comp.positionChange;
    	
    	ArrayList<Vector3d> averageVelocity = new ArrayList<Vector3d>();
        ArrayList<Vector3d> averagePosition = new ArrayList<Vector3d>();
        
        for(int i = 0; i < velocityChange.size(); i++)
        {
        	Vector3d velocityDifference = velocityChange.get(i).add(compareVelocity.get(i));
        	averageVelocity.add(velocityDifference.mul(0.5));
        	
        	Vector3d positionDifference = positionChange.get(i).add(comparePosition.get(i));
        	averagePosition.add(positionDifference.mul(0.5));
        }
        
        return new Rate(averageVelocity, averagePosition);
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
    public Rate add(Rate rate)
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
    public Rate scale(double scalar)
    {
    	ArrayList<Vector3d> vCopy = velocityChange;
    	ArrayList<Vector3d> pCopy = positionChange;
    	    	
    	for(int i = 0; i < positionChange.size(); i++)
    	{
    		vCopy.get(i).mul(scalar);
    		pCopy.get(i).mul(scalar);
    	}
    	return new Rate(vCopy, pCopy);
    }
}
