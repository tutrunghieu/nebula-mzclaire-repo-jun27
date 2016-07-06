package org.naebulae.writers;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HtmlWriter extends HtmlWriter1970
{
	public HtmlWriter(File f) throws Exception 
	{
		super(f);
	}

	public HtmlWriter(PrintWriter t) 
	{
		super(t);
	}

	public HtmlWriter(Writer t) 
	{ 
		super(t); 
	}
	


	public static Field[] getFieldArrayFromObject(Object ik) 
	{
		List<Field> res = new ArrayList<Field>();
		
		for(Field fk: ik.getClass().getDeclaredFields())
			if( Modifier.isPublic(fk.getModifiers()) ) res.add(fk);
		
		return res.toArray(new Field[] {});
	}
	
	public static Set<String> getFieldSetFromClass(Class<?> cl) 
	{
		Set<String> res = new LinkedHashSet<String>();
		for(Method fk: cl.getMethods())
		if( Modifier.isPublic(fk.getModifiers()) ) res.add(fk.getName());
		return res;
	}	

	@SuppressWarnings("unchecked")
	public static List<String> getFieldListFromMap(Object s) 
	{
		Map<String, Object> obj = (Map<String, Object>)s;
		List<String> res = new ArrayList<String>();
		res.addAll(obj.keySet());
		return res;
	}

	@SuppressWarnings("unchecked")
	public static String[] getFieldArrayFromMap(Object s) 
	{
		Map<String, Object> obj = (Map<String, Object>)s;
		List<String> res = new ArrayList<String>();
		res.addAll(obj.keySet());
		return res.toArray(new String[] {});
	}
	
	public void headerRowFromStrings(String... fields)
	{
		dataWriter.print("<tr>");
		for(Object ak: fields) dataWriter.print("<th>"+ak+"</th>"); 
		dataWriter.println("</tr>");
	}	
		
	public void headerRowFromFields(Field... fields) 
	{
		dataWriter.print("<tr>");
		for(Field ak: fields) dataWriter.print("<th>"+ak.getName()+"</th>"); 
		dataWriter.println("</tr>");		
	}
	
	public void headerRowFromSet(Set<String> fields) 
	{
		dataWriter.print("<tr>");
		for(String ak: fields) dataWriter.print("<th>"+ak+"</th>"); 
		dataWriter.println("</tr>");		
	}
	
	
	public void headerRowFromList(List<String> fields)
	{
		dataWriter.print("<tr>");
		for(String ak: fields) dataWriter.print("<th>"+ak+"</th>"); 
		dataWriter.println("</tr>");
	}	
		
	
	public void dataRowFromArrayObj(Object... fields)
	{
		dataWriter.print("<tr>");
		for(Object ak: fields) dataWriter.print("<td>"+ak+"</td>"); 
		dataWriter.println("</tr>");
	}
	
	public void dataRowFromArrayStr(String... fields)
	{
		dataWriter.print("<tr>");
		for(Object ak: fields) dataWriter.print("<td>"+ak+"</td>"); 
		dataWriter.println("</tr>");
	}		
	
	public<T> void dataRowFromList(List<T> names) 
	{
		dataWriter.print("<tr>");
		for(T nk: names) dataWriter.print("<td>"+nk+"</td>"); 
		dataWriter.println("</tr>");						
	}
	
	public<T> void dataRowFromSet(Set<T> names) 
	{
		dataWriter.print("<tr>");
		for(T nk: names) dataWriter.print("<td>"+nk+"</td>"); 
		dataWriter.println("</tr>");						
	}
	

	public void dataRowFromObject(Object obj) throws Exception 
	{
		if(dataFields == null) 
		{
			dataFields = getFieldArrayFromObject(obj);
			dataWriter.print("<tr>");
			for(Field fk: dataFields) dataWriter.print("<td>"+fk.getName()+"</td>"); 
			dataWriter.println("</tr>");		
		}
		
		dataWriter.print("<tr>");
		for(Field fk: dataFields) dataWriter.print("<td>"+fk.get(obj)+"</td>"); 
		dataWriter.println("</tr>");		
		
	}
	
	@SuppressWarnings("unchecked")
	public void dataRowFromMap(Object s, List<String> fields)  
	{
		Map<String, Object> obj = (Map<String, Object>)s;
		
		dataWriter.print("<tr>");
		for(String fk: fields) dataWriter.print("<td>"+obj.get(fk)+"</td>"); 
		dataWriter.println("</tr>");				
	}
	
	@SuppressWarnings("unchecked")
	public void dataRowFromMap(Object s, String[] fields)  
	{
		Map<String, Object> obj = (Map<String, Object>)s;
		
		dataWriter.print("<tr>");
		for(String fk: fields) dataWriter.print("<td>"+obj.get(fk)+"</td>"); 
		dataWriter.println("</tr>");				
	}
	
	@SuppressWarnings("unchecked")
	public void dataRowFromMap(Object s, Set<String> fields)  
	{
		Map<String, Object> obj = (Map<String, Object>)s;
		
		if(fields == null) fields = obj.keySet();
		
		dataWriter.print("<tr>");
		for(String fk: fields) dataWriter.print("<td>"+obj.get(fk)+"</td>"); 
		dataWriter.println("</tr>");				
	}
	
	public void dataCellFromLink(String text, String href) 
	{
		dataWriter.println("<td>");
		dataWriter.println("<a href='"+href+"'>"+text+"</a>");
		dataWriter.println("</td>");
	}	


	public String anchorText(String text, String link) 
	{
		return "<a href='"+link+"'>"+text+"</a>";
	}
	
	public String anchorTextTar(String text, String link) 
	{
		return "<a target=_blank href='"+link+"'>"+text+"</a>";
	}
	
	public String imageText(String sk, String cl) 
	{
		return "<img src='"+sk+"' class='"+cl+"'>";
	}
	

	public String spanText(String text, String cl) 
	{
		return "<span class='"+cl+"'>"+text+"</span>";
	}

	public<T1> void box(T1 src, HtmlWriterAction<T1> lf) 
	{
		dataWriter.println("<div style='display:inline-block; vertical-align: top;"
				+ "border: dashed 1px #aeaeae; padding: 5px; margin: 5px;'>");
		lf.invokeWriterAction(src, this);
		dataWriter.println("</div>");
	}

}
