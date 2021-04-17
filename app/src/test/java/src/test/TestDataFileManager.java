package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.data.DataFileManager;
import src.data.DataFileReference;
import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.DTG;

class TestDataFileManager 
{	
	@Test
	void testSaveFile() 
	{
		CelestialBody[] data = new CelestialBody[2];
		data[0] = new CelestialBody(new Vector3d(1,1,1), new Vector3d(2,2,2), 100, 100, "Test", "/image/path", "icon/path", new DTG(1,1,1,1,1,1));
		data[1] = new CelestialBody(new Vector3d(2,2,2), new Vector3d(3,3,3), 100, 100, "Test", "/image/path", "icon/path", new DTG(1,1,1,1,1,2));
		
		DataFileManager manager = new DataFileManager();
		manager.save(data);
	}
	
	@Test
	void testLoadFile()
	{
		CelestialBody[] exp = new CelestialBody[2];
		exp[0] = new CelestialBody(new Vector3d(1,1,1), new Vector3d(2,2,2), 100, 100, "Test", "/image/path", "icon/path", new DTG(1,1,1,1,1,1));
		exp[1] = new CelestialBody(new Vector3d(2,2,2), new Vector3d(3,3,3), 100, 100, "Test", "/image/path", "icon/path", new DTG(1,1,1,1,1,2));
		
		DataFileManager manager = new DataFileManager();
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
