package apps.recom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class UniformRecommemder<TYPE>
{
	private File dataFolder;

	public void setStockFolder(File f) 
	{
		dataFolder = f;
	}
	
	public Random coin = new Random(1997);
	
	public void setNanoCoin() 
	{
		int x = (int)(System.currentTimeMillis() % 1997);
		coin = new Random(x);
	} 
	

	public List<TYPE> pickItems(int kpar) throws Exception 
	{
		List<TYPE> res = new ArrayList<TYPE>();
		
		File[] files = dataFolder.listFiles();
		if(files == null) return res;
		
		
		while(true)
		{
			int rk = coin.nextInt( files.length );
			
			TYPE ik = readItem(files[rk]);
			res.add(ik);
			
			if(res.size() >= kpar) break;
		}
		
		return res;
	}
	
	public List<TYPE> pickAllItems() throws Exception 
	{
		List<TYPE> res = new ArrayList<TYPE>();
		File[] files = dataFolder.listFiles();
		
		for(File f: files) {
			TYPE ik = readItem(f);
			res.add(ik);
		}
		
		return res;
	}

	protected abstract TYPE readItem(File fk) throws Exception;


	public TYPE pickItemByUrl(String url) throws Exception {
		File[] files = dataFolder.listFiles();
		for(File f: files) {
			if(url.equals(f.getName())) {
				return readItem(f);
			}
		}
		return null;
	}


}
