package src.land;

import src.peng.Vector2d;

public class LanderObject {

	public double direction;
	public Vector2d position;
	public double time;
	
	public LanderObject(Vector2d position, double direction, double time) {
		this.position = position;
		this.direction = direction;
		this.time = time;
	}
	
	public Vector2d getPosition() {
		return this.position;
	}
	
	public double getDirection() {
		return this.direction;
	}
	
	public double getTime() {
		return this.time;
	}
	
	public boolean equals(Object o)
    {
        LanderObject v = (LanderObject) o;
    	if((v.time == this.time) && (v.direction == this.direction) && (v.position.equals(position)))
        {
            return true;
        }
        return false;
    }
}
