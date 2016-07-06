package apps.mzclaire.jaccard;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class JaccardEngineBow extends JaccardEngine 
{
	
	public double jaccardIndex(String d1, String d2) 
	{
		Map<String, Integer> s1 = toMap(d1);
		Map<String, Integer> s2 = toMap(d2);
		
		return jaccardIndex(s1, s2);
	}

	public Map<String, Integer> toMap(String s) 
	{
		Map<String, Integer> res = new TreeMap<String, Integer>();
		
		for(String sk: s.split("\\s+"))
		{
			String nk = sk.toLowerCase();
			Integer ck = res.get(nk);
			res.put(nk, (ck==null ? 1 : ck+1) );
		}
				
		return res;
	}

	//https://en.wikipedia.org/wiki/Jaccard_index
	//https://en.wikipedia.org/wiki/Jaccard_index#Generalized_Jaccard_similarity_and_distance
	private double jaccardIndex(Map<String, Integer> s1, Map<String, Integer> s2) 
	{
		int c = 0, s = 0;
		
		Set<String> cm = new TreeSet<String>();
		cm.addAll(s1.keySet());
		cm.addAll(s2.keySet());
		
		for(String x: cm)
		{
			Integer x1 = s1.get(x);
			if(x1 == null) x1 = 0;
			
			Integer x2 = s2.get(x);
			if(x2 == null) x2 = 0;
			
			c += Math.min(x1, x2);
			s += Math.max(x1, x2);
		}
			
		return c/(double)(s==0 ? 1 : s);
	}

}
