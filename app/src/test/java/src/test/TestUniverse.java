package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.univ.Universe;

/**
 * Group 22
 * Test class for Universe package
 * @author L.Debnath
 * @date 14 Mar 21
 */
class TestUniverse 
{	
	@Test void testAppend()
	{
		Universe universe1 = new Universe(generateSettingsForAppendTest());
		Universe universe2 = new Universe(generateSettingsForAppendTest());
		universe1.append(universe2);
		assertEquals(21, universe1.U[0].length);
	}
	
	public SimulationSettings generateSettingsForAppendTest()
	{
		try 
		{		
			SimulationSettings settings;
			settings = SettingsFileManager.load();
			settings.noOfSteps = 10;
			return settings;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}
