package src.traj;

import src.peng.Vector3d;
import src.univ.Universe;

public class GuidanceController 
{
	private Universe universe;
	private String target;
	private Vector3d[] trajectory;
	private Vector3d[] corrections;
		
	public GuidanceController(Universe universe, String target) 
	{
		this.universe = universe;
		this.target = target;
	}
	
	public Vector3d[] getTrajectory() 
	{
		return trajectory;
	}
	
	public Vector3d[] getCorrections() 
	{
		return corrections;
	}
}
