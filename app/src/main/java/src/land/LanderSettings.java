package src.land;

import java.time.LocalDateTime;

import src.peng.Vector2d;
import src.univ.CelestialBody;

public class LanderSettings 
{
	public Vector2d cbLocation;
	public LandingModule module;
	public int noOfSteps;
	public double stepSize;
	public int stepOffset = 0;

	public LanderSettings(	  Vector2d cbLocation,
							  LandingModule module, 
							  int noOfSteps,
							  double stepSize)
	{
		this.cbLocation = cbLocation;
		this.module = module;
		this.noOfSteps = noOfSteps;
		this.stepSize = stepSize;
	}

	public void setStartVelocity(double velocityX)
	{
		module.initialVelo = new Vector2d(velocityX, 0);
	}
	
	public LanderSettings copy()
	{
		return new LanderSettings(cbLocation,
				    module,
				    noOfSteps,
				    stepSize);
	}
	
	public int getStartStep()
	{
		return stepOffset;
	}
	
	public int getEndStep()
	{
		return noOfSteps + stepOffset;
	}
}
