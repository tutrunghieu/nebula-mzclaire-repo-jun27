package tasks.task3_comparing_songs_Thao;

import java.util.List;

import apps.mzclaire.ClaireDataAccess;
import tasks.task3_comparing_movies_DucAnh.ClassUtils;

public class task2_crawling_songs extends ClaireDataAccess
{
	public static void main(String[] args) 
	throws Exception
	{
		List<String> links = start().getSongCatalogLinks();
		
		for (String linkSong: links) {
			ClaireSong song = ClaireSongCrawler.start().crawlSongWithLink(linkSong);
			ClassUtils.printDeclared(song);
		}
		System.out.println("Finish crawling");
	}

}
