package apps.rc2.engines;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;


//Download data from http://people.csail.mit.edu/torralba/code/spatialenvelope/spatial_envelope_256x256_static_8outdoorcategories.zip
public class SearchEngine 
{
	private static ImageComparator img = new ImageComparator();
	
	private double jaccardSimilarScore(BufferedImage q, BufferedImage d)
	{
		return img.jaccardSimilarScore(q, d);
	}	

	
	public int MAX_INDEX = 30;
	public File source = new File("D:/nebula-rc2/images");

	public List<String> findSimilarImages_old(String q, int kpar)
	{
		List<String> res = new ArrayList<String>();
		
		for(int k=0; k<kpar; k++) res.add(q);
		
		return res;
	}
	
	
	public List<String> findSimilarImages(String str, int kpar)
	throws Exception
	{
		BufferedImage q = ImageIO.read(new URL(str));
		
		Map<Double, List<File>> res = new TreeMap<Double, List<File>>(Collections.reverseOrder()); 
		
		for(File sk: source.listFiles())
		try
		{
			System.out.println( sk.getAbsolutePath() );
			double vk = jaccardSimilarScore(q, ImageIO.read(sk));
			
			List<File> lk = res.get(vk);
			if(lk == null) res.put(vk, lk = new ArrayList<File>());
			
			lk.add(sk);
			
			if(res.size() > MAX_INDEX) break;
		}
		catch(Exception xp) {}
		
		List<String> res1 = new ArrayList<String>();
		for(Double vk: res.keySet())
		{
			System.out.println(vk);
			
			for(File fk: res.get(vk)) 
			{
				res1.add( fk.getName() );
				if(res1.size() > kpar) break;
			}
			
			if(res1.size() > kpar) break;
		}
		
		return res1;
	}


}
