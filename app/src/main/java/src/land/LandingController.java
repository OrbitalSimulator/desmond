package src.land;

import src.peng.State2d;
import src.peng.Vector2d;
import src.univ.CelestialBody;

public class LandingController {
	
	public LandingController() {
		
	}
	public void thrustAt(LanderSettings setting, State2d state, double height, Vector2d thrust) {
		double probeHeight = state.position.get(0).getY();					//0 is a placeholder index
		double targetHeight = setting.cbLocation.getY();
		double currentHeight = probeHeight - targetHeight;
		if (currentHeight == height) {
			state.velocity.set(1, (state.velocity.get(0)).add(thrust));		//index is placeholder
		}
	}
}
