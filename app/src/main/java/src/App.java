package src;

import java.io.IOException;
import src.univ.CelestialBody;
import src.visu.SetupMenu;

public class App 
{
	public static void main(String[] args) 
    {

		try 
		{

			CelestialBody[] U=new CelestialBody[0];
			new SetupMenu(U);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
}
