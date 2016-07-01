package apps.mzclaire;

import java.io.File;

public class ClaireDataAccess  
{
	public static ClaireDataAccess start()
	{
		return new ClaireDataAccess();
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
