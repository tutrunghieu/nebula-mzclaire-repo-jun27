package apps.recom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class ClaireSearchEngine<T> 
{
	public List<ClaireSearchResult<T>> findSimilarItems(T q, List<T> s) 
	throws Exception
	{
		Map<Double, List<T>> sort = new TreeMap<Double, List<T>>(Collections.reverseOrder()); 
		
		for(T sk: s)
		{
			double dk = jaccardIndex(sk, q);
			
			List<T> lk = sort.get(dk);
			if(lk == null) sort.put(dk, lk = new ArrayList<T>());
			
			lk.add(sk);
		}
		
		List<ClaireSearchResult<T>> res = new ArrayList<ClaireSearchResult<T>>();
		
		for(Double nk: sort.keySet())
		for(T vk: sort.get(nk))
		{
			ClaireSearchResult<T> rk = new ClaireSearchResult<T>();
			rk.score = nk;
			rk.document = vk;
			
			res.add(rk);
		}
		
		return res;
	}

	public abstract double jaccardIndex(T sk, T q) throws Exception;
}

