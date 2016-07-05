package apps.recom;

import java.util.List;

public class task3_nearest_recommendation 
{
	public static void main(String[] args) 
	throws Exception
	{
		List<String> stocked = RandUtils
				.buildList("action", "drama", "comedy", "comic", "active");
		
		ClaireSearchEngine<String> eng = new ClaireSearchEngineString();
		
		List<ClaireSearchResult<String>> results = eng.findSimilarItems("activ", stocked);
		
		for(ClaireSearchResult<String> rk: results)
		{
			System.out.println(rk.score + ": " + rk.document);
		}
		
	}


}
