package org.naebulae.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Class2 {
	public static List<Class<?>> fetchClasses(Class<?> c0) 
	{
		List<Class<?>> res = new ArrayList<Class<?>> ();
		
		File f0 = resourceFileForClass(c0);
		
		int len = f0.getAbsolutePath().length() - c0.getName().length() - 6;
		
		Stack<File> todo = new Stack<File>();
		todo.add(f0.getParentFile());
		
		while(!todo.isEmpty())
		{
			File[] files = todo.pop().listFiles();
			if(files == null) continue;
			
			for(File x: files)
			{
				if(x.isDirectory()) todo.push(x);
				
				String nk = x.getName();
				if(x.isFile() && nk.endsWith(".class")) 
				try
				{
					nk = nk.substring(0, nk.lastIndexOf('.'));
					String ck_name = classNameFromFile(x, len);
			
					Class<?> ck  = Class.forName(ck_name);
					res.add(ck);
				}
				catch(Exception xp) {}
				
			} //for each inner file
		} //while there are pending folder
		
		return res;				
	}
	
	public static String classNameFromFile(File x, int len) 
	{
		String name = x.getAbsolutePath();
		return name.substring(len, name.length() - 6).replace('\\', '.').replace('/', '.');
	}

	public static File resourceFileForClass(Class<?> start) 
	{
		String name = '/' + start.getName().replace('.', '/') + ".class";
		File f = new File( start.getResource(name).getFile() );
		return f;
	}

	public static void copyFields(Object r1, Object r2) 
	throws Exception
	{
		for(Field f: r1.getClass().getFields())
		if( Modifier.isPublic(f.getModifiers()) )
		{
			Object v = f.get(r1);
			System.out.println(f.getName() + ": " + v);
			f.set(r2, v);
		}
		
		return;
	}

	public static void copyToMap(Object dk, Map<String, Object> rk) 
	throws Exception
	{
		for(Field t: dk.getClass().getFields())
			if(Modifier.isPublic(t.getModifiers())) 
				rk.put(t.getName(), t.get(dk));		
	}

	public static void copyFromMapToMap(Map<String, Object> src, Map<String, Object> tar)
	{
		for(String nk: src.keySet())
		{
			Object vk = src.get(nk);
			tar.put(nk, vk);
		}
		
		return;
	}
	

}
