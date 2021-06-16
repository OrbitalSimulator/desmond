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
	public Vector2d initialPos;
	public Vector2d initialVelo;
	public double bodyMass;
	public double direction;
	
	public LandingModule(double width, double length, double fuel, double mass, Vector2d initialPos, Vector2d initialVelo, double body,  double direction) {
		this.fuel = fuel;
		this.length = length;
		this.width = width;
		this.mass = mass;
		this.direction = direction;
		this.initialPos = initialPos;
		this.initialVelo = initialVelo; 
		this.bodyMass = body;
	}
	
	public LandingModule(double width, double length, double fuel, double mass, Vector2d initialPos, Vector2d initialVelo, double body) {
		this.fuel = fuel;
		this.length = length;
		this.width = width;
		this.mass = mass;
		this.direction = 0;
		this.initialPos = initialPos;
		this.initialVelo = initialVelo; 
		this.bodyMass = body;
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
