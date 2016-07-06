package org.naebulae.access;

import javax.servlet.http.HttpServletRequest;

public class RequestAccess 
{

	public static String beforePort(String s) 
	{
		return s.substring(0, s.indexOf('/', 8) );
	}

	public static String beforePort(HttpServletRequest request) 
	{
		return beforePort(request.getRequestURL().toString());
	}

	public static String trim(String tok, String dv) 
	{
		return tok==null ? dv : tok.trim();
	}

	public static String trim(HttpServletRequest request, String name, String dv) 
	{
		String tok = request.getParameter(name);
		return tok==null ? dv : tok.trim();
	}
}
