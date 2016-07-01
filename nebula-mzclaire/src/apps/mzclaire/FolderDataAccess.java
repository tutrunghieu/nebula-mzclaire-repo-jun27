package apps.mzclaire;

import java.io.File;

public class FolderDataAccess 
{
	
	public File getCachedFolder() 
	{
		return new File(System.getProperty("user.home") + "/data-mzclaire-cached"); 	
	}
	
	public File getCachedFile(String url) 
	{
		String id = String2.identifier256(url);
		return new File(System.getProperty("user.home") + "/data-mzclaire-cached/" + id); 	
	}
	
	public File getCachedFile(String url, String ext) 
	{
		String id = String2.identifier256(url);
		return new File(System.getProperty("user.home") + "/data-mzclaire-cached/" + id + ext); 	
	}
	
	
	public File getStockMovieFolder() 
	{
		return new File(System.getProperty("user.home") + "/data-mzclaire-movies"); 	
	}

	
	public File getStockMovieFileForUrl(String url) 
	{
		String id = String2.identifier256(url);
		return new File(System.getProperty("user.home") + "/data-mzclaire-movies/" + id); 	
	}

	public File getStockMovieFileForUrl(String url, String ext) 
	{
		String id = String2.identifier256(url);
		return new File(System.getProperty("user.home") + "/data-mzclaire-movies/" + id + ext); 	
	}
	
	
	
	public File getStockSongFolder() 
	{
		return new File(System.getProperty("user.home") + "/data-mzclaire-songs"); 	
	}
			
	public File getStockSongFileForUrl(String url) 
	{
		String id = String2.identifier256(url);
		return new File(System.getProperty("user.home") + "/data-mzclaire-songs/" + id); 	
	}

	public File getStockSongFileForUrl(String url, String ext) 
	{
		String id = String2.identifier256(url);
		return new File(System.getProperty("user.home") + "/data-mzclaire-songs/" + id + ext); 	
	}
	

}
