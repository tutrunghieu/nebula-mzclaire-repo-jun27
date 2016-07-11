package org.naebulae.zendy;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.naebulae.zendy.RequestInspector;

public class RequestDebugger 
{
	private static RequestDebugger __st = new RequestDebugger(); 
	
	public static RequestDebugger start() 
	{
		return __st;
	}
	
	public void saveRequest(HttpServletRequest request) 
	{
		if(request.getRequestURI().contains("/help/requests")) return;
		
		try 
		{
			RequestInspector res = new RequestInspector(request);
			res.save( getInspectionFile() );
		} 
		catch(Exception xp) {  }
	}

	public void saveRequest(HttpServletRequest request, File f) 
	{
		try 
		{
			RequestInspector res = new RequestInspector(request);
			res.save(f);
		} 
		catch(Exception xp) {  }
	}

	public File getInspectionFile() 
	{
		return new File(System.getProperty("user.home") 
				+ "/data-inspector101/" + System.nanoTime() + ".json");
	}

	public File getInspectionFile(String fname) 
	{
		return new File(System.getProperty("user.home") 
				+ "/data-inspector101/" + fname);
	}
	
}
