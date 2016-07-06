package org.naebulae.access;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;

public class Explorer 
{

	public static BufferedReader openReaderUtf8(File file) throws Exception
	{
		InputStreamReader utf = new InputStreamReader(new FileInputStream(file), "UTF-8"); 
		return new BufferedReader(utf);	
	}

	public static PrintWriter openWriterUtf8(File file) throws Exception
	{
		file.getParentFile().mkdirs();
		OutputStreamWriter utf = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		return new PrintWriter(utf);	
	}
	
	public static Filer getDesktop() 
	{
		return new Filer(System.getProperty("user.home") + "/Desktop");
	}

	public static Filer getAppConf(String app) 
	{
		return new Filer(System.getProperty("user.home") + "/Documents/" + app);
	}
	
	public static Filer getFolder(Class<?> res)  
	{
		return new Filer(res);
	}

	public static Filer getFolder(String r) 
	{
		return new Filer(r);
	}

	public static void show(File f) throws Exception
	{
		Desktop.getDesktop().open(f);
	}

	public static void showIndexHtml(File f) throws Exception
	{
		Desktop.getDesktop().open(new File(f.getAbsolutePath() + "/index.html") );
	}
	
	public static void notepad(File fk) throws Exception 
	{
		Runtime
		.getRuntime()
		.exec("notepad \"" + fk.getAbsolutePath() + "\"");		
	}

	public static void showLink(String uk) throws Exception 
	{
		Desktop.getDesktop().browse(new URI(uk));		
	}


}
