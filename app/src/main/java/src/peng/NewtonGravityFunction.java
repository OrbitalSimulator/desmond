package src.peng;

import java.util.ArrayList;

import src.univ.Universe;

//TODO (Travis Dawson) Clean up comments in class functions
public class NewtonGravityFunction implements ODEFunctionInterface
{
	public static final boolean DEBUG = false;
    public double[] masses;

    /**
     * Constructor
     * @param masses Represent the masses of all Celestial Bodies
     */
    public NewtonGravityFunction(double[] masses)
    {
        this.masses = masses;
    }
	
    /**
     * Method is utilized to calculate yPrime, the derivative of the state y.
     * @param t The time at which to evaluate
     * @param y The current state at which to evaluate
     * @return The average rate of change of the state over time
     */
	public RateInterface call(double t, StateInterface y)
    {
        State stateInfo = (State)y;                                      //Cast y into State object to access information

        /*1.Calculate resultant sum acceleration */
        ArrayList<Vector3d> cv = newtonGravity(y, masses);
        if(DEBUG)                                                       //Display
        {
            System.out.println("Acceleration vectors");
            System.out.println(cv.get(0).toString());
            System.out.println(cv.get(1).toString());
            System.out.println("------");
        }

        /*2. Calculate the resultant change in position*/
        ArrayList<Vector3d> cp = new ArrayList<Vector3d>();
        for(int i=0; i< stateInfo.position.size(); i++)
        {
            cp.add(stateInfo.velocity.get(i));
        }
        if(DEBUG)                                                       //Display
        {
        	System.out.println(cp.get(0).toString());
        	System.out.println(cp.get(1).toString());
        }

        /* 3.Return rateInterface object reflecting these changes*/
        Rate result = new Rate(cv, cp);
        if(DEBUG) System.out.println(result.toString());
        return result;
    }

    /**
     * newtonGravity method calculates Celestial Body acceleration as a result of gravitational relations between all Celestial bodies
     * @param y The state of the current system.
     * @param masses Array containing all masses of the Celestial Bodies in the system
     * @return An array containing resultant acceleration of all Celestial bodies in the system
    */
    public ArrayList<Vector3d> newtonGravity(StateInterface y, double[] masses)
    {
        /*Initial*/
        State info = (State)y;                                                                          //Cast into State object to access information                   
        ArrayList<Vector3d> changeInVelocity = new ArrayList<Vector3d>();                               //Initialize array to return. Same quanait of CB

        /* Calculations*/
        for(int i=0; i<masses.length; i++)                                                              //Iterate through each planet
        {
            double planetMass = masses[i];                                                              //Current planetary info
            Vector3d currentPos = info.position.get(i);
            Vector3d accelerationSum = new Vector3d(0,0,0);                                             //Initialize sum vector for acceleration

            for(int j=0; j< info.velocity.size(); j++)                                                  //Iterate through all other planets                         
            {
                if(i != j)                                                                              //Not current selected one
                {
                    double otherMass = masses[j];                                                       //Other CB info, that is exerting force on current planet
                    Vector3d otherPos = info.position.get(j);

                    /*Distance calc*/
                    double r = otherPos.dist(currentPos);                                               //Calculate the distance between the two planets
                    if(DEBUG) System.out.println("Distance: "+ r);

                    /*Unit vector calc*/
                    Vector3d rHat = (otherPos.sub(currentPos)).unitVector();
                    if(DEBUG) System.out.println("Unit vector: "+ rHat.toString());

                    /*Scaler quantity calc */
                    double quantity = (Universe.gConstant*planetMass*otherMass)/ Math.pow(r,2);
                    if(DEBUG) System.out.println("Scaler quantity: "+ quantity);

                    /*Gravitational force calc */
                    Vector3d gravitationalForce = rHat.mul(quantity);                                   //Direction vector multiplied by non-vector quantity
                    if(DEBUG) System.out.println("gravitationalForce "+ gravitationalForce.toString());

                    /*Resultant acceleration calc*/
                    Vector3d resAcceleration = gravitationalForce.mul(1.0/planetMass);                  //a = F/mass 
                    if(DEBUG) System.out.println("resultant acceleration: "+ resAcceleration.toString());

                    /*Summation*/
                    accelerationSum  = accelerationSum.add(resAcceleration);
                }
            }
            changeInVelocity.add(i, accelerationSum);                                                   //Record accelerationSum / change in velocity
        }
        /*Output*/
        return changeInVelocity;
    }
}
