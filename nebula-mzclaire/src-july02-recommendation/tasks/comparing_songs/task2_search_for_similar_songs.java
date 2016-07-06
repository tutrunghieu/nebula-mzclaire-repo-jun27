package tasks.comparing_songs;

import java.io.File;
import java.util.List;

import org.naebulae.writers.HtmlWriter;

import apps.mzclaire.ClaireDataAccess;
import apps.recom.ClaireSearchEngine;
import apps.recom.ClaireSearchResult;
import tasks.task3_comparing_songs_Thao.ClaireSong;

public class task2_search_for_similar_songs {

	public static void main(String[] args) 
	throws Exception
	{
		File f = ClaireDataAccess.start().getStockSongFolder();
		List<ClaireSong> stocked = ClaireDataAccess.start().readJsonList(f, ClaireSong.class);
		stocked = stocked.subList(0, 10);
				
		ClaireSearchEngine<ClaireSong> eng = new ClaireSearchEngineSong();
		
		ClaireSong q = stocked.get(1);
		
		List<ClaireSearchResult<ClaireSong>> results = eng
				.findSimilarItems(q, stocked);
		
		f = ClaireDataAccess.start().getDesktopFile("out1.html");
		
		eng.renderSearchResults(q, results,
				(x, out) -> showQuery(x, out),
				(x, out) -> showDocument(x, out),
				f);
	}

	private static void showQuery(ClaireSong x, HtmlWriter out) 
	{ 
		out.h1(x.s_title); 
		out.println(x.s_genre + "<br>");
		out.println(x.s_length + "<br>");
		out.println(x.s_producer + "<br>");
	}
	
	private static void showDocument(ClaireSearchResult<ClaireSong> x, HtmlWriter out) 
	{
		out.println("<div style='display:inline-block; width: "
				+ "300px; border: dashed 1px #aeaeae; vertical-align: top; margin: 5px; padding: 7px;'>");
		out.println(x.score + "<br><b>" + x.document.s_title + "</b><br>");
		out.println(x.document.s_genre + "<br>");
		out.println(x.document.s_length + "<br>");
		out.println(x.document.s_producer + "<br>");
		out.println("</div>");
	}
}
