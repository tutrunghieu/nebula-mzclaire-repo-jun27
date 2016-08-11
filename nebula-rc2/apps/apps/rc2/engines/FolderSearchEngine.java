package apps.rc2.engines;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import apps.rc2.services.MapSorter;

public class FolderSearchEngine extends SearchEngine 
{
	private File indexFolder = new File("d:/nebula-rc2/index/word-to-doc");
	private File usedFolder = new File("d:/nebula-rc2/index/used");
	
	public FolderSearchEngine() 
	{
	}

	public void indexFilesInFolder(File f) 
	{
		for(File fk: f.listFiles())
		try
		{
			System.out.println("Indexing " + fk);
			
			String id = IndexFileUtils.getFileIdentifier(fk);
			
			File fk_used = new File(usedFolder.getAbsolutePath() + "/" + id);
			if(fk_used.exists()) continue;
			
			Set<String> colors = VisualBowUtils.getColorForImage( ImageIO.read(fk) );
			for(String cj: colors) addPair(cj, fk.getAbsolutePath());

			IndexFileUtils.createFile(fk_used);
		}
		catch(Exception xp) {}
	}


	private void addPair(String cj, String dk) 
	throws Exception
	{
		File fj = new File(indexFolder.getAbsolutePath() + "/" + cj + ".txt");
		
		PrintWriter out = IndexFileUtils.openWriterForAppend(fj);
		out.println(dk);		
		out.close();
	}

	public List<String> findSimilarImages(URL url, int kpar)
	throws Exception
	{
		BufferedImage q = ImageIO.read(url);
		return findSimilarImages(q, kpar);
	}
	
	public List<String> findSimilarImages(File url, int kpar)
	throws Exception
	{
		BufferedImage q = ImageIO.read(url);
		return findSimilarImages(q, kpar);
	}
	
	public List<String> findSimilarImages(BufferedImage q, int kpar)
	throws Exception {
		
		Set<String> colors = VisualBowUtils.getColorForImage(q);
		
		Map<String, Integer> hist = new TreeMap<String, Integer>();
		
		for(String cj: colors)
		{
			System.out.println("Processing color " + cj);
			
			List<String> fileList = IndexFileUtils.getFilesForColor(new File( indexFolder.getAbsolutePath() + "/" + cj + ".txt" ));
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
//			System.out.println(ik.getValue() + ": " + ik.getKey() );
			res1.add(ik.getKey());
			if(res1.size() >= kpar) break;
		}
		
		return res1;		
	}
	
	public static void main(String[] args) 
	throws Exception
	{
		FolderSearchEngine eng = new FolderSearchEngine();
		eng.indexFilesInFolder( new File("D:/nebula-rc2/images")  );
		
		File f = new File("D:/nebula-rc2/images/tallbuilding_urban983.jpg");
		
		for(String fj: eng.findSimilarImages(f, 30)) System.out.println(fj);
	}

	
}
