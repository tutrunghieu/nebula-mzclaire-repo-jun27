package org.naebulae.util.dali;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.codehaus.jackson.map.ObjectMapper;
import org.naebulae.util.String2;

public class LocalFileService extends ByteUtils
{
	public static File getStockFolder()
	{
		File f = new File( System.getProperty("user.home") + "/data-static-files");
		if( !f.exists() ) f.mkdirs();
		
		return f;
	}
	
	public static File getStockFile(String id) 
	{
		String pref = getStockFolder().getAbsolutePath();
		return new File(pref + "/" + id);
	}


	public static LocalFileMetag getStockMetag(String id) 
	{
		String pref = getStockFolder().getAbsolutePath();
		File tk_metag = new File(pref + "/" + id + ".metag");
		
		ObjectMapper engineJson = new ObjectMapper();
		try { return engineJson.readValue(tk_metag, LocalFileMetag.class); } 		
		catch(Exception xp) { return null; }
	}

	public static String getStockMineType(String id, String dv) 
	{
		LocalFileMetag res = getStockMetag(id);
		return res==null ? dv : res.mimeType;
	}
	
	
	private static void writeFileMetag(File sk, File tk_metag, Map<String, Object> tags)
	throws Exception
	{
		LocalFileMetag res = new LocalFileMetag();
		
		res.fileName = sk.getName();
		res.fileSize = "" + sk.length();
		
		res.fileExt = String2.afterLast('.', res.fileName);
		try { res.mimeType = getMimeType(sk); } catch(Exception xp) {}
		
		res.tags = tags;
		
		ObjectMapper engineJson = new ObjectMapper();
		engineJson.writeValue(tk_metag, res); 		
	}	
	

	public static Map<String, Object> buildMap(String... args)
	{
		Map<String, Object> res = new LinkedHashMap<String, Object>();
		
		for(int k=0; k+1<args.length; )
		{
			String nk = args[k++];
			String vk = args[k++];
			res.put(nk, vk);
		}
		
		return res;
	}

	
	private static String getMimeType(File f)
	throws Exception
	{
		 Path source = Paths.get(f.getAbsolutePath());
		 return Files.probeContentType(source);
	}
	

	
	public static String insertFileSized(File sk)
	throws Exception
	{
		String tk_name = String2.identifier256(sk.getAbsolutePath());
		return insertFileSized(sk, tk_name, buildMap());
	}
	
	public static String insertFileSized(File sk, String tk_name, Map<String, Object> tags)
	throws Exception
	{
		String pref = getStockFolder().getAbsolutePath();
		File tk_data = new File(pref + "/" + tk_name);
		File tk_metag = new File(pref + "/" + tk_name + ".metag");
		
		//if(! tk_data.exists() ) 
		{
			BufferedImage img = ImageIO.read(sk);
			
			int max = Math.max(img.getWidth(), img.getHeight());
			
			int tw = img.getWidth()*400/max;
			int th = img.getHeight()*400/max;
			
			BufferedImage res = new BufferedImage(tw, th, img.getType());
			Graphics2D g = res.createGraphics();
			g.drawImage(img, 0, 0, tw, th, null);
			g.dispose();	
			
			ImageIO.write(res, "png", tk_data);
		}
		
		//if(! tk_metag.exists() ) 
		{
			writeFileMetag(sk, tk_metag, tags);
		}

		return tk_name;
	}

	
	public static String insertFile(File sk)
	throws Exception
	{
		String tk_name = String2.identifier256(sk.getAbsolutePath());
		return insertFile(sk, tk_name, buildMap());
	}
	
	public static String insertFile(File sk, String tk_name)
	throws Exception
	{
		return insertFile(sk, tk_name, buildMap());
	}
	
	public static String insertFile(File sk, String tk_name, Map<String, Object> tags)
	throws Exception
	{
		String pref = getStockFolder().getAbsolutePath();
		
		File tk_data = new File(pref + "/" + tk_name);
		File tk_metag = new File(pref + "/" + tk_name + ".metag");
		
		if(! tk_data.exists() ) copyBytes(sk, tk_data);
		if(! tk_metag.exists() ) writeFileMetag(sk, tk_metag, tags);

		return tk_name;
	}


	
	public static String insertUrl(String url) 
	throws Exception
	{
		String tk_name = String2.identifier256(url);
		
		String pref = getStockFolder().getAbsolutePath();
		File tk_data = new File(pref + "/" + tk_name);
		File tk_metag = new File(pref + "/" + tk_name + ".metag");
		
		if(! tk_data.exists() ) 
		{ 
			URL uk = new URL(url); 
			copyBytes(uk.openStream(), tk_data); 
		}
		
		if(! tk_metag.exists() ) 
		{
			writeFileMetag(tk_data, tk_metag, buildMap());
		}

		return tk_name;
	}

	public static void exportBytes(String id, File tar) 
	throws Exception
	{
		String pref = getStockFolder().getAbsolutePath();
		File tk_metag = new File(pref + "/" + id);
		copyBytes(tk_metag, tar);
	}		
		

	
}
