package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import src.data.DataFileManager;
import src.data.DataFileReference;
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
		CelestialBody[] exp = new CelestialBody[2];
		exp[0] = new CelestialBody(new Vector3d(1,1,1), new Vector3d(2,2,2), 100, 100, "Test", "/image/path", "icon/path", LocalDateTime.of(d1, t1));
		exp[1] = new CelestialBody(new Vector3d(2,2,2), new Vector3d(3,3,3), 100, 100, "Test", "/image/path", "icon/path", LocalDateTime.of(d2, t2));
		
		DataFileManager manager = new DataFileManager();
		manager.overwrite(exp);
		
		DataFileReference ref = new DataFileReference(exp);
		CelestialBody[] act = new CelestialBody[exp.length];
		try {
			act = manager.load(ref);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < exp.length; i++)
		{
			assertEquals(exp[i].location.getX(), act[i].location.getX());
			assertEquals(exp[i].location.getY(), act[i].location.getY());
			assertEquals(exp[i].location.getZ(), act[i].location.getZ());
			assertEquals(exp[i].velocity.getX(), act[i].velocity.getX());
			assertEquals(exp[i].velocity.getY(), act[i].velocity.getY());
			assertEquals(exp[i].velocity.getZ(), act[i].velocity.getZ());
		}
	}
}
