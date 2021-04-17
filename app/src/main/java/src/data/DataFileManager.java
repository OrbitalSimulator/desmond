package src.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.DTG;

public class DataFileManager 
{		
	public void save(CelestialBody[] data)
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
		else
		{
			return readFileData(reference, file);
		}
	}
	
	private String createFileName(CelestialBody[] data)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(data[0].name + "_");
		fileName.append(data[0].time.toString() + "_");
		fileName.append(data[data.length-1].time.toString() + "_");
		fileName.append(data.length);
		return fileName.toString();
	}
	
	private String createFileName(DataFileReference reference)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(reference.Name + "_");
		fileName.append(reference.Start_Time.toString() + "_");
		fileName.append(reference.End_Time.toString() + "_");
		fileName.append(reference.No_of_Steps);
		return fileName.toString();
	}
	
	private String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/data/" + fileName);
	}
	
	private void writeFileHeader(File file, CelestialBody[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,false);
		writer.write("Name=" + data[0].name + "\n");
		writer.write("Mass=" + data[0].mass + "\n");
		writer.write("Radius=" + data[0].radius + "\n");
		writer.write("Image_Path=" + data[0].image + "\n");
		writer.write("Icon_Path=" + data[0].icon + "\n");
		writer.write("Start_Time=" + data[0].time.toString() + "\n");
		writer.write("End_Time=" + data[data.length-1].time.toString() + "\n");
		writer.write("No_of_Steps=" + data.length + "\n");
		writer.write("$SOE\n");
		writer.close();
	}
	
	private void writeFileData(File file, CelestialBody[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,true);
		for(int i = 0; i < data.length; i++)
		{
			writer.write(data[i].time + "," + 
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
			String[] subStrings = line.split(","); 
			data[i] = new CelestialBody(
					new Vector3d(Double.valueOf(subStrings[1]),Double.valueOf(subStrings[2]),Double.valueOf(subStrings[3])),
					new Vector3d(Double.valueOf(subStrings[4]),Double.valueOf(subStrings[5]),Double.valueOf(subStrings[6])),
					reference.Mass,
					reference.Radius,
					reference.Name,
					reference.Image_Path,
					reference.Icon_Path,
					new DTG(subStrings[0]));  // TODO (Leon) add time step
		}
		reader.close();
		return data;
	}
}
