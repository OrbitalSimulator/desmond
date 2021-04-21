package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import src.config.FileManager;

class TestFileManager 
{

	@Test
	void testZipDateTime() 
	{
		FileManager fm = new FileManager();
		
		LocalTime time = LocalTime.parse("12:34:56");
		LocalDate date = LocalDate.parse("2021-04-01");
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		String exp = "20210401123456";
		String act = fm.zipDateTime(dateTime);
		if(!exp.equalsIgnoreCase(act))
			fail("Not Equal");
	}
	
	@Test
	void testUnzipDateTime() 
	{
		FileManager fm = new FileManager();
		assertEquals("2021-04-01T12:34:56",fm.unzipDateTime("20210401123456"));
	}
	
	@Test
	void testparseDateTime() 
	{
		FileManager fm = new FileManager();
		
		LocalTime expTime = LocalTime.parse("12:34:56");
		LocalDate expDate = LocalDate.parse("2021-04-01");
		LocalDateTime expDateTime = LocalDateTime.of(expDate, expTime);
		
		LocalDateTime actDateTime = fm.parseDateTime("20210401123456");
		
		assertEquals(expDateTime, actDateTime);
	}

}
