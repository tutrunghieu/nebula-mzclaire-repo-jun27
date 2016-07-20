package tasks.july20_spatial_clustering;

import java.util.ArrayList;
import java.util.List;

//see https://en.wikipedia.org/wiki/Hierarchical_clustering
public class task2_bottom_up_grouping 
{
	public static void main(String[] args) 
	{
		List<Object> items = newList(1, 2, 101, 103, 10);
		
		for(int n=items.size(); n>1; n=items.size())
		{
			NearestPair pk = findNearestPair(items);
			items.remove(pk.left);
			items.remove(pk.right);
			items.add(pk);
			
			System.out.println(getMembers(pk, new ArrayList<Integer>()) );
		}
		
	}

	private static NearestPair findNearestPair(List<Object> items) 
	{
		double dmin = Double.MAX_VALUE;
		
		Object lmin = null, rmin = null;
		
		for(Object lj: items)
		for(Object rk: items)
		if(lj != rk)
		{
			double djk = distance(lj, rk);
			if(djk < dmin) { dmin = djk; lmin = lj; rmin = rk; }
		}
			
		return new NearestPair(dmin, lmin, rmin);
	}

	private static double distance(Object ql, Object qr) 
	{
		List<Integer> left = getMembers(ql, new ArrayList<Integer>());
		List<Integer> right = getMembers(qr, new ArrayList<Integer>());
		
		double dmin = Double.MAX_VALUE;
		
		for(Integer lj: left)
		for(Integer rk: right)
		{
			double dj = Math.abs(lj - rk);
			if(dj < dmin) dmin = dj;
		}
		
		return dmin;
	}

	private static List<Integer> getMembers(Object lj, List<Integer> res) 
	{
		if(lj instanceof Integer) 
		{
			res.add((Integer)lj);
		}
		
		if(lj instanceof NearestPair) 
		{
			NearestPair t = (NearestPair)lj;
			getMembers(t.left, res);
			getMembers(t.right, res);
		}
		 
		return res;
	}

	private static List<Object> newList(int... args)
	{
		List<Object> res = new ArrayList<Object>();
		for(int ak: args) res.add(ak);
		return res;
	}

}
