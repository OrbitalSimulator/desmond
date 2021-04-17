package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.data.DataFileManager;
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

}
