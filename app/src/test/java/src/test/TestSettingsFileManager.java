package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import src.conf.SettingsFileManager;
import src.conf.SimulationSettings;
import src.peng.Vector3dInterface;
import src.peng.Vector3d;

class TestSettingsFileManager 
{
	LocalTime stime = LocalTime.parse("00:00:00");
	LocalDate sdate = LocalDate.parse("2021-04-01");
	LocalDateTime startDateTime = LocalDateTime.of(sdate, stime);
	LocalTime etime = LocalTime.parse("00:00:00");
	LocalDate edate = LocalDate.parse("2022-04-01");
	LocalDateTime endDateTime = LocalDateTime.of(edate, etime);
	Vector3dInterface pos = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	Vector3dInterface vel = new Vector3d(21240.816883792304, -57621.75558355437, -268.76563663220065);
	Vector3d location = new Vector3d(6.047855986424127e+06,-6.801800047868888e+10,-5.702742359714534e+09);
	Vector3d velocity = new Vector3d(3.892585189044652e+04, 2.978342247012996e+03,-3.327964151414740e+03);
	double mass = 3.302e23;
	double radius = 2440e3;
	String image = "/src/main/java/src/misc/mercuryScaled.png";
	String icon = "/src/main/java/src/misc/mercuryIcon.png";
	LocalTime cbTime = LocalTime.parse("00:00:00");
	LocalDate cbDate = LocalDate.parse("2021-04-01");
	LocalDateTime cbDateTime = LocalDateTime.of(cbDate, cbTime);
	
	@Test
	void test() 
	{
		try {
			SimulationSettings settings = SettingsFileManager.load();
			
			assertEquals(startDateTime, settings.startTime);
			assertEquals(endDateTime, settings.endTime);
			assertEquals(11712, settings.noOfSteps);
			assertEquals(2700, settings.stepSize);
			assertEquals(pos, settings.probeStartPosition);
			assertEquals(vel, settings.probeStartVelocity);
			assertEquals(location, settings.celestialBodies[1].location);
			assertEquals(velocity, settings.celestialBodies[1].velocity);
			assertEquals(mass, settings.celestialBodies[1].mass);
			assertEquals(radius, settings.celestialBodies[1].radius);
			assertEquals(image, settings.celestialBodies[1].image);
			assertEquals(icon, settings.celestialBodies[1].icon);
			assertEquals(cbDateTime, settings.celestialBodies[1].time);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
