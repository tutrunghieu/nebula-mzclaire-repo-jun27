package apps.mzclaire;

public abstract class JaccardObjectMatcher<T> 
{
	public abstract double jaccardSimilarScore(T s1, T s12) throws Exception; 

}
