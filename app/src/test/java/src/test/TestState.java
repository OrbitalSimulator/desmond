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
	   ArrayList<Vector3d> velocities = new ArrayList<Vector3d>();
	   velocities.add(new Vector3d(1, 2, 3));

	   ArrayList<Vector3d> positions = new ArrayList<Vector3d>();
	   positions.add(new Vector3d(4, 5, 6));

	   ArrayList<Vector3d> deltaVelocities = new ArrayList<Vector3d>();
	   deltaVelocities.add(new Vector3d(6, 7, 8));

	   ArrayList<Vector3d> deltaPositions = new ArrayList<Vector3d>();
	   deltaPositions.add(new Vector3d(4, 5, 6));

	   State test = new State(velocities, positions);
	   Rate test2 = new Rate(deltaVelocities, deltaPositions);
	   StateInterface res = (State)test.addMul(2, test2);

	   State result = (State)res;
	   System.out.println(result.toString());	

	   assertEquals(new Vector3d(13, 16, 19), result.velocity.get(0));
	   assertEquals(new Vector3d(12, 15, 18), result.position.get(0));
	}
}
