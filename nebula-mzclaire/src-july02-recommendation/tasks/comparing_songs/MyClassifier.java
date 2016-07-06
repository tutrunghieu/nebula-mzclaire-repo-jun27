package tasks.comparing_songs;

public class MyClassifier extends Classifier<double[], String> 
{
	@Override
	public double jaccardSimilarScore(double[] a, double[] b) 
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

}
