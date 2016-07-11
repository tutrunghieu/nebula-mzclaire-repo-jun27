package org.naebulae.zendy;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naebulae.writers.HtmlWriter;

public class GeneralManager 
{
	public HttpServletRequest request;
	public HttpServletResponse response;
	public HtmlWriter out;
	public Object target;
	public String prefix;
	
	public String readParam(String name, String dv) 
	{
		String v = request.getParameter(name);
		return v==null ? dv : v;
	}
	
	

	public void processRequest() 
	throws Exception
	{
		String cmd = this.readParam("cmd", "").trim();
		
		if(cmd.length() != 0) 
		{
			cmd = prefix + cmd + "Action";
			Method m = getMethod(target, cmd);
			
			System.out.println("Mapping " + cmd + " -> " + (m==null ? "#NULL" : m.getName()) );
			if(m != null) invokeMethod(target, m);
		}
		
		try {
			Method m = getMethod(target, prefix + "filterForm");
			if(m != null) invokeMethod(target, m);			
		} catch(Exception xp) { error(xp); }
		
		try {
			Method m = getMethod(target, prefix + "addForm");
			if(m != null) invokeMethod(target, m);			
		} catch(Exception xp) {  error(xp); }
		
		
		try {
			Method m = getMethod(target, prefix + "mainForm");
			if(m != null) invokeMethod(target, m);			
		} catch(Exception xp) {  error(xp); }
		
		return;
	}

	private void error(Exception xp) 
	{
		xp.printStackTrace();
	}



	private static void invokeMethod(Object src, Method m)
	throws Exception
	{
		m.invoke(src); 
	}



	private static  Method getMethod(Object src, String name)
	throws Exception
	{
		if(src == null) return null;
		return src.getClass().getMethod(name); 
	}

}
