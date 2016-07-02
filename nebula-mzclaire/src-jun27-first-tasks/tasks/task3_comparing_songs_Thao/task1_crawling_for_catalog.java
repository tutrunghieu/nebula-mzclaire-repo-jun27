package tasks.task3_comparing_songs_Thao;

public class task1_crawling_for_catalog 
{
	public static void main(String[] args) 
	throws Exception
	{
		String url = "https://en.wikipedia.org/wiki/Lists_of_songs";
		
		ClaireSongCatalogCrawler.start().crawlForLinks(url);

	}

}
