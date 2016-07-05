package tasks.task3_comparing_movies_DucAnh;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import apps.mzclaire.ClaireDataAccess;
import apps.mzclaire.String2;
import apps.recom.ClaireMovie;
import apps.recom.ClaireSearchEngine;
import apps.recom.ClaireSearchResult;
import apps.recom.UniformRecommemder;
import apps.recom.UniformRecommemderMovie;

public class MovieNearestRecommendation {
	
	public static void main(String[] args) throws Exception {
		UniformRecommemder<ClaireMovie> uk = new UniformRecommemderMovie();
		uk.setStockFolder( ClaireDataAccess.start().getStockMovieFolder() );
		
		System.out.println( "Total file: " + uk.pickAllItems().size());

		ClaireMovie q = uk.pickItemByUrl(String2.identifier256("https://en.wikipedia.org/wiki/X-Men:_Apocalypse") + ".jsmv");
		
		List<ClaireMovie> movies = uk.pickAllItems();
		
		ClaireSearchEngine<ClaireMovie> eng = new ClaireSearchEngineMovie();
		
		List<ClaireSearchResult<ClaireMovie>> results = eng.findSimilarItems(q, movies);
		
		System.out.println("Total result: " + results.size());
		
		File file = new File(System.getProperty("user.home") + "/Desktop/result.html");
		file.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(file);
		out.println("<html><body>");
		out.println("<h2>" + "Total result: " + results.size() + "</h2>");
		for(ClaireSearchResult<ClaireMovie> rk: results)
		{	
			out.println("<p>" + rk.score + ": " + "<a href=\'" + rk.document.m_poster + "\'>" + rk.document.m_poster +"</a>"  + "</p>");
		}
		out.println("</body></html>");
		out.close();
		
	}
	
	
	
	
}
