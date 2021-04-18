package src.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.DTG;

public class ConfigFileManager
{		
	public void overwrite(String fileName, String[] data)
	{
		try 
		{
			String filePath = getFilePath(fileName);
			File file = new File(filePath);
				
			if(!file.exists())
			{
				file.createNewFile();
			}
			writeFileData(file, data);
		}
		catch (IOException e)
		{
			e.printStackTrace();	
		}
	}
	
	public CelestialBody[] load(String fileName) throws Exception
	{
		String filePath = getFilePath(fileName);
		File file = new File(filePath);
		if(!file.exists())
		{
			throw new FileNotFoundException(filePath + " Not found");
		}
		else
		{
			return readFileData(file);
		}
	}
		
	private String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/data/" + fileName);
	}
		
	private void writeFileData(File file, String[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,true);
		for(int i = 0; i < data.length; i++)
		{
			writer.write(data[i]);
		}
		writer.close();
	}
	
	private CelestialBody[] readFileData(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		ArrayList<CelestialBody> dataList = new ArrayList<CelestialBody>();
		while(line != null)
		{
			line = reader.readLine();
			dataList.add(convertToCelestialBody(line));
		}
		reader.close();
		return convertToArray(dataList);
	}
	
	private CelestialBody convertToCelestialBody(String line)
	{
		String[] subStrings = line.split(",");
		return new CelestialBody(
				new Vector3d(Double.valueOf(subStrings[1]),Double.valueOf(subStrings[2]),Double.valueOf(subStrings[3])),
				new Vector3d(Double.valueOf(subStrings[4]),Double.valueOf(subStrings[5]),Double.valueOf(subStrings[6])),
				Double.valueOf(subStrings[7]),
				Double.valueOf(subStrings[8]),
				subStrings[0],
				subStrings[9],
				subStrings[9],
				new DTG(subStrings[0]));  // TODO (Leon) add time step
	}
	
	private CelestialBody[] convertToArray(ArrayList<CelestialBody> arrayList)
	{
		CelestialBody[] array = new CelestialBody[arrayList.size()];
		for(int i = 0; i < arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
}