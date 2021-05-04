package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import src.config.SettingsFileManager;
import src.config.SimulationSettings;

class TestSettingsFileManager 
{
	LocalTime stime = LocalTime.parse("00:00:00");
	LocalDate sdate = LocalDate.parse("2021-04-01");
	LocalDateTime startDateTime = LocalDateTime.of(sdate, stime);
	LocalTime etime = LocalTime.parse("00:00:00");
	LocalDate edate = LocalDate.parse("2022-04-01");
	LocalDateTime endDateTime = LocalDateTime.of(edate, etime);
	
	@Test
	void test() 
	{
		try {
			SimulationSettings settings = SettingsFileManager.load();
			
			assertEquals(startDateTime, settings.startTime);
			assertEquals(endDateTime, settings.endTime);
			assertEquals(100000, settings.noOfSteps);
			assertEquals(500, settings.stepSize);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	

}
