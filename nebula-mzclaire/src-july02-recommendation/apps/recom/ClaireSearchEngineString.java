package apps.recom;

import apps.mzclaire.jaccard.JaccardTitleMatcher;

public class ClaireSearchEngineString extends ClaireSearchEngine<String> 
{
	JaccardTitleMatcher eng = new JaccardTitleMatcher();

	@Override
	public double jaccardIndex(String sk, String q) 
	{
		return eng.jaccardSimilarScore(sk, q);
	}

}
