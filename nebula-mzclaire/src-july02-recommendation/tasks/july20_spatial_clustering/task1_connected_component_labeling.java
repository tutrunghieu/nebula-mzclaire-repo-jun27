package tasks.july20_spatial_clustering;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;

public class task1_connected_component_labeling  
{

	public static void main(String[] args) throws Exception 
	{
		File f = FolderAccess.start().getDesktopFile("auto-divide-4.png");
		
		BufferedImage img = IslandScanner.start().labelAll(f);
		
		File rf = FolderAccess.start().getDesktopFile("out1.png");  
		ImageIO.write(img, "png", rf);
		FolderAccess.start().showFile(rf);
	}
	


}
