package apps.rc2.engines;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;

public class VisualBowUtils 
{

	public static Set<String> getColorForImage(BufferedImage img) 
	{
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		Set<String> res = new TreeSet<String>();
		
		for(int y=0; y<Ry; y++)
		for(int x=0; x<Rx; x++)
		{
			Color c1 = new Color( img.getRGB(x, y) );
			String v1 = colorToString(c1); 
			res.add(v1);
		}
		
		return res;
	}
	
	public static String colorToString(Color c) 
	{
		int r = c.getRed()/16;
		int g = c.getGreen()/16;
		int b = c.getBlue()/16;
		return r + "/" + g + "/" + b;
	}
}
