package src.univ;

import src.peng.Vector3d;
import src.peng.StateInterface;
import src.peng.Vector3dInterface;
import src.peng.State;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

//Class containing universal constants
public class Universe
{
    public static final boolean DEBUG = false;
    
    //Gravitational constant
    public static double gConstant = 6.67430e-11;
    public static int totalPlanets = 11; //Was 11, currently using 12 for the probe
    public static int totalBodies = 12;

    //Instance field of planets
    /*00*/public  CelestialBody sun = new CelestialBody(1.988500e30, new Vector3d( -6.806783239281648e+08,   1.080005533878725e+09,   6.564012751690170e+06), new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00,  3.994237625449041e-01),696000e3);
    /*01*/public  CelestialBody mercury = new CelestialBody(3.302e23, new Vector3d(  6.047855986424127e+06,  -6.801800047868888e+10,  -5.702742359714534e+09), new Vector3d( 3.892585189044652e+04,  2.978342247012996e+03, -3.327964151414740e+03), 2440e3);
    /*02*/public  CelestialBody venus=  new CelestialBody(4.8685e24, new Vector3d( -9.435345478592035e+10,   5.350359551033670e+10,   6.131453014410347e+09), new Vector3d( -1.726404287724406e+04, -3.073432518238123e+04,  5.741783385280979e-04), 6051.84e3);
    /*03*/public  CelestialBody earth=  new CelestialBody(5.97219e24, new Vector3d( -1.471922101663588e+11,  -2.860995816266412e+10,   8.278183193596080e+06), new Vector3d( 5.427193405797901e+03, -2.931056622265021e+04,  6.575428158157592e-01),6371e3);
    /*04*/public  CelestialBody moon  =new CelestialBody(7.349e22, new Vector3d( -1.472343904597218e+11,  -2.822578361503422e+10,   1.052790970065631e+07), new Vector3d( 4.433121605215677e+03, -2.948453614110320e+04,  8.896598225322805e+01), 1738.0e3);
    /*05*/public  CelestialBody mars=  new CelestialBody(6.4171e23, new Vector3d(  -3.615638921529161e+10,  -2.167633037046744e+11,  -3.687670305939779e+09), new Vector3d( 2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02),3389.92e3);
    /*06*/public  CelestialBody jupiter = new CelestialBody(1.89813e27, new Vector3d(  1.781303138592153e+11,  -7.551118436250277e+11,  -8.532838524802327e+08), new Vector3d( 1.255852555185220e+04,  3.622680192790968e+03, -2.958620380112444e+02),1898.13e3);
    /*07*/public  CelestialBody saturn = new CelestialBody(5.6834e26, new Vector3d(  6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09), new Vector3d( 8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02),60268e3);
    /*08*/public  CelestialBody titan = new CelestialBody(1.34553e23, new Vector3d(  6.332873118527889e+11,  -1.357175556995868e+12,  -2.134637041453660e+09), new Vector3d(  3.056877965721629e+03,  6.125612956428791e+03, -9.523587380845593e+02),2575.5e3);
    /*09*/public  CelestialBody uranus=  new CelestialBody(8.6813e25, new Vector3d( 2.395195786685187e+12,   1.744450959214586e+12,  -2.455116324031639e+10), new Vector3d( -4.059468635313243e+03,  5.187467354884825e+03,  7.182516236837899e+01),25362e3);
    /*10*/public  CelestialBody neptune=  new CelestialBody(1.02413e26, new Vector3d(  4.382692942729203e+12,  -9.093501655486243e+11,  -8.227728929479486e+10), new Vector3d(  1.068410720964204e+03,  5.354959501569486e+03, -1.343918199987533e+02),24766e3);
   
    public CelestialBody probe;
    
    //Array of all planets
    public  CelestialBody[] U; 
    public  CelestialBody[][] U2;
   
    //Array of all planets image paths
    public String[] iconPath = {"/src/misc/sunIcon.png",
    							"/src/misc/mercuryIcon.png",
    							"/src/misc/venusIcon.png",
    							"/src/misc/earthIcon.png",
    							"/src/misc/moonIcon.png",
    							"/src/misc/marsIcon.png",
    							"/src/misc/jupiterIcon.png",
    							"/src/misc/saturnIcon.png",
    							"/src/misc/titanIcon.png",
    							"/src/misc/uranusIcon.png",
    							"/src/misc/neptuneIcon.png",
    							"/src/misc/craftIcon.png"};
    
    public String[] imagePath = {"/src/misc/sunScaled.png",
							     "/src/misc/mercuryScaled.png",
							     "/src/misc/venusScaled.png",
							     "/src/misc/earthScaled.png",
							     "/src/misc/moonScaled.png",
							     "/src/misc/marsScaled.png",
							     "/src/misc/jupiterScaled.png",
							     "/src/misc/saturnScaled.png",
							     "/src/misc/titanScaled.png",
							     "/src/misc/uranusScaled.png",
							     "/src/misc/neptuneScaled.png",
							     "/src/misc/craftIcon.png"};

    //In the universe initialization, we need to read the icon and image information into each celestial body.
    public Universe(int size, Vector3dInterface p0, Vector3dInterface v0)
    {
    	Vector3d p00 = (Vector3d) p0;
    	Vector3d v00 = (Vector3d) v0;
    	
    	probe = new CelestialBody(15000, p00, v00,2440e3);
    	
    	U = new CelestialBody[12];
    	U[0] = sun;
    	U[1] = mercury;
    	U[2] = venus;
    	U[3] = earth;
    	U[4] = moon;
    	U[5] = mars;
    	U[6] = jupiter;
    	U[7] = saturn;
    	U[8] = titan;
    	U[9] = uranus;
    	U[10] = neptune;
    	U[11] = probe;
    	    	
    	FileSystem fileSystem = FileSystems.getDefault();
		String dirPath = fileSystem.getPath("").toAbsolutePath().toString();
    	for(int i = 0; i < totalPlanets; i++)
        {       	
    		U[i].setIcon(dirPath.concat(iconPath[i]));
        	U[i].setImage(dirPath.concat(imagePath[i]));
        }  	
    	
    	U2 = new CelestialBody[totalBodies][size];

        for(int i = 0; i < U2.length; i++)
        {
        	U2[i][0] = U[i];
        }
    }
    
    //In the universe initialization, we need to read the icon and image information into each celestial body.
    public Universe()
    {
    	Vector3d p00 = new Vector3d(0,0,0);
    	Vector3d v00 = new Vector3d(0,0,0);
    	
    	probe = new CelestialBody(15000, p00, v00,2440e3);
    	
    	U = new CelestialBody[12];
    	U[0] = sun;
    	U[1] = mercury;
    	U[2] = venus;
    	U[3] = earth;
    	U[4] = moon;
    	U[5] = mars;
    	U[6] = jupiter;
    	U[7] = saturn;
    	U[8] = titan;
    	U[9] = uranus;
    	U[10] = neptune;
    	U[11] = probe;
    	    	
    	FileSystem fileSystem = FileSystems.getDefault();
		String dirPath = fileSystem.getPath("").toAbsolutePath().toString();
    	for(int i = 0; i < totalPlanets; i++)
        {       	
    		U[i].setIcon(dirPath.concat(iconPath[i]));
        	U[i].setImage(dirPath.concat(imagePath[i]));
        }  	
    	
    	U2 = new CelestialBody[totalBodies][0];

        for(int i = 0; i < U2.length; i++)
        {
        	U2[i][0] = U[i];
        }
    }
 
    public void convertToCelestialBody(StateInterface[] S)
    {
    	//Iterate through S
        for(int i=0; i< S.length; i++)
        {
            //Cast each object into a State object
            State temp = (State)S[i];

            //Iterate through each planet contained in a single state
            for(int j=0; j< temp.velocity.size(); j++)
            {
                U2[j][i] = U[j].copyOf(temp.velocity.get(j), temp.position.get(j), temp.time);
            }
        }
    }
    
    public CelestialBody[] accessUniverse()
    {
        return U;
    }

    public StateInterface initialState()
    {
        //Create two arraylists to generate the state
        ArrayList<Vector3d> velocity = new ArrayList<Vector3d>();
        ArrayList<Vector3d> position = new ArrayList<Vector3d>();

        for(int i=0; i< U.length; i++)
        {
            velocity.add(U[i].velocity);
            position.add(U[i].location);
        }
        return new State(velocity, position);
    }

    /**
     * Method to allow access to the masses of all Celestial bodies
     * @return An array of the celestial body masses in their respective positions.
     */
    public double[] accessMasses()
    {
        double[] masses = new double[U.length];

        for(int i=0; i< U.length; i++)
        {
            masses[i] = U[i].mass;
        }

        return masses;
    }
}
