package src.peng;

import java.util.ArrayList;

import src.conf.LanderSettings;

public class FreeFallFunction implements ODEFunctionInterface {
	
	private LanderSettings settings;
	public static final double GRAVITY = 6.67430e-11;

	public FreeFallFunction(LanderSettings settings) {
		this.settings = settings;
	}

	 /** TO BE ADAPTED
	  * 
     * Method is utilized to calculate yPrime, the derivative of the state y.
     * @param t The time at which to evaluate
     * @param y The current state at which to evaluate
     * @return The average rate of change of the state over time
     */
	public RateInterface call(double t, StateInterface y)
    {
        State stateInfo = (State)y;                                      //Cast y into State object to access information

        /*1.Calculate resultant sum acceleration by gravity*/
        ArrayList<Vector2d> cvG = newtonGravity(y, settings.celestialBody.mass);
        
        /*2. Calculate the resultant change in position by gravity*/
        ArrayList<Vector2d> cp = new ArrayList<Vector2d>();
        for(int i=0; i< stateInfo.position.size(); i++)
        {
            cp.add(stateInfo.velocity.get(i));
        }
        
        /*3. Calculate the total resultant acceleration by drag and gravity*/
        ArrayList<Vector2d> cvT = drag(y, settings.celestialBody.mass);

        /*4. Calculate the total resultant change in position by gravity and drag*/
        ArrayList<Vector2d> cpT = new ArrayList<Vector2d>();
        for(int i=0; i< stateInfo.position.size(); i++)
        {
            cpT.add(stateInfo.velocity.get(i));
        }
        
        /* 5.Return rateInterface object reflecting these changes*/
        return new Rate(cvT, cpT);
    }
	
	/**
     * newtonGravity method calculates Celestial Body acceleration as a result of gravitational relations between all Celestial bodies
     * @param y The state of the current system.
     * @param masses Array containing all masses of the Celestial Bodies in the system
     * @return An array containing resultant acceleration of all Celestial bodies in the system
    */
    public ArrayList<Vector2d> newtonGravity(ArrayList<Vector2d> velo, ArrayList<Vector2d> posi, double planetMass, LanderSettings setting)
    {
        /*Initial*/
    																			//Cast into State object to access information                   
        ArrayList<Vector2d> changeInVelocity = new ArrayList<Vector2d>();                               //Initialize array to return. Same quanait of CB

        /* Calculations*/
        Vector2d currentPos = posi.get(0);
        Vector2d accelerationSum = new Vector2d(0,0);                                             //Initialize sum vector for acceleration

        for(int j=0; j< velo.size(); j++)                                                  //Iterate through all other planets                         
        {
               
             double otherMass = planetMass;                                                       //Other CB info, that is exerting force on current planet
             Vector2d otherPos = setting.celestialBody.location.positionConvert2d(); //******************IMPORTANT

             /*Distance calc*/
             double r = otherPos.dist(currentPos);                                               //Calculate the distance between the two planets

             /*Unit vector calc*/
             Vector2d rHat = (otherPos.sub(currentPos)).unitVector();

             /*Scaler quantity calc */
             double quantity = (GRAVITY*planetMass*otherMass)/ Math.pow(r,2);

             /*Gravitational force calc */
             Vector2d gravitationalForce = rHat.mul(quantity);                                   //Direction vector multiplied by non-vector quantity

             /*Resultant acceleration calc*/
             Vector2d resAcceleration = gravitationalForce.mul(1.0/planetMass);                  //a = F/mass 

             /*Summation*/
             accelerationSum  = accelerationSum.add(resAcceleration);
                
            }
            changeInVelocity.add(accelerationSum);                                                   //Record accelerationSum / change in velocity
        
        /*Output*/
        return changeInVelocity;
    }
    
    /**
     * newtonGravity method calculates Celestial Body acceleration as a result of gravitational relations between all Celestial bodies
     * @param y The state of the current system.
     * @param masses Array containing all masses of the Celestial Bodies in the system
     * @return An array containing resultant acceleration of all Celestial bodies in the system
    */
    public ArrayList<Vector2d> drag(ArrayList<Vector2d> velo, ArrayList<Vector2d> posi, double planetMass, LanderSettings setting)
    {
    	
    	
    }
    
    
}
