package tasks.task3_comparing_songs_Thao;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import apps.mzclaire.ClaireDataAccess;
import apps.recom.ClaireMovie;
import tasks.task3_comparing_movies_DucAnh.ClaireMovieCrawler;

public class ClaireSongCatalogCrawler extends ClaireMovieCrawler 
{
	public static ClaireSongCatalogCrawler start()
	{
		return new ClaireSongCatalogCrawler();
	}

	public void crawlForLinks(String url) 
	throws Exception
	{
		Document d = Jsoup.parse(new URL(url), 3000);
		for(Element dk: d.getAllElements())
		{
			if( dk.tagName().equals("a")
					&& outerContextContains(dk, "div#mw-content-ltr; div#mw-body-content; ") )
			{
				System.out.println( dk.text() + " -> " + dk.absUrl("href"));
			}
			
		} //for each element in Jsoup

		return;
	}

}
