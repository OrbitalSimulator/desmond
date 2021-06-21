package src.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.util.ArrayList;

import src.peng.Vector3d;
import src.univ.CelestialBody;

public abstract class SettingsFileManager extends FileManager
{
	public static SimulationSettings load() throws IOException
	{
		String filePath = getFilePath("settingsConfig");
		File file = new File(filePath);
		if(!file.exists())
		{
			throw new FileNotFoundException(filePath + " Not found");
		}
		return readConfigFile(file);
	}
	
	public static SimulationSettings load(String fileName) throws IOException
	{
		String filePath = getFilePath(fileName);
		File file = new File(filePath);
		if(!file.exists())
		{
			throw new FileNotFoundException(filePath + " Not found");
		}
		return readConfigFile(file);
	}

	public static SimulationSettings loadTest() throws IOException
	{
        String filePath = getFilePath("testConfig");
		File file = new File(filePath);
		if(!file.exists())
		{
			throw new FileNotFoundException(filePath + " Not found");
		}
		return readConfigFile(file);
	}
	
	private static SimulationSettings readConfigFile(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		
		// Read Header
		while(!line.equalsIgnoreCase("##HEADER"))  {line = reader.readLine();} // Find header
		LocalDateTime startTime = parseDateTime(reader.readLine());
		LocalDateTime endTime = parseDateTime(reader.readLine());
		int noOfSteps = Integer.valueOf(reader.readLine());
		double stepSize = Double.valueOf(reader.readLine());
		
		// Read Celestial Bodies
		while(!line.equalsIgnoreCase("##CELESTIAL BODIES"))  {line = reader.readLine();} // Find bodies
		ArrayList<CelestialBody> dataList = new ArrayList<CelestialBody>();
		line = reader.readLine();
		
		while(!line.equalsIgnoreCase("##END"))
		{
			dataList.add(convertToCelestialBody(line));
			line = reader.readLine();
		}
		CelestialBody[] celestialBodies = convertToArray(dataList);
		
		// Read probe
		while(!line.equalsIgnoreCase("##PROBE")) {line = reader.readLine();} // Find probe
		line = reader.readLine();
		CelestialBody probe =  convertToCelestialBody(line);
		
		// Read Waypoints
		while(!line.equalsIgnoreCase("##WAYPOINTS")) { line = reader.readLine();} // Find waypoints
		line = reader.readLine();
		String waypoints[] = line.split(",");
		
		reader.close();
		return new SimulationSettings(celestialBodies,
						              probe.location,
						              probe.velocity,
						              startTime,
						              endTime,
						              noOfSteps,
						              stepSize,
						              waypoints); 
	}
	
	private static CelestialBody convertToCelestialBody(String line)
	{
		String[] subStrings = line.split(",");
		subStrings = removeWhiteSpace(subStrings);
		return new CelestialBody(
				new Vector3d(Double.valueOf(subStrings[1]),Double.valueOf(subStrings[2]),Double.valueOf(subStrings[3])),
				new Vector3d(Double.valueOf(subStrings[4]),Double.valueOf(subStrings[5]),Double.valueOf(subStrings[6])),
				Double.valueOf(subStrings[7]),
				Double.valueOf(subStrings[8]),
				subStrings[0],							
				subStrings[9],
				subStrings[10],
				parseDateTime(subStrings[11]));
	}
	
	private static CelestialBody[] convertToArray(ArrayList<CelestialBody> arrayList)
	{
		CelestialBody[] array = new CelestialBody[arrayList.size()];
		for(int i = 0; i < arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	
	private static String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/conf/" + fileName);
	}
}
