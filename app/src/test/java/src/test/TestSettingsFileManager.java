package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3dInterface;
import src.peng.Vector3d;

class TestSettingsFileManager 
{		
	@Test
	void testLoadStartDateTime() 
	{
		SimulationSettings settings = loadSettings();
		LocalTime time = LocalTime.parse("00:00:00");
		LocalDate date = LocalDate.parse("2020-04-01");
		LocalDateTime startDateTime = LocalDateTime.of(date, time);	

		assertEquals(startDateTime, settings.startTime);
	}
	
	@Test
	void testLoadEndDateTime() 
	{
		SimulationSettings settings = loadSettings();
		LocalTime time = LocalTime.parse("00:00:00");
		LocalDate date = LocalDate.parse("2021-04-01");
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

	@Test
	void loadProbePosition(){
                SimulationSettings settings = loadSettings();
                assertEquals(new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), settings.probeStartPosition);
	}

	@Test
	void loadProbeVelocity(){
                SimulationSettings settings = loadSettings();
                assertEquals(new Vector3d(21240.816883792304, -57621.75558355437, -268.76563663220065), settings.probeStartVelocity);
	}

	@Test
	void loadCelestialBodyLocation(){
                SimulationSettings settings = loadSettings();
                assertEquals(new Vector3d(6.047855986424127e+06,-6.801800047868888e+10,-5.702742359714534e+09), settings.celestialBodies[1].location);
	}

	@Test
	void loadCelestialBodyVelocity(){
                SimulationSettings settings = loadSettings();
                assertEquals(new Vector3d(3.892585189044652e+04, 2.978342247012996e+03,-3.327964151414740e+03), settings.celestialBodies[1].velocity);
	}

	@Test
	void loadCelestialBodyMass(){
                SimulationSettings settings = loadSettings();
                assertEquals(3.302e23, settings.celestialBodies[1].mass);
        }

        @Test
	void loadCelestialBodyRadius(){
                SimulationSettings settings = loadSettings();
                assertEquals(2440e3, settings.celestialBodies[1].radius);
        }
	
	@Test
	void loadCelestialBodyImage(){
                SimulationSettings settings = loadSettings();
                assertEquals("/src/main/java/src/misc/mercuryScaled.png", settings.celestialBodies[1].image);
        }

        @Test
	void loadCelestialBodyIcon(){
                SimulationSettings settings = loadSettings();
                assertEquals("mercuryIcon.png", settings.celestialBodies[1].icon);
        }

        @Test
	void loadCelestialBodyTime(){
                SimulationSettings settings = loadSettings();
                LocalTime cbTime = LocalTime.parse("00:00:00");
		LocalDate cbDate = LocalDate.parse("2021-04-01");
		LocalDateTime cbDateTime = LocalDateTime.of(cbDate, cbTime);
                assertEquals(cbDateTime, settings.celestialBodies[1].time);
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
