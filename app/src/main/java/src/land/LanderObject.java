package src.land;

import src.peng.Vector3d;

public class LanderObject 
{

	private double angle;
	private Vector3d position;
	
	public LanderObject(Vector3d position, double angle) 
	{
		this.position = position;
		this.angle = angle;
	}
	
	public Vector3d getPosition() 
	{
		return this.position;
	}
	
	public double getAngle() 
	{
		return this.angle;
	}
	
	public boolean equals(Object o)
    {
        LanderObject v = (LanderObject) o;
    	if((v.angle == this.angle) && (v.position.equals(position)))
        {
            return true;
        }
        return false;
    }
	
	public LanderObject rotate(double angleChange, double direction) {
		double newAngle = this.angle;
		if (direction == 0) {
			newAngle = newAngle + angleChange;
		}
		else{
			newAngle = newAngle - angleChange;
		}
		return new LanderObject(this.position,newAngle);
	}
}
