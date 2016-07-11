package org.naebulae.util.dali;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonImage1970 
{
	protected static ObjectMapper json = new ObjectMapper();
	
	public static String LOCAL_FILE = "local-file://";
	
	public static String __BASE_URL = ""; 
	
	private static String __IMAGE_CACHE = null;
	
	public static String DATA_CACHED_IMAGES()
	{
		if(__IMAGE_CACHE != null) return __IMAGE_CACHE;
		return __IMAGE_CACHE = System.getProperty("user.home") + "/data-cached-images";
	}
	
	public static String DATA_CACHED_IMAGES(String src)
	{
		return __IMAGE_CACHE = src;
	}

	public static String BASE_URL(String src)
	{
		System.out.println(src);
		return __BASE_URL = src;
	}
}
