package src.univ;

import java.util.ArrayList;

import src.conf.LanderSettings;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector2d;

public class LandingModule {
	
	private double width;
	private double length;
	private double fuel;
	private Vector2d initialPos;
	private Vector2d initialVelo;
	private CelestialBody body;
	
	public LandingModule(double width, double length, double fuel, LanderSettings setting) {
		this.fuel = fuel;
		this.length = length;
		this.width = width;
		this.initialPos = setting.probeStartPosition;
		this.initialVelo = setting.probeStartVelocity; 
	}
	
	public ArrayList<Vector2d> landerFreeFall() {
		
		return null;
	}
	
	public ArrayList<Vector2d> dragInfluence() {
		
		return null;
	}
	
	public boolean fuelEmpty() {
		if (this.fuel <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
