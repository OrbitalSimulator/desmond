package src.conf;

import java.time.LocalDateTime;
import src.peng.Vector3dInterface;
import src.peng.Vector3d;
import src.univ.CelestialBody;

public class SimulationSettings 
{
	public CelestialBody[] celestialBodies;
	public Vector3dInterface probeStartPosition;
	public Vector3dInterface probeStartVelocity;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public int noOfSteps;
	public double stepSize;
	public String[] waypoints;
	public int stepOffset = 0;

	public SimulationSettings(CelestialBody[] celestialBodies,
			                  Vector3dInterface probeStartPosition,
							  Vector3dInterface probeStartVelocity,
							  LocalDateTime startTime,
							  LocalDateTime endTime,
							  int noOfSteps,
							  double stepSize,
							  String[] waypoints)
	{
		this.celestialBodies = celestialBodies;
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
		probeStartVelocity = new Vector3d(velocityX, 0, 0);
	}
	
	public SimulationSettings copy()
	{
		return new SimulationSettings(celestialBodies,
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
