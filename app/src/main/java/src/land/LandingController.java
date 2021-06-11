package src.land;

import src.peng.Vector2d;
import src.univ.CelestialBody;

public class LandingController {
	
	public LandingController() {
		
	}
	public void thrustAt(LanderSettings setting, CelestialBody target, double height, Vector2d thrust) {
		double probeHeight = setting.probeCurrentPosition.getY();
		double targetHeight = target.location.getY();
		double currentHeight = probeHeight - targetHeight;
		if (currentHeight == height) {
			setting.setProbeVelocity(setting.probeCurrentVelocity.add(thrust));
		}
	}
}
