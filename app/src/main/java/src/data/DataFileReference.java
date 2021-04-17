package src.data;

import src.univ.CelestialBody;
import src.univ.DTG;

public class DataFileReference 
{
	public String Name;
	public double Mass;
	public double Radius;
	public String Image_Path;
	public String Icon_Path;
	public DTG Start_Time;
	public DTG End_Time;
	public int No_of_Steps;
	
	public DataFileReference generateFileReferece(CelestialBody[] data)
	{
		DataFileReference ref = new DataFileReference();
		ref.Name = data[0].name;       
		ref.Mass = data[0].mass;       
		ref.Radius = data[0].radius;     
		ref.Image_Path = data[0].image; 
		ref.Icon_Path = data[0].icon;  
		ref.Start_Time = data[0].time;    
		ref.End_Time = data[data.length-1].time;      
		ref.No_of_Steps = data.length;   
		return ref;
	}
}
