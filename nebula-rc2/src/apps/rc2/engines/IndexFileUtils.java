package apps.rc2.engines;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class IndexFileUtils {

	public static PrintWriter openWriterForAppend(File fj)
	throws Exception
	{
		fj.getParentFile().mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(fj, true)) );
	}
	
	public static void createFile(File fj) 
	throws Exception
	{
		fj.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(fj);
		out.println(System.currentTimeMillis());
		out.close();
	}
	
	
	public static String getFileIdentifier(File fk) 
	{
		try 
		{ 
			MessageDigest md = MessageDigest.getInstance("SHA-256");			
			byte[] id = md.digest(fk.getAbsolutePath().getBytes());
			return toHexString(id); 
		}
		catch(Exception xp) { return null; }
	}
	
	public static String toHexString(byte[] t)
	{
		final StringBuilder builder = new StringBuilder();
		for(byte b : t) builder.append(String.format("%02x", b) );
		return builder.toString();
	}

	public static List<String> getFilesForColor(File cj) 
	throws Exception
	{
		List<String> res = new ArrayList<String>();
		if(!cj.exists()) return res;
		
	    InputStreamReader isr = new InputStreamReader(new FileInputStream(cj), Charset.forName("UTF-8"));
	    BufferedReader br = new BufferedReader(isr);

	    while(true)
	    {
	      String line = br.readLine();
	      if(line==null) break;
	      res.add(line);
	    }
	    
	    br.close();
	
	    return res;
	}

	
}
