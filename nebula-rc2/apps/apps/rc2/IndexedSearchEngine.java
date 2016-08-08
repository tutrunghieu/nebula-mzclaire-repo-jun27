package apps.rc2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class IndexedSearchEngine extends SearchEngine 
{
	private Map<String, List<String>> index = null;

	public IndexedSearchEngine(File f) 
	{
		this.source = f;
	}
	
	public List<String> findSimilarImages(String str, int kpar)
	throws Exception
	{
		if(index == null) index = createIndex(source);
		
		BufferedImage q = ImageIO.read(new URL(str));
		
		Set<String> colors = getColorForImage(q);
		
		Map<String, Integer> hist = new TreeMap<String, Integer>();
		
		for(String cj: colors)
		{
			System.out.println("Processing color " + cj);
			
			List<String> fileList = index.get(cj);
			if(fileList==null) continue;
			
			for(String fk: fileList)
			{
				Integer ck = hist.get(fk);
				hist.put(fk, ck==null ? 1 : ck+1);
			}
		}
		
		List<Map.Entry<String, Integer>> items = hist.entrySet().stream()
		.sorted((x,y) -> sortedByCount(x, y))
		.collect(Collectors.toList());
	
		List<String> res1 = new ArrayList<String>();
		
		for(Map.Entry<String, Integer> ik: items)
		{
			System.out.println(this.source.getAbsolutePath() + "/" + ik.getKey() + " >> " + ik.getValue());
			res1.add(ik.getKey());
		}
		
		return res1;
	}

	
	private int sortedByCount(Entry<String, Integer> x, Entry<String, Integer> y) 
	{
		int xk = x.getValue();
		int yk = y.getValue();
		return yk - xk;
	}

	private Map<String, List<String>> createIndex(File f) 
	{
		Map<String, List<String>> res = new TreeMap<String, List<String>>();
		
		int cnt = 0;
		for(File fk: f.listFiles())
		try
		{
			System.out.println("Indexing " + fk);
			Set<String> colors = getColorForImage(ImageIO.read(fk) );
			for(String cj: colors) add(res, cj, fk.getName());
			
			if(cnt++ >= 200) break;
		}
		catch(Exception xp) {}
		
		return res;
	}

	private void add(Map<String, List<String>> res, String cj, String name) 
	{
		List<String> lj = res.get(cj);
		if(lj == null) res.put(cj, lj = new ArrayList<String>());
		lj.add(name);
	}

	private Set<String> getColorForImage(BufferedImage img) 
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
	
	private String colorToString(Color c) 
	{
		int r = c.getRed()/16;
		int g = c.getGreen()/16;
		int b = c.getBlue()/16;
		return r + "/" + g + "/" + b;
	}
	
}
