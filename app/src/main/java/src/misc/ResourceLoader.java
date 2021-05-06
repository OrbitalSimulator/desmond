package src.misc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class ResourceLoader 
{
	
	public static Icon getIcon(String name)
	{
		FileSystem fileSystem = FileSystems.getDefault();
		String systemPath = fileSystem.getPath("").toAbsolutePath().toString();	
		String appPath = "/src/main/java/src/misc/";
		String path = systemPath + appPath + name;
		BufferedImage image;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			image = null;
			System.out.println(name + " not found");
			e.printStackTrace();
		}
		return new ImageIcon(image);
	}
}
