package tasks.july11_spatial_clustering;

import java.awt.Color;

import tasks.comparing_songs.Classifier;

public class ColorClassifier extends Classifier<double[], Color> 
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

	public static double colorDiff(Color x, Color y) 
	{
		int r = x.getRed() - y.getRed();
		int g = x.getGreen() - y.getGreen();
		int b = x.getBlue() - y.getBlue();
		
		return Math.sqrt((r*r + g*g + b*b)/(3.0*255*255) );
	}

}
