package src.land;

import src.conf.Logger;
import src.peng.State;

public class OpenLoopController extends LandingController
{
	private final double PARACHUTE_AREA = 1000;
	private boolean parachuteDeployed = false;	
	private double deployParachuteHeight = 5000;
	private int parachuteState = 0;
	
	public OpenLoopController() 
	{
		super();
		logFileName = "openloop_controller";
	}
	
	@Override
	protected State controllerAction(State currentState, double planetRadius)
	{
		if(!parachuteDeployed && testHeight(currentState, planetRadius + deployParachuteHeight))
			deployParachute();
		return currentState;
	}
	
	protected double getTotalArea()
	{
		double totalArea = 	LANDER_AREA;	
		if(parachuteDeployed)
			totalArea = totalArea + getParachuteState();
		return totalArea;
	}
	
	private void deployParachute()
	{
		parachuteDeployed = true;
		Logger.logCSV(logFileName, "Parachute Deployed!");
	}
			
	protected double getParachuteState()
	{
		if(parachuteState < 100)
			parachuteState = parachuteState + 5;
		
		return (PARACHUTE_AREA/100) * parachuteState;
	}
}
