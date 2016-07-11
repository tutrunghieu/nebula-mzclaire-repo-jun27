package tasks.july08_knn_clustering;

import java.util.Map;
import java.util.TreeMap;

import apps.mzclaire.jaccard.JaccardEngineBow;

public class ClusterStringHenry  extends Cluster<String>
{
	private Map<String, Double> mean = new TreeMap<String, Double>();
	
	public void add(String pk) 
	{
		members.add(pk);				
	}

	public void average() 
	{
		if (members.size() ==0 ) return;
		
		int n = 0;
		for(String mk: members)
		for(String wj: mk.split("\\s+"))
		{
			Double cj = mean.get(wj);
			mean.put(wj, cj==null ? 1 : cj+1);
			n++;
		}
		
		if(n==0)n=1;
		for(String nk: mean.keySet())
		{
			Double ck = mean.get(nk);
			mean.put(nk, ck/n);
		}
		
		System.out.println("Center == " + mean.keySet());
	}
	
	public JaccardEngineBow jaccard = new JaccardEngineBow(); 
	
	public double distance(String pk) 
	{
		Map<String, Double> mk = jaccard.toMapDouble(pk);
		return jaccard.jaccardIndexDouble(mk, mean);
	}
}
