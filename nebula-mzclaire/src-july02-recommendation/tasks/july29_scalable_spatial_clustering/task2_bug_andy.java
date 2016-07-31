package tasks.july29_scalable_spatial_clustering;

import java.util.ArrayList;
import java.util.List;

import tasks.july20_spatial_clustering.NearestPair;
import apps.mzclaire.jaccard.JaccardEngine;
import apps.mzclaire.jaccard.JaccardEngineBow;

public class task2_bug_andy {
	
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
			
			System.out.println( convert(getMembers(pk, new ArrayList<MovieDoc>())) );
		}
	}

	private static NearestPair findNearestPair(List<Object> items) 
	{
		double dmin = Double.MIN_VALUE;
		
		Object lmin = null, rmin = null;
		
		//TODO: items*items or Oh(n*n) or 13000*13000 = 169'000'000 pairs
		//to complete this matrix, we need 169000 seconds (46 hours)
		for(Object lj: items)
		for(Object rk: items)
		if(lj != rk)
		{
			double djk = distance(lj, rk);
			if(djk > dmin) { dmin = djk; lmin = lj; rmin = rk; }
		}
			
		return new NearestPair(dmin, lmin, rmin);
	}

	static JaccardEngine eng = new JaccardEngineBow();
	private static double distance(Object ql, Object qr) 
	{
		List<MovieDoc> left = getMembers(ql, new ArrayList<MovieDoc>());
		List<MovieDoc> right = getMembers(qr, new ArrayList<MovieDoc>());
		
		double dmax = Double.MIN_VALUE;
		
		//TODO: in the worse case, we have 6500 members of the left and 65000 members on the right
		//oh(n*n*(n/2)*(n/2)) = oh(n^4/4) = oh(n^4)
		for(MovieDoc lj: left)
		for(MovieDoc rk: right)
		{
			double s12 = eng.jaccardIndex(lj.words, rk.words);
			if(s12 > dmax) dmax = s12;
		}
		
		return dmax;
	}

	private static List<MovieDoc> getMembers(Object lj, List<MovieDoc> res) 
	{
		if(lj instanceof MovieDoc) 
		{
			res.add((MovieDoc)lj);
		}
		
		if(lj instanceof NearestPair) 
		{
			NearestPair t = (NearestPair)lj;
			getMembers(t.left, res);
			getMembers(t.right, res);
		}
		 
		return res;
	}

	public static List<Object> newListObj(Object... args)
	{
		List<Object> res = new ArrayList<Object>();
		for(Object ak: args) res.add(ak);
		return res;
	}
	
	private static String convert(List<MovieDoc> items) {
		String res = "";
		for(MovieDoc mk: items) {
			res += mk.words + " , ";
		}
		return "[ " + res + " ]";
	}
	
}
