package src.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import src.peng.Vector3d;

import java.io.FileWriter;

public class SaveFile
{
	File file;
	FileWriter writer;
	String path;
	
	// The constructor creates a new instance of a File Object called "fileName.txt".
	// If the file already exists it throws a IOException
	public SaveFile(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		path = fileSystem.getPath("").toAbsolutePath().toString();
		path = path.concat("/src/logg/" + fileName + ".csv");
		file = new File(path);
		
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("An Error Occured:");
				e.printStackTrace();
			}
		}
	}
	
	
	public void save(Vector3d v)
	{
		try{
			writer = new FileWriter(file, false);
			writer.write(v.getX() + "\n");
			writer.write(v.getY() + "\n");
			writer.write(v.getZ() + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File not overwritten");
		}
	}
	
	public Vector3d fetch()
	{
		double x = 0;
		double y = 0;
		double z = 0;
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String xLn = br.readLine();
			String yLn = br.readLine(); 
			String zLn = br.readLine(); 
			
			x = Double.valueOf(xLn);
			y = Double.valueOf(yLn);
			z = Double.valueOf(zLn);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new Vector3d(x,y,z);
	}
}
