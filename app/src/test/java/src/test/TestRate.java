package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.peng.Rate;
import src.peng.Vector3d;

class TestRate 
{
	@Test
	void testAverage() 
	{
		// Arrange
		// Create the state for A
		ArrayList<Vector3d> velocityA = new ArrayList<Vector3d>();
		velocityA.add(new Vector3d(2, 4, 10));
		ArrayList<Vector3d> positionA = new ArrayList<Vector3d>();
		positionA.add(new Vector3d(2, 4, 10));
		Rate A = new Rate(velocityA, positionA);
		
		// Create the state for B
		ArrayList<Vector3d> velocityB = new ArrayList<Vector3d>();
		velocityB.add(new Vector3d(0, 2, 10));
		ArrayList<Vector3d> positionB = new ArrayList<Vector3d>();
		positionB.add(new Vector3d(0, 2, 10));
		Rate B = new Rate(velocityB, positionB);
		
		// Act
		Rate ave = A.average(B);
		
		// Assert
		assertEquals(ave.velocityChange.get(0).getX(), 1);
		assertEquals(ave.velocityChange.get(0).getY(), 3);	
		assertEquals(ave.velocityChange.get(0).getZ(), 10);	
		assertEquals(ave.positionChange.get(0).getX(), 1);
		assertEquals(ave.positionChange.get(0).getY(), 3);
		assertEquals(ave.positionChange.get(0).getZ(), 10);
	}
}
