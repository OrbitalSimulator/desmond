package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.peng.Rate;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector3d;

class TestState 
{	
	@Test
	void testAddMulMethod() 
	{
	   //Creating an ArrayList to contain velocity vector
	   ArrayList<Vector3d> v = new ArrayList<Vector3d>();
	   v.add(new Vector3d(1, 2, 3));

	   //Creating an ArrayList to contain position vector
	   ArrayList<Vector3d> pos = new ArrayList<Vector3d>();
	   pos.add(new Vector3d(4, 5, 6));

	   //Creating an ArrayList to contain change in velocity vector
	   ArrayList<Vector3d> cv = new ArrayList<Vector3d>();
	   cv.add(new Vector3d(6, 7, 8));

	   //Creating an ArrayList to contain change in position vector
	   ArrayList<Vector3d> cp = new ArrayList<Vector3d>();
	   cp.add(new Vector3d(4, 5, 6));

	   //Generate state
	   State test = new State(v, pos);
	   System.out.println(test.toString());

	   //Generate rate
	   Rate test2 = new Rate(cv, cp);

	   //Generate result of addMul method
	   StateInterface res = (State)test.addMul(2, test2);

	   //Cast it to State to access information
	   State result = (State)res;
	   System.out.println(result.toString());	

	   //Check velocity assertion
	   assertEquals(new Vector3d(13, 16, 19), result.velocity.get(0));

	   //Check position assertion
	    assertEquals(new Vector3d(12, 15, 18), result.position.get(0));
	}
}
