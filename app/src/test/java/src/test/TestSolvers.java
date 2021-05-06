package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.univ.Universe;
import src.peng.State;
import src.peng.Vector3d;

class TestSolvers 
{
	static final boolean SAVE_TO_FILE = false;
	
	static final double STEP_DAY = 86400;
	static final double STEP_HOUR = 3600;
	static final double STEP_MIN = 60;
	static final double STEP_SEC = 1;
	
	static final double EARTH_ONE_DAY_X = -1.467016284491896E11;
	static final double EARTH_ONE_DAY_Y = -3.113774419524897E10;
	static final double EARTH_ONE_DAY_Z =  8.336289095385000E06;
	
	static final double EARTH_ONE_YEAR_X = -1.477128501379751E11;
	static final double EARTH_ONE_YEAR_Y = -2.821059740389257E10;
	static final double EARTH_ONE_YEAR_Z =  2.031783034570143E07;
	
    static final double DAY_ACCURACY = 725; 
    static final double YEAR_ACCURACY = 5.733198703E09; 
    
	
	@Test void testOneDay() 
	{	
		State finalState = setupOneDayTest();
		double x = finalState.position.get(3).getX();
		double y = finalState.position.get(3).getY();
		double z = finalState.position.get(3).getZ();
		assertEquals(EARTH_ONE_DAY_X, x, DAY_ACCURACY);
		assertEquals(EARTH_ONE_DAY_Y, y, DAY_ACCURACY);
		assertEquals(EARTH_ONE_DAY_Z, z, DAY_ACCURACY);
	}
	
	@Test void testOneYear() 
	{	
		State finalState = setupOneYearTest();
		double x = finalState.position.get(3).getX();
		double y = finalState.position.get(3).getY();
		double z = finalState.position.get(3).getZ();
		assertEquals(EARTH_ONE_YEAR_X, x, YEAR_ACCURACY);
		assertEquals(EARTH_ONE_YEAR_Y, y, YEAR_ACCURACY);
		assertEquals(EARTH_ONE_YEAR_Z, z, YEAR_ACCURACY);
	}
	

	
	private State setupOneDayTest()
	{
		int dayInSeconds = 60*60*24;
		SimulationSettings settings = generateSettings(STEP_SEC, dayInSeconds);
		Universe universe = new Universe(settings, SAVE_TO_FILE);
		return universe.getStateAt(dayInSeconds);
	}
	
	private State setupOneYearTest()
	{
		int yearInHours = 365*24;
		SimulationSettings settings = generateSettings(STEP_HOUR, yearInHours);
		Universe universe = new Universe(settings, SAVE_TO_FILE);
		return universe.getStateAt(yearInHours);
	}
	
	private SimulationSettings generateSettings(double stepSize, int numberOfSteps)
	{
		try 
		{
			SimulationSettings settings = SettingsFileManager.load();
			settings.noOfSteps = numberOfSteps;
			settings.stepSize = stepSize;
			return settings;
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		return null;
	}
}
