package src.conf;

import java.time.LocalDateTime;
import src.peng.Vector2d;
import src.univ.CelestialBody;
import src.univ.LandingModule;

public class LanderSettings 
{
	public Vector2d cbLocation;
	public LandingModule module;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public int noOfSteps;
	public double stepSize;
	public String[] waypoints;
	public int stepOffset = 0;

	public LanderSettings(	  Vector2d cbLocation,
							  LandingModule module,
			               	  LocalDateTime startTime,
							  LocalDateTime endTime,
							  int noOfSteps,
							  double stepSize,
							  String[] waypoints)
	{
		this.cbLocation = cbLocation;
		this.module = module;
		this.startTime = startTime;
		this.endTime = endTime;
		this.noOfSteps = noOfSteps;
		this.stepSize = stepSize;
		this.waypoints = waypoints;
	}

	public void setStartVelocity(double velocityX)
	{
		module.initialVelo = new Vector2d(velocityX, 0);
	}
	
	public LanderSettings copy()
	{
		return new LanderSettings(cbLocation,
				    module,
				    startTime,
				    endTime,
				    noOfSteps,
				    stepSize,
				    waypoints);
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
