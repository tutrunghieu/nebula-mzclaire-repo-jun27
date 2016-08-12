package apps.rc2.services;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelper {


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
