package org.naebulae.sortkeys;

import java.util.Comparator;

public class SortDoubleArray implements Comparator<double[]> 
{

	@Override
	public int compare(double[] a, double[] b)
	{
		int na = (a==null ? 0 : a.length);
		int nb = (b==null ? 0 : b.length);
		
		for(int n=Math.min(na, nb), k=0; k<n; k++)
		{
			int d = (int)Math.signum(a[k] - b[k]);
			if(d != 0) return d;
		}
		
		return na - nb;
	}

}
