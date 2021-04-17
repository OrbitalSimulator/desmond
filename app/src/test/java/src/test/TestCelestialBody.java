package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.Coordinate;
import src.univ.DTG;

/**
 * Group 22
 * Test class for CelestialBody
 * @author L.Debnath
 * @date 14 Mar 21
 */
class TestCelestialBody 
{	
	@Test
	void testCollision() 
	{
		// Arrange (set up everything for your test)
		double radius = 100;											// make a new celestial body	
		Vector3d centre = new Vector3d();								// with a radius 50 and	a centre of 0,0,0
		CelestialBody cb = new CelestialBody(centre, centre, 0, radius, null, null, null, new DTG());
		
		Coordinate[] input = new Coordinate[10];
		for(int i = 0; i < input.length; i++)
		{
			input[i] = new Coordinate(10*i,10*i,10*i, new DTG());			// Build a list of input values (coordinates go up in sets of 10
		}
		boolean[] exp = {true, true, true, true, true, 
						true, false, false, false, false};					// Set up expected results (value change taken from symbolab)
		boolean[] act = new boolean[exp.length];							// The actual values array
		
		
		// 	Act (Do the test)
		for(int i = 0; i < input.length; i++)
		{
			act[i] = cb.collision(input[i]);
		}
		
		
		// Assert (compare the expected and actual results)
		for(int i = 0; i < exp.length; i++)
		{
			assertEquals(exp[i], act[i]);
		}
	}

}
