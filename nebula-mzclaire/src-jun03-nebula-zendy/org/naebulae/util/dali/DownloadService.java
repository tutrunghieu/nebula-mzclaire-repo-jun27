package org.naebulae.util.dali;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class DownloadService {

	public void writeHeaderComma(HttpServletResponse response, String fname) 
	{
		response.setContentType("text/csv;charset=utf-8");
		response.setHeader("Content-Disposition","attachment; filename=\"" + fname + ".csv\"");		
	}

	public <T1> void writeComma(List<T1> items, PrintWriter out, Class<T1> cl)
	throws Exception
	{
		Field[] fields = cl.getDeclaredFields();
		
		for(int k=0; k<fields.length; k++)
		{
			if(k>0) out.print(",");
			out.print(fields[k].getName());
		}
		
		out.println();
		
		for(T1 ik: items)
		{
			for(int k=0; k<fields.length; k++)
			{
				if(k>0) out.print(",");
				String vk = toString(fields[k].get(ik), fields[k]);
				vk = vk.replace("\"", "\"\"");
				
				out.print('"' + vk + '"');
			}
			
			out.println();			
		}
		
	}

	public String toString(Object object, Field field) 
	{
		if(object instanceof java.util.Date)
		{
			return "HERE " + object; 
		}
		
		return object.toString();
	}

}
