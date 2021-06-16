package src.land;

import java.util.ArrayList;

import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector2d;
import src.univ.CelestialBody;

public class LandingModule {
	
	public double width;
	public double length;
	public double fuel;
	public double mass;
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