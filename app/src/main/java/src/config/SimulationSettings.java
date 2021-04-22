package src.config;

import java.time.LocalDateTime;
import src.peng.Vector3dInterface;
import src.univ.CelestialBody;

public class SimulationSettings 
{
	public CelestialBody[] celestialBodies;
	public Vector3dInterface probeStartPosition;
	public Vector3dInterface probeStartVelocity;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public int noOfSteps;
	public String[] wayPoints;

	public SimulationSettings(CelestialBody[] celestialBodies,
			                  Vector3dInterface probeStartPosition,
							  Vector3dInterface probeStartVelocity,
							  LocalDateTime startTime,
							  LocalDateTime endTime,
							  int noOfSteps,
							  String[] wayPoints)
	{
		this.celestialBodies = celestialBodies;
		this.probeStartPosition = probeStartPosition;
		this.probeStartVelocity = probeStartVelocity;
		this.startTime = startTime;
		this.endTime = endTime;
		this.noOfSteps = noOfSteps;
		this.wayPoints = wayPoints;
	}
}
