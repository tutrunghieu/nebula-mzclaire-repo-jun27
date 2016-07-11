package org.naebulae.zendy;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.naebulae.writers.HtmlWriter;
import org.naebulae.zendy.exceptions.ControllerCastingException;

@SuppressWarnings("serial")
public abstract class WebXmlTarget extends HttpServlet 
{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, String> webXmlParams = new TreeMap<String, String>();

	public abstract void processRequest();
	
	public static BaseController findTargetController(HttpServletRequest request, HttpServletResponse response, Class<?> cl) throws Exception  
	{
		Object res1 = cl.newInstance();
		
		if(! (res1 instanceof BaseController) ) 
			throw new ControllerCastingException();
		
		BaseController res = (BaseController)res1;
		
		res.method = "";
		res.request = request;
		res.response = response;
		res.out = new HtmlWriter(response.getWriter());
		
		return res;
	}	
	
	
	
	public BaseController findTargetController(Class<?> cl) throws Exception  
	{
		Object res = cl.newInstance();
		
		if(! (res instanceof BaseController) ) 
			throw new ControllerCastingException();
		
		BaseController res1 = (BaseController)res;
		
		res1.request = request;
		res1.response = response;
		res1.out = new HtmlWriter(response.getWriter());
		
		return res1;
	}	
	
	@SuppressWarnings("unchecked")
	public<T1> T1 findTargetAction(Map<String, Object> lut, Class<T1> cl) 
	{
		String[] cells = this.request.getRequestURI().split("\\/");
		String c2 = getCell(cells, 2, ""); 
		String c3 = getCell(cells, 3, "");
	
		
		return (T1)lut.get("/" + c2 + "/" + c3);
	}
	
	public static boolean checkHttps(HttpServletRequest request) 
	{
		return request.getRequestURL().toString().startsWith("https://");				
	}
	
	@SuppressWarnings("unchecked")
	public static<T1> T1 findTargetAction(HttpServletRequest request, Map<String, Object> lut, Class<T1> cl) 
	{
		String[] cells = request.getRequestURI().split("\\/");
		String c2 = getCell(cells, 2, ""); 
		String c3 = getCell(cells, 3, "");
		
		String s = "/" + c2 + "/" + c3;
		System.out.println("Find action " + s);
		
		return (T1)lut.get(s);
	}
	
	private static String getCell(String[] cells, int k, String dv) 
	{
		return (k < cells.length ? cells[k] : dv);
	}


	public void init() throws ServletException 
	{
		ServletConfig s = getServletConfig();

		Enumeration<String> names = s.getInitParameterNames();
		
		while (names.hasMoreElements()) 
		{
			String nk = names.nextElement();
			String vk = s.getInitParameter(nk);
			webXmlParams.put(nk, vk);
		}

		System.out.println("servlet started!");
	}
	
	public void destroy() 
	{
		System.out.println("servlet destroyed!");
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		this.request = req;
		this.response = resp;
		processRequest();		
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		this.request = req;
		this.response = resp;
		processRequest();				
	}

	public String uri0() 
	{
		String[] cells = this.request.getRequestURI().split("\\/");
		return "/" + cells[1];
	}

	public String uri0(String rel) 
	{
		String[] cells = this.request.getRequestURI().split("\\/");
		return "/" + cells[1] + "/" + rel;
	}

	public String uri() 
	{
		return this.request.getRequestURI();
	}

	public String url() 
	{
		return this.request.getRequestURL().toString();
	}

	
}
