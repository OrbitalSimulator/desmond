package src.conf;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public abstract class Logger extends FileManager
{
    public static void log(String str)
    {
        try{
            String filePath = getFilePath("log.csv");
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file, true);

            writer.write(str + "," + "\n");
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void log(String[] str)
    {
        try{
            String filePath = getFilePath("log.csv");
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file, true);

            for(int i = 0; i < str.length; i++){
                writer.write(str[i] + ",");
            }
            writer.write("\n");
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private static String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/conf/" + fileName);
	}
}
