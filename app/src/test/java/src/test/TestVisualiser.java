package src.test;

import org.junit.jupiter.api.Test;

import expData.EphemerisReader;
import src.univ.CelestialBody;
import src.visu.Visualiser;

class TestVisualiser 
{
	@Test
	void testVisualiser()
	{
		int noOfCB = 11;
		
		// Find all files that start with "Ephemeris_" and end with ".txt"
		String[] paths = new String[11];
		for(int i = 0; i < noOfCB; i++)
		{	
			StringBuilder s =  new StringBuilder();
			s.append("/src/test/java/expData/Ephemeris_");
			s.append(i);
			s.append(".txt");
			paths[i] = s.toString();
		}
		
		EphemerisReader er = new EphemerisReader(paths[0]);
		CelestialBody[] temp = er.getOrbit();
		CelestialBody[][] U = new CelestialBody[noOfCB][temp.length];
		U[0] = temp;
			
		// Read all of the files and add the array to the list
		EphemerisReader[] ers = new EphemerisReader[paths.length];
		for(int i = 1; i < U.length; i++)
		{
			er = new EphemerisReader(paths[i]);
			U[i] = er.getOrbit(); 
		}
		
		Visualiser.getInstance().addUniverse(U);
	}
}
