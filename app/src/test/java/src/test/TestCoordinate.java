
package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.univ.Coordinate;
import src.univ.DTG;

class TestCoordinate
{
	// TODO TestCoordinate full partition testing
	
	@Test
	void test() 
	{
		Coordinate c = new Coordinate(1,1,1, new DTG());
		System.out.println(c.toString());
		c.setX(10);
		c.setY(10);
		c.setY(10);
		System.out.println(c.toString());
	}

}