package org.naebulae.zendy;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.naebulae.writers.HtmlWriter;

public class BaseController 
{
	protected String method;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HtmlWriter out;
	
	public String readParam(String name, String dv) 
	{
		String v = request.getParameter(name);
		return v==null ? dv : v;
	}
	
	public String[] readParams(String name){
		String[] list=request.getParameterValues(name);
		return list;
	}
	public String readHeader(String name, String dv) 
	{
		String v = request.getHeader(name);
		return v==null ? dv : v;
	}
	
	public void renderMethod(Method res) throws Exception
	{
		res.invoke(this);
	}		

	@SuppressWarnings("rawtypes")
	public void renderResult(Object res) throws Exception
	{
		if(res instanceof HtmlResult) 
		{
			PrintWriter out = response.getWriter();
			((HtmlResult)res).invokeResultAction(out);
		}
		
		else 
		{
			this.response.getWriter().println(res);
		}
	}	
	

	public String uri() 
	{
		return this.request.getRequestURI();
	}
	
	public String urlApp() 
	{
		String url = this.request.getRequestURL().toString();
		url = url.substring(0, url.indexOf('/', 9));
		
		String[] cells = this.request.getRequestURI().split("\\/");
		return url + "/" + cells[1];
	}
	
	public String uriApp() 
	{
		String[] cells = this.request.getRequestURI().split("\\/");
		return "/" + cells[1];
	}

	public String uriApp(String rel) 
	{
		String[] cells = this.request.getRequestURI().split("\\/");
		
		if(rel.startsWith("/")) return "/" + cells[1] + rel;
		else return "/" + cells[1] + "/" + rel;
	}

	public String uriApp(Class<?> cl, String rel) 
	{
		String[] cells = this.request.getRequestURI().split("\\/");
		if(rel.startsWith("/")) return "/" + cells[1] + rel;
		else return "/" + cells[1] + "/" + rel;
	}


	public HtmlWriter getHtmlWriter() 
	{
		try { return new HtmlWriter(response.getWriter()); }
		catch(Exception xp) { return null; }
	}

	private static ObjectMapper engineJson = new ObjectMapper();
	
	public void writeJson(Object src) 
	{
		try { engineJson.writeValue(response.getWriter(), src); } 
		catch(Exception xp) {  }
	}

	public void writeJson(Object src, File outf) 
	{
		try 
		{ 
			outf.getParentFile().mkdirs();
			engineJson.writeValue(outf, src); 
		}
		
		catch(Exception xp) {  }
	}

	public GeneralManager createManager(String prefix, Class<? extends GeneralManager> cl)
	throws Exception
	{
		GeneralManager res = cl.newInstance();
		
		res.request = this.request;
		res.response = this.response;
		res.out = this.out;
		res.target = this;
		res.prefix = prefix;

		return res;
	}	
}
