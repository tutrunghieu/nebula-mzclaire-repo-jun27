package apps.rc2.engines;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import apps.rc2.services.MapSorter;

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
		
		Set<String> colors = VisualBowUtils.getColorForImage(q);
		
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
		
		List<Map.Entry<String, Integer>> items = MapSorter.sortValueWithStream(hist);
	
		List<String> res1 = new ArrayList<String>();
		
		for(Map.Entry<String, Integer> ik: items)
		{
			System.out.println(this.source.getAbsolutePath() + "/" + ik.getKey() + " >> " + ik.getValue());
			res1.add(ik.getKey());
		}
		
		return res1;
	}



	private Map<String, List<String>> createIndex(File f) 
	{
		Map<String, List<String>> res = new TreeMap<String, List<String>>();
		
		int cnt = 0;
		for(File fk: f.listFiles())
		try
		{
			System.out.println("Indexing " + fk);
			Set<String> colors = VisualBowUtils.getColorForImage(ImageIO.read(fk) );
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

	
}
