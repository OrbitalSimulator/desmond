package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.peng.Vector3d;
import src.prob.Probe;

class TestProbe 
{

	@Test void testGetMassWithLander() 
	{
		Probe.getInstance().reset();
		double dryMass = 7.8e4;
		double landerMass = 6e3;
		double fuelMass = 1e5;
		double massWithLander = dryMass + landerMass + fuelMass;
		assertEquals(massWithLander, Probe.getInstance().getMass());
	}
	
	@Test void testGetMassWithoutLander()
	{
		Probe.getInstance().reset();
		double dryMass = 7.8e4;
		double fuelMass = 1e5;
		double massWithLander = dryMass + fuelMass;
		Probe.getInstance().releaseLander();
		assertEquals(massWithLander, Probe.getInstance().getMass());
	}
	
	@Test void testBurn()
	{
		Probe.getInstance().reset();
		Vector3d startVelocity = new Vector3d(0,0,0);
		Vector3d finishVelocity = new Vector3d(1,0,0);
		double timeInSeconds = 1;
		
		double massBeforeBurn = Probe.getInstance().getFuelMass();
		Probe.getInstance().burn(startVelocity, finishVelocity, timeInSeconds);
		double massAfterBurn = Probe.getInstance().getFuelMass();
		
		double deltaMass = massAfterBurn - massBeforeBurn;
		assertEquals(-9.2, deltaMass, 0.1);
	}

}
