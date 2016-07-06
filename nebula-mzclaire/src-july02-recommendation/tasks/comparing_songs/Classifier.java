package tasks.comparing_songs;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Classifier<S, T> 
{
	private Map<S, T> examples = new LinkedHashMap<S, T>(); 

	public void train(S sk, T tk) 
	{
		examples.put(sk, tk);
	}

	public T predict(S q) 
	{
		double dres = -Double.MAX_VALUE;
		T tres = null;
		
		for(S nk: examples.keySet())
		{
			double dk = jaccardSimilarScore(nk, q);
			if(dk > dres) { dres = dk; tres = examples.get(nk); }
			
			System.out.println(dk + ":" + nk);
		}
		
		return tres;
	}

	public abstract double jaccardSimilarScore(S nk, S q); 

}
