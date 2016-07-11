package org.naebulae.access;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.naebulae.sortkeys.SortFileByLastModified;
import org.naebulae.util.String2;
import org.naebulae.util.func.JsacAction;
import org.naebulae.util.func.JsacAction2;
import org.naebulae.util.func.JsacPredicate;
import org.naebulae.writers.HtmlWriter;

public class FolderAccess extends FolderAccess1970
{
	public static FolderAccess start()
	{
		return new FolderAccess();
	}
	
	public File getDesktopFile() 
	{
		return new File(System.getProperty("user.home") + "/Desktop"); 	
	}
	
	public File getDesktopFile(String fname) 
	{
		return new File(System.getProperty("user.home") + "/Desktop/" + fname); 	
	}
	
	public File getDataFile() 
	{
		return new File(System.getProperty("user.home") ); 	
	}
	
	public File getDataFile(String fname) 
	{
		return new File(System.getProperty("user.home") + "/" + fname); 	
	}
		

	public File getInspectionFile() 
	{
		return new File(System.getProperty("user.home") 
				+ "/data-inspector101/" + System.nanoTime() + ".json");
	}

	public File getInspectionFile(String fname) 
	{
		return new File(System.getProperty("user.home") 
				+ "/data-inspector101/" + fname);
	}
	
	public List<File> getInspectionFiles() 
	{
		List<File> res = new ArrayList<File>();
		
		File f = new File(System.getProperty("user.home") 
				+ "/data-inspector101");
		
		File[] files = f.listFiles();
		if(files == null) return res;
		
		for(File fk: files)
			if(fk.getName().endsWith(".json")) res.add(fk);
		
		return res;
	}


	public List<File> orderByTime(List<File> f) 
	{
		return f.stream()
				.sorted(new SortFileByLastModified())
				.collect(Collectors.toList());
	}
	
//
//	public static void visitAll(File f, FolderAction lf) 
//	{
//		Stack<FolderLevel> todo = new Stack<FolderLevel>();
//		todo.add(new FolderLevel(f, 0));
//		
//		String pref = f.getAbsolutePath();
//		
//		while(!todo.isEmpty())
//		{
//			FolderLevel fk = todo.pop();
//			
//			int level = fk.dataLevel + 1;
//			File[] files = fk.dataFile.listFiles();
//			
//			if(files != null) 
//			for(File fj: files) 
//			{
//				lf.invokeFolderAction(fk.dataFile, fk.dataLevel, pref);				
//				if(fj.isDirectory()) todo.add(new FolderLevel(fj, level));
//			}
//		}
//		
//		return;
//	}
	
	public List<File> readInnerFilesNR(File f0) 
	{
		List<File> res = new ArrayList<File>();
		
		File[] files = f0.listFiles();
		if(files != null)  for(File fk: files) res.add(fk);

		return res;
	}
	

	public List<File> readInnerFiles(File f0) 
	{
		List<File> res = new ArrayList<File> ();
		
		Stack<File> todo = new Stack<File>();
		todo.add(f0);
		
		while(!todo.isEmpty())
		{
			File fk = todo.pop();
			
			File[] files = fk.listFiles();
			if(files == null) continue;
			
			for(File fj: files)
			if( fj.isFile() ) res.add(fj);
			else if( fj.isDirectory() ) todo.add(fj);
		}
		
		return res;
	}

	public Set<String> readInnerFileTypes(File f0) 
	{
		Set<String> res = new TreeSet<String>();
		
		for(File fk: readInnerFiles(f0))
		{
			String ek = String2.afterLast('.', fk.getName());
			res.add(ek.toLowerCase());
		}
				
		return res;
	}

	public byte[] readAllBytes(File fk) 
	throws Exception
	{
		Path path = Paths.get(fk.getAbsolutePath());
		return Files.readAllBytes(path);
	}

	public void showFile(File f, JsacAction<HtmlWriter> lf) 
	throws Exception
	{
		HtmlWriter out = new HtmlWriter(f);
		lf.invokeJsacAction(out);
		out.close();
		showFile(f);
	}

	public List<Object> readFilesAsMap(File f0, JsacPredicate<File> lf, 
			JsacAction2<Map<String, Object>, File> ef) 
	throws Exception
	{
		List<Object> res = new ArrayList<Object> ();
		
		File[] files = f0.listFiles();
		
		if(files != null)
		for(File fk: files)
		if( lf.invokeJsacFilter(fk) )
		{
			Map<String, Object> rk = new LinkedHashMap<String, Object>();
			res.add(rk);
			
			rk.put("file-name", fk.getName());
			rk.put("file-size", fk.length());
			rk.put("file-size", fk.length());
			
			ef.invokeJsacAction(rk, fk);
		}
		
		return res;
	}

	public void writeAllBytes(File f, String b) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(b.getBytes());
		fos.close();		
	}
	
	public void writeAllBytes(File f, byte[] b) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(b);
		fos.close();		
	}

	public String readLinesAsText(Class<?> cl, String fname) 
	throws Exception
	{
		File f = new File( cl.getResource(fname).getFile() );
		System.out.println(f.getAbsolutePath());
		
		return readLinesAsText(f);
	}


	
}
