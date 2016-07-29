package tasks.july29_scalable_spatial_clustering;

import java.util.ArrayList;
import java.util.List;

import tasks.july20_spatial_clustering.NearestPair;

//see https://en.wikipedia.org/wiki/Hierarchical_clustering
public class task2_bottom_up_grouping 
{
	public static void main(String[] args) 
	{
		List<Object> items = newListObj(
				new MovieDoc("red red red green"),
				new MovieDoc("red red red red green"),
				new MovieDoc("red red red red green"),
				new MovieDoc("red red red red red blue"),
				new MovieDoc("blue blue blue green blue"),
				new MovieDoc("blue blue blue green blue"),
				new MovieDoc("blue blue blue green blue blue"),
				new MovieDoc("blue blue blue green"),
				new MovieDoc("blue blue blue green blue blue red")
				);
		
		hclust(items);
	}

	private static void hclust(List<Object> items) 
	{
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
		
		//TODO: items*items or Oh(n*n) or 13000*13000 = 169'000'000 pairs
		//to complete this matrix, we need 169000 seconds (46 hours)
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
		
		//TODO: in the worse case, we have 6500 members of the left and 65000 members on the right
		//oh(n*n*(n/2)*(n/2)) = oh(n^4/4) = oh(n^4)
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
//
//	private static List<Object> newList(int... args)
//	{
//		List<Object> res = new ArrayList<Object>();
//		for(int ak: args) res.add(ak);
//		return res;
//	}

	public static List<Object> newListObj(Object... args)
	{
		List<Object> res = new ArrayList<Object>();
		for(Object ak: args) res.add(ak);
		return res;
	}
}
