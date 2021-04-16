package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.peng.Vector3d;

public class TestVector 
{
    Vector3d a = new Vector3d(2,5,-4);
    Vector3d b = new Vector3d(-2,-3,-5);
	
	// Eucl norm test (Vector length) test
	void testEuclNorm()
    {       
        assertEquals(6.708203932499369,a.norm());
    }
    
	// Add two vectors
	void testAddVector()
    {
        Vector3d q = a.add(b);
        assertEquals(0, q.getX());
        assertEquals(2, q.getY());
        assertEquals(-9, q.getZ());
    }
	
	// Subtract two vectors
	void testSubtractVector()
    {
        Vector3d q = a.sub(b);
        assertEquals(4, q.getX());
        assertEquals(8, q.getY());
        assertEquals(1, q.getZ());
    }
	
	// Dot product test
	void testDotProduct()
    {
		assertEquals(1,a.dotProduct(b));
    }
	
	// Unit vector test
	void testUnitVector()
    {
		 Vector3d q = a.unitVector();
	     assertEquals(0.29814239699997197, q.getX());
	     assertEquals(0.7453559924999299, q.getY());
	     assertEquals(-0.5962847939999439, q.getZ());
    }
	
	// Scalar multiple test
	void testScalarMultiple()
    {
		 Vector3d q = a.mul(5);
		 assertEquals(10, q.getX());
	     assertEquals(25, q.getY());
	     assertEquals(-20, q.getZ());
    }
	
    // Distance between two vectors test
	void testDistance()
    {
		assertEquals(9, a.dist(b));
    }
    
	// Add a multiple of another vector test
	void testAddMul()
    {
		Vector3d q = a.addMul(5, b);
		 assertEquals(8, q.getX());
	     assertEquals(22, q.getY());
	     assertEquals(-25, q.getZ());
    }

	//TODO (Travis Dawson) Test vector equality method.
	@Test
	void testEquals()
	{
		/*Initialize*/
		Vector3d trial1 = new Vector3d(1, 2, 3);
		Vector3d trial2 = new Vector3d(1, 2, 3);

		/*Test*/
		assertEquals(true, trial1.equals(trial2));

	}
}
