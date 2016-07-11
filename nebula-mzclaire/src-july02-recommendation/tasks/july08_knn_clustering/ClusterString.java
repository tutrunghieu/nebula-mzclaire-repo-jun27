package tasks.july08_knn_clustering;

import apps.mzclaire.jaccard.JaccardEngineBow;

public class ClusterString extends Cluster<String> 
{
	private double centroid;
	
	public void add(String pk) 
	{
		members.add(pk);				
	}

	public void average() 
	{
		if (members.size() ==0 ) return;
		
		int size = members.size();
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				centroid += jaccard.jaccardIndex(members.get(i), members.get(j));
			}
		}
		
		centroid = centroid / (members.size() * members.size());
		System.out.println("Center == " + centroid);
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
		
		smax = Math.abs(smax - centroid);
		
		return smax;
	}
	
}
