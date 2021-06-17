package src.peng;

import java.util.ArrayList;

import src.land.LanderSettings;
import src.solv.EulerSolver;
import src.solv.ODESolver;

public class NewtonGravity2d implements ODEFunctionInterface {
	
	private LanderSettings settings;
	public static final double GRAVITY = 6.67430e-11;	//6.67430e-11, 1.352
	private double r = 1.5;								//1.5 bar, 147000 Pa
	private double dragCoeff = 0.55;
	private double area;
	public double bodyMass;
	public double width;
	public double length;
	public double mass;

	 public NewtonGravity2d(double bodyMass, double width, double length, double mass) {
		this.bodyMass = bodyMass;
		this.width = width;
		this.length = length;
		this.mass = mass;
	}

	/**
     * Method is utilized to calculate yPrime, the derivative of the state y.
     * @param t The time at which to evaluate
     * @param y The current state at which to evaluate
     * @return The average rate of change of the state over time
     */
	public RateInterface call(double t, StateInterface y)
    {
        State2d stateInfo = (State2d)y;                                      //Cast y into State object to access information

        /*1.Calculate resultant sum acceleration by gravity & drag*/
        ArrayList<Vector2d> cv = freeFall(stateInfo, this.bodyMass);
        
        /*2. Calculate the resultant change in position by gravity & drag*/
        ArrayList<Vector2d> cp = new ArrayList<Vector2d>();
        for(int i=0; i< stateInfo.position.size(); i++)
        {
            cp.add(stateInfo.velocity.get(i));
        }
        
        /* 3.Return rateInterface object reflecting these changes*/
        return new Rate2d(cv, cp);
    }
	
	/**
     * freeFall method calculates acceleration as a result of gravitational relations between celestial body, and the air resistance in the atmosphere
     * @param y The state of the current system.
     * @param mass of the Celestial Body in the system
     * @return An array containing resultant acceleration of all Celestial bodies in the system
    */
    public ArrayList<Vector2d> freeFall(StateInterface state, double planetMass)
    {
        /*Initial*/
    	State2d stateInfo = (State2d) state;													//Cast into State object to access information                   
        ArrayList<Vector2d> changeInVelocity = new ArrayList<Vector2d>();                               //Initialize array to return. Same quanait of CB

        /* Calculations*/
        Vector2d bodyPos = new Vector2d(0,0);
        Vector2d accelerationSum = new Vector2d(0,0);                                             //Initialize sum vector for acceleration
        
        area = length * width;
        
        for(int j=0; j< stateInfo.velocity.size(); j++)                                                  //Iterate through all other planets                         
        {
               
             double otherMass = mass;                                                       //Other CB info, that is exerting force on current planet
             Vector2d landerPos = stateInfo.position.get(j); 					

             /*Distance calc*/
             double dis = landerPos.dist(bodyPos);                                               //Calculate the distance between the two planets
             System.out.println(dis);
             
             /*Unit vector calc*/
             Vector2d rHat = (bodyPos.sub(landerPos)).unitVector();
             Vector2d unitVec = (landerPos.sub(bodyPos)).unitVector();
             
             /*Scaler quantity calc */
             double quantity = (GRAVITY*otherMass*planetMass)/(Math.pow(dis, 2));

             /*Gravitational force calc */
             Vector2d gravitationalForce = rHat.mul(quantity);                                   //Direction vector multiplied by non-vector quantity

             //velo squared
//             double vSq = stateInfo.velocity.get(j).dotProduct(stateInfo.velocity.get(j));
             
            //drag = drag coefficient * area * (air density * 0.5 * velocity^2)					
     		double dScale = dragCoeff*area*((r*(stateInfo.velocity.get(j).dotProduct(stateInfo.velocity.get(j))))/2);
     		//follow the NASA given formula to get the mathematical force applied by drag
     		
     		//drag force being applied to landing module
     		Vector2d dragForce = unitVec.mul(dScale);			//convert the given drag force into vector form
     		
     		//resultant force
     		Vector2d resForce = dragForce.add(gravitationalForce);
     		
     		//resultant acceleration due to force
    		Vector2d resAccel = resForce.mul(1/otherMass);				//a = (W-D)/mass
     		
             /*Summation*/
             accelerationSum  = accelerationSum.add(resAccel);
                
            }
            changeInVelocity.add(accelerationSum);                                                   //Record accelerationSum / change in velocity
        
        /*Output*/
        return changeInVelocity;
    }
    
}
