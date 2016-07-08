package tasks.july08_knn_clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClusteringEngine<T> 
{
	private List<T> points = new ArrayList<T>();  
	private List<Integer> labels = new ArrayList<Integer>();  

	public void add(T x) 
	{
		points.add(x);
	}

	public T getPoint(int k) 
	{
		return points.get(k);
	}
	
	public int getLabel(int k) 
	{
		return labels.get(k);
	}
	
	public int size()
	{
		return points.size();
	}
	
	public void predictWithKmeans(int kpar, int maxiter, Class<? extends Cluster<T>> cl) 
	throws Exception
	{
		randomizeLabels(kpar);
		
		for(int tt=0; tt<maxiter; tt++)
		{
			List<Cluster<T>> c = computeCenters(kpar, cl); 
			relabelWithCenters(c);
		}
	}

	private void relabelWithCenters(List<Cluster<T>> c) 
	{
		for(int k=0; k<points.size(); k++)
		{
			int lk = nearestCenter(points.get(k), c);
			labels.set(k, lk);
		}		
	}

	private int nearestCenter(T pk, List<Cluster<T>> c) 
	{
		double dmin = Double.MAX_VALUE;
		int kmin = -1;
		
		for(int k=0; k<c.size(); k++)
		{
			double dk = c.get(k).distance(pk);  
			if(dk < dmin) { dmin = dk; kmin = k; }
		}
		
		return kmin;
	}

	private void randomizeLabels(int kpar) 
	{
		Random coin = new Random(197);
		
		for(int k=0; k<points.size(); k++)
		{
			int lk = coin.nextInt(kpar);
			labels.add(lk);
		}		
	}

	@SuppressWarnings("unchecked")
	private List<Cluster<T>> computeCenters(int kpar, Class<? extends Cluster<T>> cl)
	throws Exception
	{
		List<Cluster<T>> res = new ArrayList<Cluster<T>>();
		for(int k=0; k<kpar; k++) res.add( cl.newInstance() );
		
		for(int k=0; k<points.size(); k++)
		{
			int lk = labels.get(k);
			T pk = points.get(k);
			res.get(lk).add(pk);
		}
		
		for(int k=0; k<res.size(); k++) 
			res.get(k).average();
		
		
		return res;
	}


}
