package org.naebulae.zendy;

import java.io.BufferedReader;
import java.io.File;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

public class RequestInspector 
{
	public String clientAddress;
	public String clientPort;
	
	public String requestUri;
	public String requestUrl;
	public String sessionId;
	public String requestedSessionId;
	
	public Map<String, Object> params = new LinkedHashMap<String, Object>();
	public Map<String, Object> headers = new LinkedHashMap<String, Object>();
	public Map<String, Object> body = new LinkedHashMap<String, Object>();
	
	public RequestInspector()
	{
		
	}
	
	public RequestInspector(HttpServletRequest request) 
	{
		try { inspect(request); }
		catch(Exception xp) { }
	}

	public void inspect(HttpServletRequest request) 
	throws Exception
	{
		this.clientAddress = request.getRemoteAddr();
		this.clientPort = "" + request.getRemotePort();
		
		this.requestUri = request.getRequestURI();
		this.requestUrl = request.getRequestURL().toString();
		
		this.requestedSessionId = request.getRequestedSessionId();
		this.sessionId = request.getSession().getId();
		
		copyParams(request, params);
		copyHeaders(request, headers);
		copyBody(request, body);
		
		
	}
	
	private static Object copyHeaders(
			HttpServletRequest request, Map<String, Object> res)
	throws Exception
	{
		Enumeration<String> e = request.getHeaderNames();
				
		while( e.hasMoreElements() ) 
		{
			String ek = e.nextElement();
			res.put(ek, request.getHeader(ek));
		}
		
		return res;
	}

	private static Object copyBody(
			HttpServletRequest request, Map<String, Object> res)
	throws Exception
	{
		BufferedReader reader = request.getReader();
		int cnt = 0;
		
		while(true)
		{
			String lk = reader.readLine();
			if(lk == null) break;
			
			res.put(cnt + "", lk);
			cnt++;
		}
		
		return res;
	}
	

	private static void copyParams(
			HttpServletRequest request, Map<String, Object> res) 
	{
		Map<String, String[]> src = request.getParameterMap();
		
		for(String nk: src.keySet())
		for(String vk: src.get(nk))
		{
			res.put(nk, vk);
		}
		
		return;
	}

	public void save(File f) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		
		ObjectMapper engineJson = new ObjectMapper();
		engineJson.writeValue(f, this); 
	}
}
