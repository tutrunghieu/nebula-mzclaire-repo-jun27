package tasks.july08_knn_clustering;

public class ClusterDouble extends Cluster<double[]> 
{
	public double[] center;
	
	public void add(double[] pk) 
	{
		members.add(pk);				
	}

	public void average() 
	{
		if(members.size() == 0) return;
		
		int d = members.get(0).length;
		
		center = new double[d];
		
		for(double[] mk: members)
		{
			for(int j=0; j<d; j++) center[j] += mk[j];
		}
		
		int n = members.size();
		for(int j=0; j<d; j++) center[j] /= n;
		
		System.out.println("--------------");
		for(double[] mk: members) System.out.println( Vector2.toString(mk)  );		
		System.out.println("Center " + Vector2.toString(center) );
	}
	
	public double distance(double[] pk) 
	{
		return Vector2.distance_L2(center, pk);
	}
	
}
