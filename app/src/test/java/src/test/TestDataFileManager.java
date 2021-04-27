package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import src.config.DataFileManager;
import src.config.SimulationSettings;
import src.peng.Vector3d;
import src.univ.CelestialBody;

class TestDataFileManager 
{	
	LocalTime t1 = LocalTime.parse("00:01:00");
	LocalDate d1 = LocalDate.parse("2021-04-01");
	LocalTime t2 = LocalTime.parse("00:02:00");
	LocalDate d2 = LocalDate.parse("2021-04-01");

	
	@Test
	void testSaveAndLoadFile()
	{
		//CelestialBody[][] exp = new CelestialBody[2][2];
		//exp[0][0] = new CelestialBody(new Vector3d(1,1,1), new Vector3d(2,2,2), 100, 100, "Test", "/image/path", "icon/path", LocalDateTime.of(d1, t1));
		//exp[0][1] = new CelestialBody(new Vector3d(1,1,1), new Vector3d(2,2,2), 100, 100, "Test", "/image/path", "icon/path", LocalDateTime.of(d1, t1));
		//exp[1][0] = new CelestialBody(new Vector3d(2,2,2), new Vector3d(3,3,3), 100, 100, "Test", "/image/path", "icon/path", LocalDateTime.of(d2, t2));
		//exp[1][1] = new CelestialBody(new Vector3d(2,2,2), new Vector3d(3,3,3), 100, 100, "Test", "/image/path", "icon/path", LocalDateTime.of(d2, t2));
		//
		//DataFileManager.overwrite(exp);
		//
		//SimulationSettings settings = new SimulationSettings(exp[0], 
		//													 new Vector3d(1,1,1),
		//													 new Vector3d(1,1,1),
		//													 LocalDateTime.of(d1, t1), 
		//													 LocalDateTime.of(d2, t2), 
		//													 2, 
		//													 null);
		//CelestialBody[][] act = new CelestialBody[exp.length][exp[0].length];
		//try {
		//	act = DataFileManager.load(settings);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		//
		//for(int i = 0; i < exp.length; i++)
		//{
		//	assertEquals(exp[i][0].location.getX(), act[i][0].location.getX());
		//	assertEquals(exp[i][0].location.getY(), act[i][0].location.getY());
		//	assertEquals(exp[i][0].location.getZ(), act[i][0].location.getZ());
		//	assertEquals(exp[i][0].velocity.getX(), act[i][0].velocity.getX());
		//	assertEquals(exp[i][0].velocity.getY(), act[i][0].velocity.getY());
		//	assertEquals(exp[i][0].velocity.getZ(), act[i][0].velocity.getZ());
		//}
	}
}
