package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;

class TestSettingsFileManager 
{		
	@Test
	void testLoadStartDateTime() 
	{
		SimulationSettings settings = loadSettings();
		LocalTime time = LocalTime.parse("00:00:00");
		LocalDate date = LocalDate.parse("2021-04-01");
		LocalDateTime startDateTime = LocalDateTime.of(date, time);	

		assertEquals(startDateTime, settings.startTime);
	}
	
	@Test
	void testLoadEndDateTime() 
	{
		SimulationSettings settings = loadSettings();
		LocalTime time = LocalTime.parse("00:00:00");
		LocalDate date = LocalDate.parse("2022-04-01");
		LocalDateTime endDateTime = LocalDateTime.of(date, time);

		assertEquals(endDateTime, settings.endTime);
	}
	
	@Test
	void testLoadSteps() 
	{
		SimulationSettings settings = loadSettings();
		assertEquals(11712, settings.noOfSteps);
	}
	
	@Test
	void testLoadStepSize() 
	{
		SimulationSettings settings = loadSettings();
		assertEquals(2700, settings.stepSize);
	}
	
	private SimulationSettings loadSettings()
	{
		try 
		{
			return SettingsFileManager.load();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		return null;
	}
}
