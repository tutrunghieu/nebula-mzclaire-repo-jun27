package org.naebulae.util;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandUtils 
{
	private static RandUtils inner = new RandUtils();
	public static Random coin = new Random(197);
	
	public static RandUtils start()
	{
		return inner;
	}

	@SuppressWarnings("unchecked")
	public <T1> T1 takeOne(T1... str)
	{
		int n = str.length;
		double s = 0, ds = 1d/n, r = coin.nextDouble();
		
		for(int k=0; k<n; k++)
		{
			s += ds;
			if(r < s) return str[k];
		}
		
		return null;
	}

	public <T1> T1 takeOne(List<T1> str)
	{
		int n = str.size();
		double s = 0, ds = 1d/n, r = coin.nextDouble();
		
		for(T1 sk: str)
		{
			s += ds;
			if(r < s) return sk;
		}
		
		return null;
	}
	

	public <T1> T1 takeOne(Set<T1> str)
	{
		int n = str.size();
		double s = 0, ds = 1d/n, r = coin.nextDouble();
		
		for(T1 sk: str)
		{
			s += ds;
			if(r < s) return sk;
		}
		
		return null;
	}

	public  boolean nextBoolean(double thres) 
	{
		return coin.nextDouble() >= thres;
	}

	public String nextString() 
	{
		String res = "";
		for(int k=0; k<4; k++) res += coin.nextDouble() + " ";
		return String2.identifier256(res);
	}
	
	public double nextDouble(double s) 
	{
		return coin.nextDouble() * s;
	}
	
	public int nextIntBetween(int a, int b) 
	{
		if(a > b) { int t=a; a=b; b=t; }
		
		double t =  (a) + (b-a)*coin.nextDouble();
		return (int)t;
	}
	
	public double nextDoubleBetween(double a, double b) 
	{
		if(a > b) { double t=a; a=b; b=t; }
		
		return  (a) + (b-a)*coin.nextDouble();
	}
}
