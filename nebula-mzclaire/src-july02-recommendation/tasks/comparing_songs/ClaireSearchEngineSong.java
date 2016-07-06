package tasks.comparing_songs;

import apps.mzclaire.ClaireSongMatcher;
import apps.recom.ClaireSearchEngine;
import tasks.task3_comparing_songs_Thao.ClaireSong;

public class ClaireSearchEngineSong extends ClaireSearchEngine<ClaireSong> {

	private ClaireSongMatcher matcher = new ClaireSongMatcher();
	
	@Override
	public double jaccardIndex(ClaireSong sk, ClaireSong q) throws Exception 
	{
		return matcher.jaccardSimilarScore(sk, q);
	}

}
