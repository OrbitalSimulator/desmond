package src.conf;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class FileManager 
{
	private static String getFilePath(String fileName)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		return path.concat("/src/main/java/src/config/" + fileName);
	}
	
	protected static String[] removeWhiteSpace(String[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			array[i] = array[i].strip();
		}
		return array;
	}
	
	public static LocalDateTime parseDateTime(String string)
	{
		string = unzipDateTime(string);
		String dateString = string.substring(0, 10);
		LocalDate date = LocalDate.parse(dateString);
		String timeString = string.substring(11, 19);
		LocalTime time = LocalTime.parse(timeString);
		return LocalDateTime.of(date, time);
	}
	
	public static String unzipDateTime(String zippedDateTime)
	{
		String templateString = "0000-00-00T00:00:00";
		char[] templateChars = templateString.toCharArray();
		char[] zippedChars = zippedDateTime.toCharArray();
		int pointer = 0;
		for(int i = 0; i < templateChars.length; i++)
		{
			if((pointer < zippedChars.length) && (templateChars[i] == '0'))
			{
				templateChars[i] = zippedChars[pointer++];
			}
		}
		templateString = String.copyValueOf(templateChars);
		return templateString;
	}
	
	public static String zipDateTime(LocalDateTime dateTime)
	{
		char[] dateTimeChars = dateTime.toString().toCharArray();
		char[] templateChars = new char[dateTimeChars.length];
		int pointer = 0;
		for(int i = 0; i < dateTimeChars.length; i++)
		{
			if(Character.isDigit(dateTimeChars[i]))
			{
				templateChars[pointer++] = dateTimeChars[i];
			}
		}
		String templateString = String.valueOf(templateChars);
		templateString = templateString.trim();
		return templateString;
	}
}
