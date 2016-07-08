package tasks.july08_knn_clustering;

public class Vector2 
{
	public static double distance_L2(double[] a, double[] b) 
	{
		int n = Math.max(a.length, b.length);
		
		double d = 0;
		
		for(int k=0; k<n; k++)
		{
			double dk = a[k] - b[k];
			d += dk*dk;
		}
		
		return Math.sqrt(d / (n==0 ? 1 : n) );
	}
	
	public static double jaccardSimilarScore(double[] a, double[] b) 
	{
		int n = Math.max(a.length, b.length);
		
		double d = 0;
		
		for(int k=0; k<n; k++)
		{
			double dk = a[k] - b[k];
			d += dk*dk;
		}
		
		d = Math.sqrt(d / (n==0 ? 1 : n) );
		return Math.exp(-d);
	}
	
	public static String toString(double... x) 
	{
		String res = "";
		for(int k=0; k<x.length; k++)
		{
			if(k>0) res += ", ";
			res += x[k];
		}
		
		return res;
	}

}
