package src.peng;

import java.util.ArrayList;

import src.land.LanderSettings;

public class FreeFallFunction implements ODEFunctionInterface {
	
	private LanderSettings settings;
	public static final double GRAVITY = 6.67430e-11;
	private double r = 147000;
	private double dragCoeff = 0.55;
	private double area = settings.module.length * settings.module.width;

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
        State2d stateInfo = (State2d)y;                                      //Cast y into State object to access information

        /*1.Calculate resultant sum acceleration by gravity*/
        ArrayList<Vector2d> cv = freeFall(stateInfo, settings.module.body.mass);
        
        /*2. Calculate the resultant change in position by gravity*/
        ArrayList<Vector2d> cp = new ArrayList<Vector2d>();
        for(int i=0; i< stateInfo.position.size(); i++)
        {
            cp.add(stateInfo.velocity.get(i));
        }
        
        /* 3.Return rateInterface object reflecting these changes*/
        return new Rate2d(cv, cp);
    }
	
	/**
     * newtonGravity method calculates Celestial Body acceleration as a result of gravitational relations between all Celestial bodies
     * @param y The state of the current system.
     * @param masses Array containing all masses of the Celestial Bodies in the system
     * @return An array containing resultant acceleration of all Celestial bodies in the system
    */
    public ArrayList<Vector2d> freeFall(State2d state, double planetMass)
    {
        /*Initial*/
    	State2d stateInfo = (State2d) state;													//Cast into State object to access information                   
        ArrayList<Vector2d> changeInVelocity = new ArrayList<Vector2d>();                               //Initialize array to return. Same quanait of CB

        /* Calculations*/
        Vector2d currentPos = stateInfo.position.get(0);
        Vector2d accelerationSum = new Vector2d(0,0);                                             //Initialize sum vector for acceleration

        for(int j=0; j< stateInfo.velocity.size(); j++)                                                  //Iterate through all other planets                         
        {
               
             double otherMass = planetMass;                                                       //Other CB info, that is exerting force on current planet
             Vector2d otherPos = stateInfo.position.get(j); 					//******************IMPORTANT

             /*Distance calc*/
             double r = otherPos.dist(currentPos);                                               //Calculate the distance between the two planets

             /*Unit vector calc*/
             Vector2d rHat = (otherPos.sub(settings.cbLocation)).unitVector();
             Vector2d unitVec = (otherPos.sub(settings.cbLocation)).unitVector();
             
             /*Scaler quantity calc */
             double quantity = (GRAVITY*planetMass*otherMass)/ Math.pow(r,2);

             /*Gravitational force calc */
             Vector2d gravitationalForce = rHat.mul(quantity);                                   //Direction vector multiplied by non-vector quantity

            //drag coefficient * area * air density * 0.5
     		double dScale = r*dragCoeff*area*0.5;
     		
     		//vector^2
     		double vSq = stateInfo.velocity.get(j).dotProduct(stateInfo.velocity.get(j)); 		//WRONG, FOLLOW NASA THING MORE CAREFULLY!!!!
     		
     		//NASA drag formula
     		double dragFormula = dScale*vSq;							//follow the NASA given formula to get the mathematical force applied by drag
     		
     		//drag force being applied to landing module
     		Vector2d dragForce = unitVec.mul(1/dragFormula);			//convert the given drag force into vector form
     		
     		//resultant force
     		Vector2d resForce = gravitationalForce.sub(dragForce);
     		
     		//resultant acceleration due to force
    		Vector2d resAccel = dragForce.mul(1/settings.module.body.mass);				//a = F/mass
     		
             /*Summation*/
             accelerationSum  = accelerationSum.add(resAccel);
                
            }
            changeInVelocity.add(accelerationSum);                                                   //Record accelerationSum / change in velocity
        
        /*Output*/
        return changeInVelocity;
    }
    

}
