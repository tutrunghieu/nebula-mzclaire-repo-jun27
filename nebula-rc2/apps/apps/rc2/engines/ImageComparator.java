package apps.rc2.engines;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ImageComparator {

	private BufferedImage __left;
	private Map<String, Integer> __leftSet = null;
	
	private BufferedImage __right;
	private Map<String, Integer> __rightSet = null;

	public double jaccardSimilarScore(BufferedImage l, BufferedImage r) 
	{
		if(__left != l) { __left = l; __leftSet = gramSet(l); } 
		if(__right != r) { __right = r; __rightSet = gramSet(r); }
		
		Map<String, Integer> s1 = __leftSet;
		Map<String, Integer> s2 = __rightSet;
			
		int c = 0, s = 0;
		
		
		Set<String> cm = new TreeSet<String>();
		cm.addAll(s1.keySet());
		cm.addAll(s2.keySet());
		
		for(String x: cm)
		{
			Integer x1 = s1.get(x);
			if(x1 == null) x1 = 0;
			
			Integer x2 = s2.get(x);
			if(x2 == null) x2 = 0;
			
			c += Math.min(x1, x2);
			s += Math.max(x1, x2);
		}
			
		return c/(double)(s==0 ? 1 : s);
	}

	private Map<String, Integer> gramSet(BufferedImage img) 
	{
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		Map<String, Integer> res = new TreeMap<String, Integer>();
		
		for(int y=0; y<Ry; y++)
		for(int x=0; x<Rx; x++)
		{
			Color c1 = new Color( img.getRGB(x, y) );
			String v1 = colorToString(c1); 
			add(v1, res);
		}
		
		return res;
	}

	private String colorToString(Color c) 
	{
		int r = c.getRed()/16;
		int g = c.getGreen()/16;
		int b = c.getBlue()/16;
		return r + "/" + g + "/" + b;
	}

	private void add(String v, Map<String, Integer> res) 
	{
		Integer c = res.get(v);
		res.put(v, c==null ? 1 : c+1);
	}	
}
