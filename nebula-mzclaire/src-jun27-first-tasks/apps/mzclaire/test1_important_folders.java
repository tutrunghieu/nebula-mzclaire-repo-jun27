package apps.mzclaire;

import java.io.File;

public class test1_important_folders 
{
	
	public static void main(String[] args) 
	{
		File f = ClaireDataAccess.start().getStockMovieFolder();
		System.out.println(f.getAbsolutePath());
		
		f = ClaireDataAccess.start().getStockMovieFileForUrl("http://www.google.com");
		System.out.println(f.getAbsolutePath());
		

		f = ClaireDataAccess.start().getStockMovieFileForUrl("http://www.google.com", ".json");
		System.out.println(f.getAbsolutePath());
		
		
		f = ClaireDataAccess.start().getStockSongFolder();
		System.out.println(f.getAbsolutePath());
		
		f = ClaireDataAccess.start().getStockSongFileForUrl("http://www.google.com");
		System.out.println(f.getAbsolutePath());
		
		f = ClaireDataAccess.start().getStockSongFileForUrl("http://www.google.com", ".json");
		System.out.println(f.getAbsolutePath());
	}

}
