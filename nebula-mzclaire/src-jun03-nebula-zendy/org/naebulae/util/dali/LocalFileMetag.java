package org.naebulae.util.dali;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class LocalFileMetag {

	public String fileName;
	public String fileSize;
	public String fileExt;
	
	public String mimeType;
	public Map<String, Object> tags;

	public static LocalFileMetag fromFile(File f) 
	{
		LocalFileMetag res = new LocalFileMetag();
		
		res.fileName = f.getName();
		res.fileSize = "" + f.length();
		
		try { res.mimeType = getMimeType(f); }
		catch(Exception xp) {}
		
		return res;
	}

	private static String getMimeType(File f)
	throws Exception
	{
		 Path source = Paths.get(f.getAbsolutePath());
		 return Files.probeContentType(source);
	}	

}
