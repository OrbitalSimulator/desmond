package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.prob.Probe;

class TestProbe 
{

	@Test
	void testGetMassWithLander() 
	{
		double dryMass = 7.8e4;
		double landerMass = 6e3;
		double fuelMass = 1e5;
		double massWithLander = dryMass + landerMass + fuelMass;
		assertEquals(massWithLander, Probe.getInstance().getMass());
	}
	
	@Test
	void testGetMassWithoutLander()
	{
		double dryMass = 7.8e4;
		double fuelMass = 1e5;
		double massWithLander = dryMass + fuelMass;
		Probe.getInstance().releaseLander();
		assertEquals(massWithLander, Probe.getInstance().getMass());
	}

}
