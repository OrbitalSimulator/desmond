package src.land;


public class LanderSettings 
{
	public LandingModule module;
	public int noOfSteps;
	public double stepSize;
	public int stepOffset = 0;

	public LanderSettings(LandingModule module, int noOfSteps, double stepSize)
	{
		this.module = module;
		this.noOfSteps = noOfSteps;
		this.stepSize = stepSize;
	}

	public LanderSettings copy()
	{
		return new LanderSettings(
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
