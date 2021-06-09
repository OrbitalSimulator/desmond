package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.peng.Rate2d;
import src.peng.State2d;
import src.peng.StateInterface;
import src.peng.Vector2d;

class TestState2d
{	
	@Test
	void testAddMulMethod() 
	{
	   //Creating an ArrayList to contain velocity vector
	   ArrayList<Vector2d> v = new ArrayList<Vector2d>();
	   v.add(new Vector2d(1, 2));

	   //Creating an ArrayList to contain position vector
	   ArrayList<Vector2d> pos = new ArrayList<Vector2d>();
	   pos.add(new Vector2d(4, 5));

	   //Creating an ArrayList to contain change in velocity vector
	   ArrayList<Vector2d> cv = new ArrayList<Vector2d>();
	   cv.add(new Vector2d(6, 7));

	   //Creating an ArrayList to contain change in position vector
	   ArrayList<Vector2d> cp = new ArrayList<Vector2d>();
	   cp.add(new Vector2d(4, 5));

	   //Generate state
	   State2d test = new State2d(v, pos);
	   System.out.println(test.toString());

	   //Generate rate
	   Rate2d test2 = new Rate2d(cv, cp);

	   //Generate result of addMul method
	   StateInterface res = (State2d)test.addMul(2, test2);

	   //Cast it to State to access information
	   State2d result = (State2d)res;
	   System.out.println(result.toString());	

	   //Check velocity assertion
	   assertEquals(new Vector2d(13, 16), result.velocity.get(0));

	   //Check position assertion
	    assertEquals(new Vector2d(12, 15), result.position.get(0));
	}
}
