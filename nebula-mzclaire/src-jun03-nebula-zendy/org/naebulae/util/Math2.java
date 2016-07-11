package org.naebulae.util;

import java.security.MessageDigest;

public class Math2 
{
	public static boolean IGNORE_THIS_BLOCK = false;  

	public static boolean RETURN_NOW = true;

	public static void consume(Object src) {
		
	}
	
	public static int castInt(Double val)
	{
		return (int)(double)val;
	}
	
	
	public static byte[] sha256(String bytes) throws Exception 
	{
		return sha256(bytes.getBytes());
	}
	
	public static byte[] sha256(byte[] bytes) throws Exception 
	{
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
		 return md.digest(bytes);
	}
	
	public static byte[] sha1(byte[] bytes) throws Exception 
	{
		 MessageDigest md = MessageDigest.getInstance("SHA-1");
		 return md.digest(bytes);
	}
	

	
	public static String toHexString(byte[] t)
	{
		final StringBuilder builder = new StringBuilder();
		for(byte b : t) builder.append(String.format("%02x", b) );
		return builder.toString();
	}	
	
	public static byte[] md5(byte[] bytes) throws Exception 
	{
		 MessageDigest md = MessageDigest.getInstance("MD5");
		 return md.digest(bytes);
	}

	public static double[] normalized(double... args)
	{
		double s = 0;
		for(double ak: args) s += ak;
		for(int k=0; k<args.length; k++) args[k] /= s;

		return args;
	}

	public static void theEnd() 
	{
		System.out.println("-----------THE END--------------");
		
	}

	
}
