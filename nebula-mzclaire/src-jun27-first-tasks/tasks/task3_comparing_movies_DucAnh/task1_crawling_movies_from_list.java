package tasks.task3_comparing_movies_DucAnh;

import java.io.File;
import java.util.List;

import apps.mzclaire.ClaireDataAccess;

public class task1_crawling_movies_from_list extends ClaireDataAccess 
{
	
	public static void main(String[] args) throws Exception 
	{
		File f = ClaireDataAccess.start().getStockMovieFolder();
		if (!f.exists()) { f.mkdir(); }
		
		List<String> links = start().getMovieCatalogLinks();
		
		for(String lk: links.subList(9000, links.size() - 1))
		{
			ClaireMovieCrawler.start().crawlMovieInside(lk);
			//ClassUtils.printDeclared(mk);
		}
		
	}
	
	
}
