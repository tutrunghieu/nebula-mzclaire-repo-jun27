package org.naebulae.writers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

public class HtmlWriter1970 
{
	protected Field[] dataFields;
	protected File dataFile;
	protected PrintWriter dataWriter;

	public HtmlWriter1970(File f) throws Exception
	{
		f.getParentFile().mkdirs();
		dataFile = f;

		FileOutputStream t = new FileOutputStream(f);
		t.write(new byte[] { (byte) 239, (byte) 187, (byte) 191 });
		dataWriter = new PrintWriter(new OutputStreamWriter(t, "UTF-8"), true);
	}
	
	public HtmlWriter1970(PrintWriter t) 
	{
		dataWriter = t;
	}

	public HtmlWriter1970(Writer t) 
	{
		dataWriter = new PrintWriter(t);
	}

	public String iso_8859_1(String s)
	throws Exception
	{
		return new String(s.getBytes(), "iso-8859-1");		
	}	

	
	public PrintWriter getWriter()
	{
		return dataWriter;
	}

	public void flush() 
	{
		dataWriter.flush();
	}
	
	public File close()
	{
		dataWriter.close();
		return dataFile;
	}

	
	public void print(String string)
	{
		dataWriter.print(string);
	}
	

	public void println(String string)
	{
		dataWriter.println(string);
	}
	
	public void println()
	{
		dataWriter.println();
	}
	

	public void printStyles(String... args) 
	{
		dataWriter.println("<style>");		
		for(String ak: args) dataWriter.println(ak);
		dataWriter.println("</style>");		
	}
	
	public void printScripts(String... args) 
	{
		dataWriter.println("<script>");		
		for(String ak: args) dataWriter.println(ak);
		dataWriter.println("</script>");		
	}



	public void table0() 
	{
		dataWriter.println("<table>");				
	}	
	
	public void table1() 
	{
		dataWriter.println("<table border=1>");		
	}
	
	public void table(String cl) 
	{
		dataWriter.println("<table class="+cl+">");		
	}
	
	public void tableEnd() 
	{
		dataWriter.println("</table>");		
	}

	public void h1(String text) 
	{
		dataWriter.println("<h1>"+text+"</h1>");
	}
	
	public void h2(String text) 
	{
		dataWriter.println("<h2>"+text+"</h2>");
	}

	public void h3(String text) 
	{
		dataWriter.println("<h3>"+text+"</h3>");
	}
	
	public void p(String string) 
	{
		dataWriter.println("<p>"+string+"</p>");		
	}
	
	
	public void hr() 
	{
		dataWriter.println("<hr>");
	}

	public void br() 
	{
		dataWriter.print("<br>");
	}

	
	public void middot() 
	{
		dataWriter.println("&middot;");
	}

	public void middot2() 
	{
		dataWriter.println("&nbsp;&middot;&nbsp;");
	}

	
	public void tr() 
	{
		dataWriter.println("<tr>");		
	}
	
	public void trEnd() 
	{
		dataWriter.println("</tr>");		
	}

	public void td() 
	{
		dataWriter.println("<td>");		
	}
	
	public void tdEnd() 
	{
		dataWriter.println("</td>");		
	}

	public void td(Object s) 
	{
		dataWriter.println("<td>"+s+"</td>");		
	}

	public void td2(String cl, String inner) 
	{
		dataWriter.println("<td class="+cl+">"+inner+"</td>");		
	}

	public void anchor(String text, String link) 
	{
		dataWriter.println("<a href='"+link+"'>"+text+"</a>");		
	}

	public void anchorTar(String text, String link) 
	{
		dataWriter.println("<a href='"+link+"' target=_blank>"+text+"</a>");		
	}
	
	public void image(String src, String cl) 
	{
		dataWriter.print("<img src='"+src+"' class='"+cl+"'>");
	}
	
	public String image(BufferedImage img, File f, String cl) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		ImageIO.write(img, "png", f);
		
		String src = f.getName();
		dataWriter.print("<img src='"+src+"' class='"+cl+"'>");
		return src;
	}
		
	public String imagePar(BufferedImage img, File f, String cl) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		ImageIO.write(img, "png", f);
		
		String src = f.getParentFile().getName() + "/" + f.getName();
		dataWriter.print("<img src='"+src+"' class='"+cl+"'>");
		return src;
	}
}
