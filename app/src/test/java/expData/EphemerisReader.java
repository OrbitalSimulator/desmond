package expData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.LinkedList;

import src.peng.Vector3d;
import src.univ.CelestialBody;
import src.univ.Coordinate;
import src.univ.DTG;

/**
 * Group 22
 * A class that reads an Ephemeris file from the NASA HORIZONS system out and converts it into a
 * list of Coordinates and Velocities 
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class EphemerisReader 
{
	public final boolean DEBUG = false;
	public String fileName;
	private String startOfEph = "$$SOE";
	private String endOfEph  = "$$EOE";
	private LinkedList<CelestialBody> bodies;
	private LinkedList<Coordinate> coords;
	
	//0  Ephemeris_Sol.txt
	//1  Ephemeris_Merc.txt
	//2  Ephemeris_Venu.txt
	//3  Ephemeris_Eart.txt
	//4  Ephemeris_Luna.txt
	//5  Ephemeris_Mars.txt
	//6  Ephemeris_Jupt.txt
	//7  Ephemeris_Satu.txt
	//8  Ephemeris_Tita.txt
	//9  Ephemeris_Uran.txt
	//10 Ephemeris_Nept.txt
		
	public EphemerisReader(String fileName)
	{
		this.fileName = fileName;
		coords = new LinkedList<Coordinate>();
		bodies = new LinkedList<CelestialBody>();
		
		// This opens the file and creates a new file reader
		// Exception is thrown if no file is found
		try 
		{	
			FileSystem fileSystem = FileSystems.getDefault();
			String path = fileSystem.getPath("").toAbsolutePath().toString();
			path = path.concat(fileName);
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// This tries to read the first line, throws an exception if nothing is found
			try
			{
				// Discard the first two lines
				String line = reader.readLine();
				line = reader.readLine();
				
				// Read the line with a name and crop out information either side, then remove whitespace
				String name = line.substring(30, 65).toString();
				name = name.replaceAll("\\s+","");
				double mass = lookUpMass(name);
				double radius = lookUpRadius(name);
				String image = lookUpImage(name);
				String icon = lookUpIcon(name);
											
				// While $$SOE not found, keep searching
				while(!line.startsWith(startOfEph))			
				{
					line = reader.readLine();
				}
				if(DEBUG) System.out.print("SOE found   \n");
				
				// Read the first four lines
				String dateln  = reader.readLine();
				String coordinateln = reader.readLine();
				String velocityln  = reader.readLine();
				String fourthln = reader.readLine();
				
				// While $$EOE not found, keep reading four lines at a time
				while(!dateln.startsWith(endOfEph))			
				{
					if(DEBUG) System.out.println(dateln );
					if(DEBUG) System.out.println(coordinateln);
					if(DEBUG) System.out.println(velocityln );
					if(DEBUG) System.out.println(fourthln);
					
					// Read the date line (in separate method)
					DTG dtg = toDTG(dateln);
					
					// Read the coordinate line
					char[] coordinate = coordinateln.toCharArray();
					char[] xCoord = Arrays.copyOfRange(coordinate, 4, 26);
					char[] yCoord = Arrays.copyOfRange(coordinate, 30, 52);
					char[] zCoord = Arrays.copyOfRange(coordinate, 56, 68);
					double x = Double.valueOf(String.copyValueOf(xCoord));
					double y = Double.valueOf(String.copyValueOf(yCoord));
					double z = Double.valueOf(String.copyValueOf(zCoord));
					x *= 1.E3;
					y *= 1.E3;
					z *= 1.E3;
					
					// Generate a new CelestialBody element
					Vector3d location = new Vector3d(x,y,z);
										
					// Read the velocity line
					char[] velocityLine = velocityln.toCharArray();
					char[] xV = Arrays.copyOfRange(velocityLine, 4, 25);
					char[] yV = Arrays.copyOfRange(velocityLine, 30, 51);
					char[] zV = Arrays.copyOfRange(velocityLine, 56, 67);
					double xv = Double.valueOf(String.copyValueOf(xV));
					double yv = Double.valueOf(String.copyValueOf(yV));
					double zv = Double.valueOf(String.copyValueOf(zV));
					xv *= 1.E3;
					yv *= 1.E3;
					zv *= 1.E3;
					
					Vector3d velocity = new Vector3d(xv,yv, zv);
					
					dateln  = reader.readLine();
					coordinateln = reader.readLine();
					velocityln  = reader.readLine();
					fourthln = reader.readLine();
					
					bodies.add(new CelestialBody(location, velocity, mass, radius, name, image, icon,dtg));
				}
			}
			catch (IOException e) 
			{
				System.out.println("Unable to read first line");
				e.printStackTrace();
			}	
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File " + fileName + " Not Found");
			e.printStackTrace();
		}
		
		// This prints everything to the terminal on debug
		if(DEBUG)
		{
			for(Coordinate c: coords)
			{
				System.out.println(c.toString());
			}
		}
	}
	
	public CelestialBody[] getOrbit()
	{
		CelestialBody[] C = new CelestialBody[bodies.size()];
		int pntr = 0;
		for(CelestialBody c: bodies)
		{
			C[pntr++] = c;
		}
		return C;
	}
	
	/** Read a standard string in the form:
	 * "2458940.500000000 = A.D. 2020-Apr-01 00:00:00.0000 TDB "
	 * @return a DTG
	 */
	private DTG toDTG(String date)
	{
		char[] chars = date.toCharArray();
		char[] year = Arrays.copyOfRange(chars, 25, 29);
		int yr = Integer.valueOf(String.copyValueOf(year));
		char[] month = Arrays.copyOfRange(chars, 30, 33);
		String mth = String.copyValueOf(month);
		char[] day = Arrays.copyOfRange(chars, 34, 36);
		int dy = Integer.valueOf(String.copyValueOf(day));
		char[] hour = Arrays.copyOfRange(chars, 37, 39);
		int hr = Integer.valueOf(String.copyValueOf(hour));
		char[] minute = Arrays.copyOfRange(chars, 40, 42);
		int min = Integer.valueOf(String.copyValueOf(minute));
		char[] second = Arrays.copyOfRange(chars, 43, 45);
		int sec = Integer.valueOf(String.copyValueOf(second));
		return new DTG(yr,mth,dy,hr,min,sec);
	}
	
	private double lookUpMass(String name)
	{
		switch(name)
		{
			case "Sun": 		
				 	return 1.988500e30;
			case "Mercury":	
				 	return 3.302e23;
			case "Venus": 		
				 	return 4.8685e24;  
			case "Earth": 		
				 	return 5.97219e24; 
			case "Moon":		
				 	return 7.349e22;  	
			case "Mars": 		
				 	return 6.4171e23;  
			case "Jupiter": 	
				 	return 1.89813e27; 
			case "Saturn": 	
				 	return 5.6834e26;  
			case "Titan": 		
				 	return 1.34553e23;    
			case "Uranus": 	
				 	return 8.6813e25;  
			case "Neptune": 	
				 	return 1.02413e26;
			default:
				return 1.988500e30;
		}
	}
	
	private double lookUpRadius(String name)
	{
		switch(name)
		{
			case "Sun": 		
					return 696000e3;              
			case "Mercury":	
					return 2440e3;           
			case "Venus": 		
					return 6051.84e3;             
			case "Earth": 		
					return 6371e3;      
			case "Moon":		
					return 1738.0e3;           
			case "Mars": 		
					return 3389.92e3;              
			case "Jupiter": 	
					return 1898.13e3;              
			case "Saturn": 	
					return 60268e3;              
			case "Titan": 		
					return 2575.5e3;     
			case "Uranus": 	
					return 25362e3;              
			case "Neptune": 	
					return 24624e3;
			default:
				return 696000e3;
		}
	}
	
	private String lookUpImage(String name)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		
		switch(name)
		{
			case "Sun": 		
					path = path.concat("/src/main/java/src/misc/sunScaled.png");
					return path;              
			case "Mercury":	
					path = path.concat("/src/main/java/src/misc/mercuryScaled.png");
					return path;                       
			case "Venus": 		
					path = path.concat("/src/main/java/src/misc/venusScaled.png");
					return path;                 
			case "Earth": 
					path = path.concat("/src/main/java/src/misc/earthScaled.png");
					return path;              
			case "Moon":
					path = path.concat("/src/main/java/src/misc/moonScaled.png");
					return path;                
			case "Mars": 
					path = path.concat("/src/main/java/src/misc/marsScaled.png");
					return path;                    
			case "Jupiter":
					path = path.concat("/src/main/java/src/misc/jupiterScaled.png");
					return path;                     
			case "Saturn": 
					path = path.concat("/src/main/java/src/misc/saturnScaled.png");
					return path;                  
			case "Titan": 
					path = path.concat("/src/main/java/src/misc/titanScaled.png");
					return path;              
			case "Uranus": 
					path = path.concat("/src/main/java/src/misc/uranusScaled.png");
					return path;                   
			case "Neptune":
					path = path.concat("/src/main/java/src/misc/neptuneScaled.png");
					return path;              
			default:
				return null;
		}
	}
	
	private String lookUpIcon(String name)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String path = fileSystem.getPath("").toAbsolutePath().toString();
		
		switch(name)
		{
			case "Sun": 		
					path = path.concat("/src/main/java/src/misc/sunIcon.png");
					return path;              
			case "Mercury":	
					path = path.concat("/src/main/java/src/misc/mercuryIcon.png");
					return path;                       
			case "Venus": 		
					path = path.concat("/src/main/java/src/misc/venusIcon.png");
					return path;                 
			case "Earth": 
					path = path.concat("/src/main/java/src/misc/earthIcon.png");
					return path;              
			case "Moon":
					path = path.concat("/src/main/java/src/misc/moonIcon.png");
					return path;                
			case "Mars": 
					path = path.concat("/src/main/java/src/misc/marsIcon.png");
					return path;                    
			case "Jupiter":
					path = path.concat("/src/main/java/src/misc/jupiterIcon.png");
					return path;                     
			case "Saturn": 
					path = path.concat("/src/main/java/src/misc/saturnIcon.png");
					return path;                  
			case "Titan": 
					path = path.concat("/src/main/java/src/misc/titanIcon.png");
					return path;              
			case "Uranus": 
					path = path.concat("/src/main/java/src/misc/uranusIcon.png");
					return path;                   
			case "Neptune":
					path = path.concat("/src/main/java/src/misc/neptuneIcon.png");
					return path;              
			default:
				return null;
		}
	}
}
