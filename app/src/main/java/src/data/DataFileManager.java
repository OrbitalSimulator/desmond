package src.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import src.univ.CelestialBody;

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
	
	private String createFileName(CelestialBody[] data)
	{
		StringBuilder fileName = new StringBuilder();
		fileName.append(data[0].name + "_");
		fileName.append(data[0].time.toString() + "_");
		fileName.append(data[data.length-1].time.toString() + "_");
		fileName.append(data.length);
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
		FileWriter writer = new FileWriter(file,true);
		writer.write("Name=" + data[0].name);
		writer.write("Mass=" + data[0].mass);
		writer.write("Radius=" + data[0].radius);
		writer.write("Image_Path=" + data[0].image);
		writer.write("Icon_Path=" + data[0].icon);
		writer.write("Start_Time=" + data[0].time.toString());
		writer.write("End_Time=" + data[data.length].time.toString());
		writer.write("No_of_Steps=" + data.length);
		writer.write("$SOE");
		writer.close();
	}
	
	private void writeFileData(File file, CelestialBody[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,true);
		writer.close();
	}
}
