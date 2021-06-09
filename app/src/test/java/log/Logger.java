package log;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import src.conf.FileManager;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Logger extends FileManager
{
   
    /**
     * Append a string to a new line of a {@code .txt} file
     * Creates a new file if one cannot be found
     * @param fileName  - The name of the file
     * @param str		- The string to append
     */
	public static void log(String fileName, String str)
    {
       try 
       {
			fileName = fileName.concat(".txt");
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
	
    /**
     * Append a string to a new line of the {@code log.txt} file
     * Creates a new file if one cannot be found
     * @param str		- The string to append
     */
	public static void log(String str)
    {
       try 
       {
			String fileName = "log.txt";
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
	
	/**
     * Append a string to a new line of a {@code .csv} file
     * Creates a new file if one cannot be found
     * @param fileName  - The name of the file
     * @param str		- The comma delimited string to append
     */
	public static void logCSV(String fileName, String str)
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

    /**
     * Append a string to a new line of a {@code .csv} file
     * Creates a new file if one cannot be found
     * @param fileName  - The name of the file
     * @param str		- The array of strings to append with comma delimiters
     */
    public static void logCSV(String fileName, String[] str)
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
