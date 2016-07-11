package org.naebulae.cached;

import java.io.File;

public class CachedService1970 
{
	private static File __ROOT;
	
	public static void startService(File root)
	{
		__ROOT = root;		
	}
	
	private static String getRootAbsolutePath()
	{
		if(__ROOT == null)
			__ROOT = new File(System.getProperty("user.home") + "/cached-101");
		
		return __ROOT.getAbsolutePath();
	}
	
	public static File getFileForId(String id) 
	{
		return new File(getRootAbsolutePath() + "/" + id);
	}

	public static File getFileForRoot() 
	{
		return new File( getRootAbsolutePath() );
	}

}
