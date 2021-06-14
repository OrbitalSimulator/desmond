package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import src.land.LanderObject;
import src.peng.Vector2d;

public class TestLanderObject {

	@Test
	void testEquals() {
		
		Vector2d posi = new Vector2d(10,10);
		double time = 20;
		double direction = 180;
		LanderObject lander = new LanderObject(posi,time,direction);
		LanderObject lander2 = new LanderObject(posi,time,direction);
		
		assert(lander.equals(lander2));
	}
	
	@Test
	void testGetTime() {
		Vector2d posi = new Vector2d(10,10);
		double time = 20;
		double direction = 180;
		LanderObject lander = new LanderObject(posi,time,direction);
		
		assertEquals(lander.time,20);
	}
	
	@Test
	void testGetPosition() {
		Vector2d posi = new Vector2d(10,10);
		double time = 20;
		double direction = 180;
		LanderObject lander = new LanderObject(posi,time,direction);
		
		assertEquals(lander.position,new Vector2d(10,10));
	}
	
	@Test
	void testGetDirection() {
		Vector2d posi = new Vector2d(10,10);
		double time = 20;
		double direction = 180;
		LanderObject lander = new LanderObject(posi,time,direction);
		
		assertEquals(lander.direction,180);
	}
}
