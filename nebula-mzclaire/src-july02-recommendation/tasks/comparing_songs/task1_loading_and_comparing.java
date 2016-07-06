package tasks.comparing_songs;

import java.io.File;

import apps.mzclaire.ClaireDataAccess;
import apps.mzclaire.ClaireSongMatcher;
import tasks.task3_comparing_songs_Thao.ClaireSong;

public class task1_loading_and_comparing {

	public static void main(String[] args)
	throws Exception
	{
		File f = ClaireDataAccess.start().getStockSongFolder();
		System.out.println(f);
		
		File f1 = ClaireDataAccess.start().getStockSongFile(0);
		File f2 = ClaireDataAccess.start().getStockSongFile(1);
		
		ClaireSong s1 =  ClaireDataAccess.start()
				.readJsonObject(f1, ClaireSong.class);   
		
		ClaireSong s2 =  ClaireDataAccess.start()
				.readJsonObject(f2, ClaireSong.class);   
		
		ClaireSongMatcher m = new ClaireSongMatcher();
//		double s11 = m.jaccardSimilarScore(s1, s1);
//		System.out.println(s11);
		
		double s12 = m.jaccardSimilarScore(s1, s2);
		System.out.println(s12);
	}

}
