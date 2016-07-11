package org.naebulae.cached;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.codehaus.jackson.map.ObjectMapper;
import org.naebulae.util.String2;

public class CachedService extends CachedService1970 
{
	public static void saveImageFromLink(String link) 
	throws Exception
	{
		String id = "image-" + String2.identifier256(link);
		
		{
			File f = CachedService.getFileForId(id);
			
			saveImageToPng(new URL(link), f);
			System.out.println("Data file: " + f.getAbsolutePath());
		}
		
		{
			File f = CachedService.getFileForId(id + ".metag");
			
			ObjectMapper json = new ObjectMapper();
			json.writeValue(f, new LinkMetag(link, "png") );			
			System.out.println("Metag file: " + f.getAbsolutePath());
		}

		return;
	}

	public static void saveImageToPngTrick(URL url, File f) 
	throws Exception
	{
		BufferedImage t = ImageIO.read(url);
		
		f.getParentFile().mkdirs();
		ImageIO.write(t, "png", f);
	}

	private static void saveImageToPng(URL url, File f) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		FileOutputStream out = new FileOutputStream(f); 

		InputStream input = url.openStream();
		
		{
	      int c;
	      while ((c = input.read()) != -1) out.write(c);
		}
		
		input.close();
		out.close();
	}
	
	public static List<File> getImageFiles() 
	{
		List<File> res = new ArrayList<File>();
		
		File[] files = CachedService.getFileForRoot().listFiles();
		
		if(files != null) 
		for(File fk: files)
		{
			String nk = fk.getName();
		    if(nk.startsWith("image-") && !nk.endsWith(".metag") ) res.add(fk);
		}
		return res;
	}

}
