package tasks.july15_spaitial_clustering_review;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

import org.naebulae.access.FolderAccess;

public class task1_divide_points_groups  
{

	public static void main(String[] args) throws Exception 
	{
		File f = FolderAccess.start().getDesktopFile("auto-divide-4.png");
		
		
//		PixelFilter f1 = (img, x1, y1) -> { int ck = img.getRGB(x1, y1); return ck != w && ck != b; };
		PixelAction f2 = (img, x, y) -> { System.out.println(x); };
		
		PixelGrouper g = new PixelGrouper(); 
		ImageScanner.scan(f, (img, x, y) -> addPixel(img, x, y, g) );
	}

	private static int w = Color.white.getRGB();
	
	private static void addPixel(BufferedImage img, int x, int y, PixelGrouper g) 
	{
		int cxy = img.getRGB(x, y);
		if(cxy == w) return;
		
		if( g.used(x, y) ) return;
		g.markAsUsed(x, y);
		
		Stack<Point> todo = new Stack<Point>();
		todo.add(new Point(x, y));
		
		int l = g.newGroup(x, y);
		while(!todo.isEmpty()) 
		{
									
		}
		
	}
	
	

}
