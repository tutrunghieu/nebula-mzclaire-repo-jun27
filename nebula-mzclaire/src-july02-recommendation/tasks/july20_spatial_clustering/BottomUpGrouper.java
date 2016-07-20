package tasks.july20_spatial_clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BottomUpGrouper<T> 
{
	List<BottomUpGroup<T>> g = new ArrayList<BottomUpGroup<T>>(); 

	public void add(T item)
	{
		g.add(new BottomUpGroup<T>(item));		
	}

	public void group() 
	{
		SimilarMatrix m = new SimilarMatrix(); 
		
		for(BottomUpGroup<T> lk: g)
		for(BottomUpGroup<T> rj: g)
		if( lk != rj)
		{
			double djk = similar(lk, rj);
			m.add(lk, rj, djk);
		}
			
		for(double djk: m.valueSet())
		{
		}
		
	}

	private double similar(BottomUpGroup<T> lk, BottomUpGroup<T> rj) {
		// TODO Auto-generated method stub
		return 0;
	}

}
