package tasks.task3_comparing_songs_Thao;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import apps.mzclaire.ClaireDataAccess;
import tasks.task3_comparing_movies_DucAnh.ClaireMovieCrawler;

public class ClaireSongCatalogCrawler extends ClaireMovieCrawler 
{
	public static ClaireSongCatalogCrawler start()
	{
		return new ClaireSongCatalogCrawler();
	}

	public List<String> crawListSongs(String url) 
	throws Exception
	{
		List<String> listSongs = new ArrayList<String>();
		
		Document d = Jsoup.parse(new URL(url), 3000);
		for(Element dk: d.getAllElements())
		{
			if( dk.tagName().equals("a")
					&& outerContextContains(dk, "li#; ul#; div#div-col columns column-width; div#mw-content-ltr; div#mw-body-content;") )
			{
				listSongs.add(dk.absUrl("href"));
			}
		}
		
		return listSongs;
	}

	public void crawlForLinks(String url) 
	throws Exception
	{
		Document d = Jsoup.parse(new URL(url), 3000);
		File f = ClaireDataAccess.start().getSongCatalogFile();
		FileWriter fileWrite = new FileWriter(f, true);
		for(Element dk: d.getAllElements())
		{
			if( dk.tagName().equals("a") && outerContextContains(dk, "th#; tr#; tbody#; table#wikitable sortable plainrowheaders; div#mw-content-ltr; "))
			{
				fileWrite.write(dk.absUrl("href") +"\n");
			}
		}
		fileWrite.close();
		return;
	}
}
