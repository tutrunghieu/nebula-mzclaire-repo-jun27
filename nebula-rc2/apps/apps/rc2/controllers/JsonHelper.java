package apps.rc2.controllers;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelper {

	public static<T1> JsonResult<T1> createJsonObject(Class<T1> cl)
	throws Exception
	{
		JsonResult<T1> res = new JsonResult<T1>();
		
		res.error = 0;
		res.errorMessage = "";
		res.data = cl.newInstance();
		
		return res;
	}

	public static JsonSearchResult createSearchResult() 
	{
		JsonSearchResult res = new JsonSearchResult();
		
		res.error = 0;
		res.errorMessage = "";
		
		return res;
	}

	public static void renderJson(JsonSearchResult res, HttpServletResponse response)
	throws Exception
	{
		ObjectMapper m = new ObjectMapper();
		m.writeValue(response.getOutputStream(), res);
	}

	public static String encodeUrl(String uk) 
	throws Exception
	{
		 return URLEncoder.encode(uk, "UTF-8");
	}

}
