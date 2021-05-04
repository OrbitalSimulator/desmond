package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import src.peng.Vector3d;
import src.univ.CelestialBody;

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
		LocalTime t1 = LocalTime.parse("00:01");
		LocalDate d1 = LocalDate.parse("2021-04-01");
		
		// Arrange (set up everything for your test)
		double radius = 100;											// make a new celestial body	
		Vector3d centre = new Vector3d();								// with a radius 50 and	a centre of 0,0,0
		CelestialBody cb = new CelestialBody(centre, centre, 0, radius, null, null, null, LocalDateTime.of(d1, t1));
		
		Vector3d[] input = new Vector3d[10];
		for(int i = 0; i < input.length; i++)
		{
			input[i] = new Vector3d(10*i,10*i,10*i);					
		}
		boolean[] exp = {true, true, true, true, true, 
						true, false, false, false, false};				
		boolean[] act = new boolean[exp.length];						
		
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
	
	@Test
	void testTimeInMs_Second()
	{
		LocalTime t1 = LocalTime.parse("00:00:01");
		LocalDate d1 = LocalDate.parse("0001-01-01");
		CelestialBody cb = new CelestialBody(null, null, 0, 0, null, null, null, LocalDateTime.of(d1, t1));
		assertEquals(1000, cb.timeInMs());
	}
	
	@Test
	void testTimeInMs_Min()
	{
		LocalTime t1 = LocalTime.parse("00:50:00");
		LocalDate d1 = LocalDate.parse("0001-01-01");
		CelestialBody cb = new CelestialBody(null, null, 0, 0, null, null, null, LocalDateTime.of(d1, t1));
		assertEquals(3e+6, cb.timeInMs());
	}
	
	@Test
	void testTimeInMs_Hour()
	{
		LocalTime t1 = LocalTime.parse("03:00:00");
		LocalDate d1 = LocalDate.parse("0001-01-01");
		CelestialBody cb = new CelestialBody(null, null, 0, 0, null, null, null, LocalDateTime.of(d1, t1));
		assertEquals(1.08e+7, cb.timeInMs());
	}

}
