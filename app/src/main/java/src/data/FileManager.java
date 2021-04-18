package src.data;

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
		String timeString = string.substring(0, 10);
		String dateString = string.substring(11, 19);
		System.out.println(dateString);
		LocalTime time = LocalTime.parse(timeString);
		LocalDate date = LocalDate.parse(dateString);
		return LocalDateTime.of(date, time);
	}
}
