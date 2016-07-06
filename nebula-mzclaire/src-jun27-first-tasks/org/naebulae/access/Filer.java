package org.naebulae.access;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Filer 
{
	protected String root;

	public Filer(String r) 
	{
		root = r;
	}

	public Filer(Class<?> r) 
	{
		String name = '/' + r.getName().replace('.', '/') + ".class";
		File f = new File(  r.getClass().getResource(name).getFile() );
		root = f.getParent();
	}
	
	public File get(String name) {
		return new File(root + "/" + name);
	}

	public File getRootFile() 
	{
		return new File(root);
	}	
	
	public PrintWriter getWriter(String name) throws Exception
	{
		return new PrintWriter( get(name) );
	} 

	public File addTextFile(String fname, String contents) throws Exception
	{
		File f =  get(fname);
		
		PrintWriter out = new PrintWriter(f);
		out.println(contents);
		out.close();
		
		return f;
	}
	
	public File addBinaryFile(String fname, byte[] b) throws Exception
	{
		File f =  get(fname);
		
		FileOutputStream out = new FileOutputStream(f);
		out.write(b);
		out.close();
		
		return f;
	}

	public String getFileUrl(String string) 
	{
		String res = this.get(string).getAbsolutePath();
		return "file:///" + res.replace('\\', '/');
	}
	
}
