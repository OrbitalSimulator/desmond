package src.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.io.FileWriter;

public class CSVLogger 
{
	File file;
	FileWriter writer;
	String path;
	
	// The constructor creates a new instance of a File Object called "fileName.txt".
	// If the file already exists it throws a IOException
	public CSVLogger(String fileName)
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
	
	
	public void log(String text)
	{
		try{
			writer = new FileWriter(path, true);
			writer.write(text + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}