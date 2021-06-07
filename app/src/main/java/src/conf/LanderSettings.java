package src.conf;

import java.time.LocalDateTime;
import src.peng.Vector2d;
import src.univ.CelestialBody;

public class LanderSettings 
{
	public CelestialBody celestialBody;	//only one body which we need to compare to, rest is negligible
	public Vector2d probeStartPosition;
	public Vector2d probeStartVelocity;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public int noOfSteps;
	public double stepSize;
	public String[] waypoints;
	public int stepOffset = 0;

	public LanderSettings(CelestialBody celestialBody,
			                  Vector2d probeStartPosition,
							  Vector2d probeStartVelocity,
							  LocalDateTime startTime,
							  LocalDateTime endTime,
							  int noOfSteps,
							  double stepSize,
							  String[] waypoints)
	{
		this.celestialBody = celestialBody;
		this.probeStartPosition = probeStartPosition;
		this.probeStartVelocity = probeStartVelocity;
		this.startTime = startTime;
		this.endTime = endTime;
		this.noOfSteps = noOfSteps;
		this.stepSize = stepSize;
		this.waypoints = waypoints;
	}

	public void setStartVelocity(double velocityX)
	{
		probeStartVelocity = new Vector2d(velocityX, 0);
	}
	
	public LanderSettings copy()
	{
		return new LanderSettings(celestialBody,
                	probeStartPosition,
				    probeStartVelocity,
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
