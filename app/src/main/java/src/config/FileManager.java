package src.config;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FileManager 
{
	protected String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/data/" + fileName);
	}
	
	protected String[] removeWhiteSpace(String[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			array[i] = array[i].strip();
		}
		return array;
	}
	
	protected LocalDateTime parseDateTime(String string)
	{
		String dateString = string.substring(0, 10);
		LocalDate date = LocalDate.parse(dateString);
		String timeString = string.substring(11, 16);
		LocalTime time = LocalTime.parse(timeString);
		return LocalDateTime.of(date, time);
	}
}
