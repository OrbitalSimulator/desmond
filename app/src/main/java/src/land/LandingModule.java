package src.land;

public class LandingModule {

	public double fuel;
	public double direction;
	
	public LandingModule(double fuel, double direction) {
		this.fuel = fuel;
		this.direction = direction;
	}
	
	public LandingModule(double fuel) {
		this.fuel = fuel;
		this.direction = 0;
	}
	
	public boolean fuelEmpty() {
		if (this.fuel <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public double getDirection() {
		return this.direction;
	}
	
	public double getFuel() {
		return this.fuel;
	}
}