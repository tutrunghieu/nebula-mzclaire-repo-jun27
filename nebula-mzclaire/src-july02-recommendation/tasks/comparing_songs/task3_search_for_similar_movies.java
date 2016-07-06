package tasks.comparing_songs;

import java.io.File;
import java.util.List;

import org.naebulae.writers.HtmlWriter;

import apps.mzclaire.ClaireDataAccess;
import apps.recom.ClaireMovie;
import apps.recom.ClaireSearchEngine;
import apps.recom.ClaireSearchResult;
import tasks.task3_comparing_movies_DucAnh.ClaireSearchEngineMovie;

public class task3_search_for_similar_movies {
	
	public static void main(String[] args) throws Exception 
	{
		File f = ClaireDataAccess.start().getStockMovieFolder();
		
		
		List<ClaireMovie> stocked = ClaireDataAccess.start().readJsonList(f, ClaireMovie.class);
		//stocked = stocked.subList(0, 1000);
		
		File qf = ClaireDataAccess.start()
				.getStockMovieFileForUrl("https://en.wikipedia.org/wiki/X-Men:_Apocalypse", ".jsmv");
		
		ClaireMovie q = ClaireDataAccess.start().readJsonObject(qf, ClaireMovie.class);
		
		ClaireSearchEngine<ClaireMovie> eng = new ClaireSearchEngineMovie();
		
		List<ClaireSearchResult<ClaireMovie>> results = eng.findSimilarItems(q, stocked);
		results = results.subList(0, 30);
		
		f = ClaireDataAccess.start().getDesktopFile("out1.html");
		
		eng.renderSearchResults(q, results,
				(x, out) -> showQuery(x, out),
				(x, out) -> showDocument(x, out),
				f);
	}		
	
	private static void showQuery(ClaireMovie x, HtmlWriter out) 
	{ 
		out.printStyles(".image { width: 95%; }",
				".red { color: red}",
				".box { display:inline-block; width: 10%; border: dashed 1px #aeaeae; vertical-align: top; margin: 5px; padding: 7px; }");
		
		out.println("<div class='box'>");
		out.image(x.m_poster, "image");
		out.println(x.m_directed_by + "<br>");
		out.println(x.m_cast + "<br>");
		out.println(x.m_country + "<br>");
		out.anchorTar("link", "https://en.wikipedia.org/wiki/X-Men:_Apocalypse");
		out.println("</div>");
	}
	
	private static void showDocument(ClaireSearchResult<ClaireMovie> x, HtmlWriter out) 
	{
		out.println("<div class='box'>");
		out.println("<span class=red>" + x.score + "</span>");
		out.image(x.document.m_poster, "image");
		out.println(x.document.m_directed_by + "<br>");
		out.println(x.document.m_cast + "<br>");
		out.println(x.document.m_country + "<br>");
		out.println("</div>");
	}	
		
//		File file = new File(System.getProperty("user.home") + "/Desktop/result.html");
//		file.getParentFile().mkdirs();
//		PrintWriter out = new PrintWriter(file);
//		out.println("<html><body>");
//		out.println("<h2>" + "Total result: " + results.size() + "</h2>");
//		for(ClaireSearchResult<ClaireMovie> rk: results)
//		{	
//			out.println("<p>" + rk.score + ": " + "<a href=\'" + rk.document.m_poster + "\'>" + rk.document.m_poster +"</a>"  + "</p>");
//		}
//		out.println("</body></html>");
//		out.close();
		
	

	
	
	
}
