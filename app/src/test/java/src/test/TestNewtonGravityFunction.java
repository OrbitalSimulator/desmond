package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.peng.NewtonGravityFunction;
import src.peng.Rate;
import src.peng.RateInterface;
import src.peng.State;
import src.peng.StateInterface;
import src.peng.Vector3d;

class TestNewtonGravityFunction 
{
	@Test
	void testNewtonGravityFunc() 
	{	
		/*Setup*/
	    ArrayList<Vector3d> vel = new ArrayList<Vector3d>();															
	    vel.add(new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00,  3.994237625449041e-01));
	    vel.add(new Vector3d( 3.892585189044652e+04,  2.978342247012996e+03, -3.327964151414740e+03));
	    ArrayList<Vector3d> pos = new ArrayList<Vector3d>();															
	    pos.add(new Vector3d( -6.806783239281648e+08,   1.080005533878725e+09,   6.564012751690170e+06));
	    pos.add( new Vector3d(  6.047855986424127e+06,  -6.801800047868888e+10,  -5.702742359714534e+09));

	    StateInterface test = new State(vel, pos);																		
	    System.out.println(test.toString());																			
	    System.out.println("------");

		double[] masses = {1.988500e30, 3.302e23};																		

		/*Calculation*/
	    NewtonGravityFunction testing = new NewtonGravityFunction(masses);
	    RateInterface res = testing.call(1, test);
	    Rate outcome = (Rate)res;
		System.out.println("Outcome:");
		System.out.println(outcome.toString());

		/*Testing*/
	    assertEquals(new Vector3d(4.5401856113667494E-11, -4.568309492895859E-9, -3.7746210063492234E-10), outcome.velocityChange.get(0));
		assertEquals(new Vector3d(-14.20511669610689, -4.954714716629277, 0.3994237625449041), outcome.positionChange.get(0));
	}
}
