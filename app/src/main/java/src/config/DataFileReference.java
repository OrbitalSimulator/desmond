package src.config;

import java.time.LocalDateTime;

import src.univ.CelestialBody;

public class DataFileReference 
{
	public String Name;
	public double Mass;
	public double Radius;
	public String Image_Path;
	public String Icon_Path;
	public LocalDateTime Start_Time;
	public LocalDateTime End_Time;
	public int No_of_Steps;
	
	public DataFileReference(CelestialBody[] data)
	{
		Name = data[0].name;       
		Mass = data[0].mass;       
		Radius = data[0].radius;     
		Image_Path = data[0].image; 
		Icon_Path = data[0].icon;  
		Start_Time = data[0].time;    
		End_Time = data[data.length-1].time;      
		No_of_Steps = data.length;   
	}
}
