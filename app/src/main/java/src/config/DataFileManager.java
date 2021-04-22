package src.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import src.peng.Vector3d;
import src.univ.CelestialBody;

public class DataFileManager extends FileManager
{		
	public boolean query(SimulationSettings query)
	{
		boolean dataAvailable = true;
		
		// Run through all CelestialBodies in the query
		for(int i = 0; i < query.celestialBodies.length; i++)
		{
			String fileName = createFileName(query.celestialBodies[i], 
											 query.startTime, 
											 query.endTime, 
											 query.noOfSteps);
			String filePath = getFilePath(fileName);
			File file = new File(filePath);
			if(!file.exists())
			{
				dataAvailable = false;
			}
		}
		return dataAvailable;
	}
	
	public void overwrite(CelestialBody[] data)
	{
		try 
		{
			String fileName = createFileName(data);
			String filePath = getFilePath(fileName);
			File file = new File(filePath);
				
			if(!file.exists())
			{
				file.createNewFile();
			}
			writeFileHeader(file, data);
			writeFileData(file, data);
		}
		catch (IOException e)
		{
			e.printStackTrace();	
		}
	}
	
	public CelestialBody[] load(DataFileReference reference) throws Exception
	{
		String fileName = createFileName(reference);
		String filePath = getFilePath(fileName);
		File file = new File(filePath);
		if(!file.exists())
		{
			throw new FileNotFoundException(filePath + " Not found");
		}
		return readFileData(reference, file);
	}
	
	private void writeFileHeader(File file, CelestialBody[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,false);
		writer.write("Name=" + data[0].name + "\n");
		writer.write("Mass=" + data[0].mass + "\n");
		writer.write("Radius=" + data[0].radius + "\n");
		writer.write("Image_Path=" + data[0].image + "\n");
		writer.write("Icon_Path=" + data[0].icon + "\n");
		writer.write("Start_Time=" + zipDateTime(data[0].time) + "\n");
		writer.write("End_Time=" + zipDateTime(data[data.length-1].time) + "\n");
		writer.write("No_of_Steps=" + data.length + "\n");
		writer.write("$SOE\n");
		writer.close();
	}
	
	private void writeFileData(File file, CelestialBody[] data) throws IOException
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
	
	private CelestialBody[] readFileData(DataFileReference reference, File file) throws IOException
	{
		CelestialBody[] data = new CelestialBody[reference.No_of_Steps];
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		// Find where the data starts
		while(!line.equalsIgnoreCase("$SOE"))
		{
			line = reader.readLine();
		}
		// Start reading vectors
		for(int i = 0; i < reference.No_of_Steps; i++)
		{
			line = reader.readLine();
			data[i] = convertToCelestialBody(line, reference);
		}
		reader.close();
		return data;
	}
	
	private CelestialBody convertToCelestialBody(String line, DataFileReference reference)
	{
		String[] subStrings = line.split(","); 
		subStrings = removeWhiteSpace(subStrings);
		return new CelestialBody(
				new Vector3d(Double.valueOf(subStrings[1]),Double.valueOf(subStrings[2]),Double.valueOf(subStrings[3])),
				new Vector3d(Double.valueOf(subStrings[4]),Double.valueOf(subStrings[5]),Double.valueOf(subStrings[6])),
				reference.Mass,
				reference.Radius,
				reference.Name,
				reference.Image_Path,
				reference.Icon_Path,
				parseDateTime(subStrings[0]));  
	}
	
	private String createFileName(CelestialBody[] data)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(data[0].name + "_");
		fileName.append(zipDateTime(data[0].time) + "_");
		fileName.append(zipDateTime(data[data.length-1].time) + "_");
		fileName.append(data.length);
		return fileName.toString();
	}
	
	private String createFileName(CelestialBody celestialBody, LocalDateTime startTime, LocalDateTime endTime, int noOfSteps)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(celestialBody.name + "_");
		fileName.append(zipDateTime(startTime) + "_");
		fileName.append(zipDateTime(endTime) + "_");
		fileName.append(noOfSteps);
		return fileName.toString();
	}
	
	private String createFileName(DataFileReference reference)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(reference.Name + "_");
		fileName.append(zipDateTime(reference.Start_Time) + "_");
		fileName.append(zipDateTime(reference.End_Time) + "_");
		fileName.append(reference.No_of_Steps);
		return fileName.toString();
	}
}
