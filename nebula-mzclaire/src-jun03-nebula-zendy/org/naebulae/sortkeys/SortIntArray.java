package org.naebulae.sortkeys;

import java.util.Comparator;

public class SortIntArray implements Comparator<int[]> 
{

	@Override
	public int compare(int[] a, int[] b)
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
