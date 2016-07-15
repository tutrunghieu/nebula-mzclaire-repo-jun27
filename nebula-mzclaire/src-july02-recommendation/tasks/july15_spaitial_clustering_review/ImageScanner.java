package tasks.july15_spaitial_clustering_review;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageScanner 
{
	public static void scan(File f, PixelAction lf)
	throws Exception
	{
		BufferedImage img = ImageIO.read(f);
		scan(img, lf);
	}
		
	public static void scan(BufferedImage img, PixelAction lf) 
	{
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			lf.invokePixelAction(img, x, y);
		}

		return;
	}

	public static void scan(File f, PixelFilter p, PixelAction lf)
	throws Exception
	{
		BufferedImage img = ImageIO.read(f);
		scan(img, p, lf);
	}
		
	public static void scan(BufferedImage img, PixelFilter p, PixelAction lf) 
	{
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			if(p.invokePixelFiler(img, x, y) )
			lf.invokePixelAction(img, x, y);
		}

		return;
	}
}
