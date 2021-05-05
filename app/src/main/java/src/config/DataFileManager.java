package src.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;

import src.peng.Vector3d;
import src.univ.CelestialBody;

public abstract class DataFileManager extends FileManager
{		
	public static void overwrite(CelestialBody[][] data)
	{
		try 
		{
			for(int i = 0; i < data.length; i++)
			{
				String fileName = createFileName(data[i]);
				String filePath = getFilePath(fileName);
				File file = new File(filePath);
					
				if(!file.exists())
				{
					file.createNewFile();
				}
				writeFileHeader(file, data[i]);
				writeFileData(file, data[i]);
			}
		}
		catch (IOException e)
		{
			System.out.println("Unable to save universe data");
			e.printStackTrace();	
		}
	}
	
	public static CelestialBody[][] load(SimulationSettings settings) throws Exception
	{
		CelestialBody[][] data = new CelestialBody[settings.celestialBodies.length][settings.noOfSteps];
		for(int i = 0; i < settings.celestialBodies.length; i++)
		{
			String fileName = createFileName(settings, i);
			String filePath = getFilePath(fileName);
			File file = new File(filePath);
			if(!file.exists())
			{
				System.out.println("\n" + filePath + " Not found");
				throw new FileNotFoundException(filePath + " Not found");
			}
			data[i] = readFileData(settings, file);
		}
		return data;
	}
		
	private static void writeFileHeader(File file, CelestialBody[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,false);
		writer.write(data[0].name + "\n");
		writer.write(data[0].mass + "\n");
		writer.write(data[0].radius + "\n");
		writer.write(data[0].image + "\n");
		writer.write(data[0].icon + "\n");
		writer.write(zipDateTime(data[0].time) + "\n");
		writer.write(zipDateTime(data[data.length-1].time) + "\n");
		writer.write(data.length + "\n");
		writer.write("$SOE\n");
		writer.close();
	}
	
	private static void writeFileData(File file, CelestialBody[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,true);
		for(int i = 0; i < data.length; i++)
		{
			writer.write(zipDateTime(data[i].time) + "," + 
						data[i].location.getX() + "," + 
						data[i].location.getY() + "," +
						data[i].location.getZ() + "," +
						data[i].velocity.getX() + "," +
						data[i].velocity.getY() + "," +
						data[i].velocity.getZ() + "\n");
		}
		writer.close();
	}
	
	private static CelestialBody[] readFileData(SimulationSettings settings, File file) throws IOException
	{
		CelestialBody[] data = new CelestialBody[settings.noOfSteps];
		BufferedReader reader = new BufferedReader(new FileReader(file));
		// Read file header and create a template Celestial Body
		String name = reader.readLine();
		double mass = Double.valueOf(reader.readLine());
		double radius = Double.valueOf(reader.readLine());
		String image = reader.readLine();
		String icon = reader.readLine();
		LocalDateTime startTime = parseDateTime(reader.readLine());
		LocalDateTime endTime = parseDateTime(reader.readLine());
		double noOfSteps = Double.valueOf(reader.readLine());

		CelestialBody template = new CelestialBody(new Vector3d(0,0,0),
												   new Vector3d(0,0,0),
												   mass,
												   radius, 
												   name, 
												   image, 
												   icon,  
												   startTime);
		String line = reader.readLine();
		// Find where the data starts
		while(!line.equalsIgnoreCase("$SOE"))
		{
			line = reader.readLine();
		}
		// Start reading vectors
		for(int i = 0; i < settings.noOfSteps; i++)
		{
			line = reader.readLine();
			data[i] = convertToCelestialBody(line, template);
		}
		reader.close();
		return data;
	}
	
	private static CelestialBody convertToCelestialBody(String line, CelestialBody template)
	{
		String[] subStrings = line.split(","); 
		subStrings = removeWhiteSpace(subStrings);
		return new CelestialBody(
				new Vector3d(Double.valueOf(subStrings[1]),Double.valueOf(subStrings[2]),Double.valueOf(subStrings[3])),
				new Vector3d(Double.valueOf(subStrings[4]),Double.valueOf(subStrings[5]),Double.valueOf(subStrings[6])),
				template.mass,
				template.radius,
				template.name,
				template.image,
				template.icon,
				parseDateTime(subStrings[0]));  
	}
	
	private static String createFileName(CelestialBody[] data)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(data[0].name + "_");
		fileName.append(zipDateTime(data[0].time) + "_");
		fileName.append(data.length-1 + "_");
		
		double step1 = data[1].timeInMs();
		double step0 = data[0].timeInMs();
		double stepSize = step1 - step0;
		fileName.append(String.format("%.0f", stepSize));				
		return fileName.toString();
	}
		
	private static String createFileName(SimulationSettings settings, int celestialBodyIndex)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(settings.celestialBodies[celestialBodyIndex].name + "_");
		fileName.append(zipDateTime(settings.startTime) + "_");
		fileName.append((settings.noOfSteps) + "_");
		fileName.append(String.format("%.0f", settings.stepSize));				
		return fileName.toString();
	}
	
	private static String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/data/" + fileName);
	}
	
}
