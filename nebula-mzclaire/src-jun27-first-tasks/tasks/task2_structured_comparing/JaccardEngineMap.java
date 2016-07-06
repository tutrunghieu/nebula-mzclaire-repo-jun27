package tasks.task2_structured_comparing;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import apps.mzclaire.jaccard.JaccardEngine;
import apps.mzclaire.jaccard.JaccardEngineBow;

public class JaccardEngineMap extends JaccardEngine 
{
	public boolean bin2bin = true;
	
	public double jaccardIndex(Map<String, Object> m1, Map<String, Object> m2) 
	{
		if(bin2bin) return jaccardIndexBin(m1, m2);
		else return jaccardIndexCross(m1, m2);
	}

	private double jaccardIndexBin(Map<String, Object> m1, Map<String, Object> m2) 
	{
		Set<String> keys = new TreeSet<String>();
		keys.addAll(m1.keySet());
		keys.addAll(m2.keySet());
		
		double s = 0;
		
		for(String k: keys)
		{
			double sk = jaccardIndexForField(k, m1.get(k), m2.get(k));
			System.out.println(k + " -- " + sk);
			s += sk;
		}
		
		return s / keys.size();
	}
	
	private double jaccardIndexForField(String k, Object a, Object b)
	{
		JaccardEngine inner = new JaccardEngineBow();
		return inner.jaccardIndex(a.toString(), b.toString());
	}

	private double jaccardIndexCross(Map<String, Object> m1, Map<String, Object> m2) 
	{
		// TODO Auto-generated method stub
		return 0;
	}


}
