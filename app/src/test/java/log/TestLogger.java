package log;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import src.conf.FileManager;

import java.io.FileWriter;
import java.io.IOException;

public abstract class TestLogger extends FileManager
{
    public static void log(String fileName, String str)
    {
       try 
       {
			fileName = fileName.concat(".csv");
			String filePath = getFilePath(fileName);
			File file = new File(filePath);
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileWriter writer = new FileWriter(file, true);
			writer.write(str);
			writer.append("\n");
			writer.close();
       }
       catch(IOException e)
       {
    	  System.out.println("File not found"); 
       }
    }

    public static void log(String fileName, String[] str)
    {
        try 
        {
			fileName = fileName.concat(".csv");
			String filePath = getFilePath(fileName);
			File file = new File(filePath);
	        
	        if(!file.exists())
	        {
	            file.createNewFile();
	        }
	        
	        FileWriter writer = new FileWriter(file, true);
	        for(int i = 0; i < str.length; i++)
	        {
	            writer.write(str[i] + ",");
	        }
	        writer.close();
        }
	    catch(IOException e)
	    {
	      System.out.println("File not found"); 
	    }
    }

    private static String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/test/java/log/" + fileName);
	}
}
