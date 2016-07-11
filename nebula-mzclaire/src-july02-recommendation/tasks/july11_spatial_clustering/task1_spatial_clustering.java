package tasks.july11_spatial_clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;
import org.naebulae.util.Joiner;

public class task1_spatial_clustering {

	public static void main(String[] args) 
	throws Exception
	{
		File f = FolderAccess.start().getDesktopFile("classification-1.png");
		
		
		BufferedImage img = ImageIO.read(f);
		
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		Set<Integer> s = new TreeSet<Integer>();
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			int cxy = img.getRGB(x, y);
			s.add(cxy);
		}
	
		for(int sk: s)
		{
			Color ck = new Color(sk);
			
			System.out.println(Joiner.start(":")
					.join(ck.getRed(), ck.getGreen(), ck.getBlue()));
			
		}
		return;
	}

}
