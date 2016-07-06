package tasks.task3_comparing_songs_Thao;

import java.util.List;

public class task1_crawling_for_catalog 
{
	public static void main(String[] args) 
	throws Exception
	{
		String url = "https://en.wikipedia.org/wiki/Lists_of_songs";
		
		List<String> listSongs = ClaireSongCatalogCrawler.start().crawListSongs(url);
		
		for (String urlList: listSongs) {
			ClaireSongCatalogCrawler.start().crawlForLinks(urlList);
		}
		System.out.println("Finish crawling");
	}

}
