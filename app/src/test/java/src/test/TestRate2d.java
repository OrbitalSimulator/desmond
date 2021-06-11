package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.peng.Rate2d;
import src.peng.Vector2d;

class TestRate2d 
{
	@Test
	void testAverage() 
	{
		// Arrange
		// Create the state for A
		ArrayList<Vector2d> velocityA = new ArrayList<Vector2d>();
		velocityA.add(new Vector2d(2, 4));
		ArrayList<Vector2d> positionA = new ArrayList<Vector2d>();
		positionA.add(new Vector2d(2, 4));
		Rate2d A = new Rate2d(velocityA, positionA);
		
		// Create the state for B
		ArrayList<Vector2d> velocityB = new ArrayList<Vector2d>();
		velocityB.add(new Vector2d(0, 2));
		ArrayList<Vector2d> positionB = new ArrayList<Vector2d>();
		positionB.add(new Vector2d(0, 2));
		Rate2d B = new Rate2d(velocityB, positionB);
		
		// Act
		Rate2d ave = A.average(B);
		
		// Assert
		assertEquals(ave.velocityChange.get(0).getX(), 1);
		assertEquals(ave.velocityChange.get(0).getY(), 3);	
		assertEquals(ave.positionChange.get(0).getX(), 1);
		assertEquals(ave.positionChange.get(0).getY(), 3);
	}
}
