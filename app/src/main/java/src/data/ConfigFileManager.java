package src.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import src.peng.Vector3d;
import src.univ.CelestialBody;

public class ConfigFileManager extends FileManager
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
		return readFileData(file);
	}
				
	private void writeFileData(File file, String[] data) throws IOException
	{
		FileWriter writer = new FileWriter(file,true);
		for(int i = 0; i < data.length; i++)
		{
			writer.write(data[i]);
		}
		writer.write("EOF");
		writer.close();
	}
	
	private CelestialBody[] readFileData(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		ArrayList<CelestialBody> dataList = new ArrayList<CelestialBody>();
		while(!line.equalsIgnoreCase("EOF"))
		{
			dataList.add(convertToCelestialBody(line));
			line = reader.readLine();
		}
		reader.close();
		return convertToArray(dataList);
	}
	
	private CelestialBody convertToCelestialBody(String line)
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