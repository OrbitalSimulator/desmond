package src.univ;

import java.util.ArrayList;

import src.conf.LanderSettings;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector2d;

public class LandingModule {
	
	public double width;
	public double length;
	public double fuel;
	public Vector2d initialPos;
	public Vector2d initialVelo;
	public CelestialBody body;
	public double direction;
	
	public LandingModule(double width, double length, double fuel, Vector2d initialPos, Vector2d initialVelo, double direction) {
		this.fuel = fuel;
		this.length = length;
		this.width = width;
		this.direction = direction;
		this.initialPos = initialPos;
		this.initialVelo = initialVelo; 
	}
	
	public LandingModule(double width, double length, double fuel, Vector2d initialPos, Vector2d initialVelo) {
		this.fuel = fuel;
		this.length = length;
		this.width = width;
		this.direction = 0;
		this.initialPos = initialPos;
		this.initialVelo = initialVelo; 
	}
	
	public boolean fuelEmpty() {
		if (this.fuel <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setInitialPosition(Vector2d initialPos) {
		this.initialPos = initialPos;
	}
	
	public void setInitialVelocity(Vector2d initialVelo) {
		this.initialVelo = initialVelo;
	}
	
	public double getDirection() {
		return this.direction;
	}
	
	public double getFuel() {
		return this.fuel;
	}
}
