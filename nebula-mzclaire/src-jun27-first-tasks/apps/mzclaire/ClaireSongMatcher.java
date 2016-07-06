package apps.mzclaire;

import java.lang.reflect.Field;

import apps.mzclaire.jaccard.JaccardEngineBow;
import tasks.task3_comparing_songs_Thao.ClaireSong;

public class ClaireSongMatcher extends JaccardObjectMatcher<ClaireSong> 
{
	public static boolean DEBUG = false;

	@Override
	public double jaccardSimilarScore(ClaireSong s1, ClaireSong s2) 
	throws Exception
	{
		double s = 0;
		int n = 0;
		
		for(Field fj: s1.getClass().getFields())
		{
			Object v1 = fj.get(s1);
			Object v2 = fj.get(s2);
			s += jaccardSimilarScore(v1, v2, fj);
			n++;
		}
		
		return s / (n==0 ? 1 : n);
	}
	
	private JaccardEngineBow eng = new JaccardEngineBow();

	private double jaccardSimilarScore(Object v1, Object v2, Field fj) 
	{
		String s1 = (v1==null ? "" : v1.toString());
		String s2 = (v2==null ? "" : v2.toString());
		
		
		double s = eng.jaccardIndex(s1, s2);
		
		if(DEBUG)
		{
			System.out.println("--------" + fj.getName() + ":" + s);
			System.out.println(s1);
			System.out.println(s2);
		}
		
		return s;
	}


}
