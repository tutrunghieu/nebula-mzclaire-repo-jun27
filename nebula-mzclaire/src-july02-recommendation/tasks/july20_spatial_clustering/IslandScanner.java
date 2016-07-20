package tasks.july20_spatial_clustering;

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

public class IslandScanner 
{
	public static IslandScanner start() 
	{
		return new IslandScanner();
	}
	
	public BufferedImage labelAll(File f)
	throws Exception
	{
		return labelAll(ImageIO.read(f));
	}

	private BufferedImage image; 
	private Set<Point> used = new HashSet<Point>(); 
	
	public boolean isBackground(int x, int y) 
	{
		return image.getRGB(x, y) ==Color.white.getRGB();
	}
	
	public boolean outsideImage(int x, int y) 
	{
		return x<0 || x>=image.getWidth() || y<0 || y>=image.getHeight();
	}

	
	public BufferedImage labelAll(BufferedImage img) 
	{
		image = img;
		
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			if(isBackground(x, y) || used.contains(new Point(x, y))) continue;
			
			System.out.println("Starting component " + x + ", " + y);
			
			labelOne(x, y, nextIntColor());
		}

		return image;
	}

	
	private void labelOne(int x, int y, int l) 
	{
		Stack<Point> todo = new Stack<Point>();
		todo.add(new Point(x, y));
		
		while(!todo.isEmpty()) 
		{
			Point p = todo.pop();
			if(used.contains(p) ) continue;
				
			image.setRGB(p.x, p.y, l);				
			used.add(p);
			
			for( Point nj: neigbors(p) ) todo.add(nj);
		}

		return;
	}

	private List<Point> neigbors(Point p) 
	{
		List<Point> res = new ArrayList<Point>();
		
		int cx = 7, cy = 7;
		
		for(int dx=-cx; dx<=cx; dx++)
		for(int dy=-cy; dy<=cy; dy++)
		{
			if(dx==0 && dy==0) continue;
			int x = p.x + dx;
			int y = p.y + dy;
			
			if( outsideImage(x, y) || isBackground(x, y) ) continue;
			
			res.add(new Point(x, y) );
		}
		
		return res;
	}
	

	public static Random coin = new Random(197);
	
	public int nextIntColor() 
	{
		int r = coin.nextInt(256);
		int g = coin.nextInt(256);
		int b = coin.nextInt(256);
		return new Color(r, g, b).getRGB();
	}



	
}
