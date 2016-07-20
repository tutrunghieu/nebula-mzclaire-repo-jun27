package tasks.july15_spaitial_clustering_review;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;

public class task1_divide_points_groups  
{

	public static void main(String[] args) throws Exception 
	{
		File f = FolderAccess.start().getDesktopFile("auto-divide-4.png");
		
		BufferedImage img = scan( ImageIO.read(f) );
		
		File rf = FolderAccess.start().getDesktopFile("out1.png");  
		ImageIO.write(img, "png", rf);
		
		FolderAccess.start().showFile(rf);
	}
	
	private static int w = Color.white.getRGB();
	
	
	public static BufferedImage scan(BufferedImage img) 
	{
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		Set<Point> used = new HashSet<Point>(); 
		
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			int cxy = img.getRGB(x, y);
			if(cxy == w) continue;
			
			System.out.println("Starting component " + x + ", " + y);
			label(img, x, y, nextIntColor(), used);
		}

		return img;
	}	

	private static Random coin = new Random(197);
	
	private static int nextIntColor() 
	{
		int r = coin.nextInt(256);
		int g = coin.nextInt(256);
		int b = coin.nextInt(256);
		return new Color(r, g, b).getRGB();
	}


	private static void label(BufferedImage img, int x, int y, int l, Set<Point> used) 
	{
		Stack<Point> todo = new Stack<Point>();
		todo.add(new Point(x, y));
		
		while(!todo.isEmpty()) 
		{
			Point p = todo.pop();
			if(used.contains(p) ) continue;
				
			img.setRGB(p.x, p.y, l);				
			used.add(p);
			
			for( Point nj: neigbors(img, p) ) todo.add(nj);
		}

		return;
	}

	private static List<Point> neigbors(BufferedImage img, Point p) 
	{
		List<Point> res = new ArrayList<Point>();
		
		int cx = 7, cy = 7;
		
		for(int dx=-cx; dx<=cx; dx++)
		for(int dy=-cy; dy<=cy; dy++)
		{
			if(dx==0 && dy==0) continue;
			int x = p.x + dx;
			int y = p.y + dy;
			if(x<0 || x>=img.getWidth() || y<0 || y>=img.getHeight()) continue;
			if( img.getRGB(x, y) == w) continue;
			
			res.add(new Point(x, y));
		}
		
		return res;
	}
	
	

}
