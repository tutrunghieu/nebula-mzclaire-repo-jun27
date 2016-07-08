package tasks.july08_knn_clustering;

import apps.mzclaire.jaccard.JaccardEngineBow;

public class ClusterString extends Cluster<String> 
{
	public void add(String pk) 
	{
		members.add(pk);				
	}

	public void average() 
	{
//		if(members.size() == 0) return;
//		
//		int d = members.get(0).length;
//		
//		center = new double[d];
//		
//		for(double[] mk: members)
//		{
//			for(int j=0; j<d; j++) center[j] += mk[j];
//		}
//		
//		int n = members.size();
//		for(int j=0; j<d; j++) center[j] /= n;
	}
	
	public JaccardEngineBow jaccard = new JaccardEngineBow(); 
	
	public double distance(String pk) 
	{
		double smax = 0;
		
		for(String mk: members)
		{
			double sk = jaccard.jaccardIndex(mk, pk);
			smax = Math.max(smax, sk);
		}
		
		smax = 1 - smax;
		System.out.println(pk + " " + smax);
		return smax;
	}
	
}
