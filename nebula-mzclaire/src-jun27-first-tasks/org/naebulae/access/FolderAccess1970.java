package org.naebulae.access;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.naebulae.util.dali.LocalFileMetag;

public class FolderAccess1970 
{
	public void showFile(File f) 
	throws Exception
	{
		Desktop.getDesktop().open(f);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> readJsonObject(File fk)
	throws Exception
	{
		ObjectMapper engineJson = new ObjectMapper();
		return engineJson.readValue(fk, LinkedHashMap.class); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> readJsonList(File fk)
	throws Exception
	{
		ObjectMapper engineJson = new ObjectMapper();
		return engineJson.readValue(fk, List.class); 
	}
	
	@SuppressWarnings("unchecked")
	public<T1> List<T1> readJsonList(File fk, Class<T1> cl)
	throws Exception
	{
		ObjectMapper engineJson = new ObjectMapper();
		
		List<T1> res = new ArrayList<T1>();
		for(Object sk: engineJson.readValue(fk, List.class))
		{
			T1 rk = cl.newInstance();
			res.add(rk);
			
			Map<String, Object> tk = (Map<String, Object>)sk; 
			for(String fj: tk.keySet())
			{
				Object vj = tk.get(fj);
				cl.getField(fj).set(rk, vj);
			}
		}
		
		return res;
	}
	
	public void writeJson(LocalFileMetag src, File file) 
	throws Exception
	{
		ObjectMapper engineJson = new ObjectMapper();
		engineJson.writeValue(file, src); 		
	}
	
	public void writeJson(Object src, File file) 
	throws Exception
	{
		ObjectMapper engineJson = new ObjectMapper();
		engineJson.writeValue(file, src); 		
	}
	
	public List<String> readLinesFrom(File f) 
	throws Exception
	{
		List<String> res = new ArrayList<String> ();
		
		BufferedReader reader = new BufferedReader(new FileReader(f));
		while(true)
		{
			String line = reader.readLine();
			if(line == null) break;
			res.add(line);			
		}
		reader.close();
		
		return res;
	}


	public void copyBytes(File src, File tar) 
	throws Exception
	{
		InputStream input = new FileInputStream(src);
		OutputStream output = new FileOutputStream(tar);
		IOUtils.copy(input, output);
		output.close();
		input.close();
	}
	
}
