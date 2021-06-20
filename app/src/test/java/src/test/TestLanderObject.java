package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import src.land.LanderObject;
import src.peng.Vector3d;

public class TestLanderObject {

	@Test
	void testEquals() {
		
		Vector3d posi = new Vector3d(10,10,0);
		double angle = 180;
		LanderObject lander = new LanderObject(posi,angle);
		LanderObject lander2 = new LanderObject(posi,angle);
		
		assertEquals(true, lander.equals(lander2));
	}
	
	
	@Test
	void testGetPosition() {
		Vector3d posi = new Vector3d(10,10,0);
		double angle = 180;
		LanderObject lander = new LanderObject(posi,angle);
		
		assertEquals(posi, lander.getPosition());
	}
	
	@Test
	void testGetAngle() {
		Vector3d posi = new Vector3d(10,10,0);
		double angle = 180;
		LanderObject lander = new LanderObject(posi,angle);
		
		assertEquals(angle, lander.getAngle());
	}
}
