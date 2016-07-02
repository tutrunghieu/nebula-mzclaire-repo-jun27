package apps.recom;

import tasks.task1_comparing.JaccardTitleMatcher;

public class ClaireSearchEngineString extends ClaireSearchEngine<String> 
{
	JaccardTitleMatcher eng = new JaccardTitleMatcher();

	@Override
	public double jaccardIndex(String sk, String q) 
	{
		return eng.jaccardSimilarScore(sk, q);
	}

}
