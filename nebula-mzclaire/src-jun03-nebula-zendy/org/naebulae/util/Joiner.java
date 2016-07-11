package org.naebulae.util;

public class Joiner 
{
	private String cm;

	public static Joiner start(String cm)
	{
		Joiner res = new Joiner();
		res.cm = cm;
		return res;
	}

	public String join(Object[] args) 
	{
		String res = "";
		
		for(Object ak: args)
		{
			if(res.length() > 0) res += cm;
			res += ak;
		}
		
		return res;
	}

	public String join(int... args)
	{
		String res = "";
		
		for(Object ak: args)
		{
			if(res.length() > 0) res += cm;
			res += ak;
		}
		
		return res;
	}
	

}
