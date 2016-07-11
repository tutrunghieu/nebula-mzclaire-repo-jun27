package org.naebulae.util.dali;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.naebulae.util.String2;

public class JsonImage extends JsonImage1970 
{
	public int imageWidth;
	public int imageHeight;
	public String imageUrl;
	
	public static JsonImage fromUrl(String url) 
	{
		if(url == null) return JsonImage.error();
		
		if(url.startsWith("local-file://")) 
		{ 
			url =  url.substring(LOCAL_FILE.length());
			return fromLocalUrl(url, __BASE_URL);
		}
		
		File jsonFile = new File(DATA_CACHED_IMAGES() + "/" + String2.identifier256(url) + ".json");
		
		if( jsonFile.exists() ) 
		try { return json.readValue(jsonFile, JsonImage.class); }
		catch(Exception xp) { }
		
		File dataFile = new File(DATA_CACHED_IMAGES() + "/" + String2.identifier256(url) );
		
		if(! dataFile.exists() )
		try { downloadFile(url, dataFile); }
		catch(Exception xp) 
		{
			System.out.println("!!! Cannot download image: " + url); 
			return JsonImage.error(); 
		}		
			
		try { return createJsonImage(dataFile, jsonFile, url); }
		catch(Exception xp)
		{
			System.out.println("!!! Cannot create json " + xp.getClass().getName()); 
			return JsonImage.error(); 
		}
	}

	private static JsonImage createJsonImage(File dataFile, File jsonFile, String url) 
	throws Exception
	{
//		System.out.println("Loading " + dataFile);
//		System.out.println("Saving " + jsonFile);
		
		BufferedImage img = (BufferedImage)ImageIO.read(dataFile);
		
		JsonImage res = new JsonImage();
		res.imageUrl = url;
		res.imageWidth = (img==null ? 0 : img.getWidth());
		res.imageHeight = (img==null ? 0 : img.getHeight());
		
		System.out.println("Create json image " + jsonFile.getAbsolutePath());
		jsonFile.getParentFile().mkdirs();
		json.writeValue(jsonFile, res);
		
		return res;
	}

	private static void downloadFile(String url, File dataFile)
	throws Exception
	{
			System.out.println("Download and save image " + dataFile.getAbsolutePath());
			BufferedImage img = (BufferedImage) ImageIO.read(new URL(url));
			
			dataFile.getParentFile().mkdirs();
			ImageIO.write(img, "png", dataFile);
	}

	public static JsonImage fromLocalUrl(String idf, String repl) 
	{
		File jsonFile = new File(DATA_CACHED_IMAGES() + "/" + idf + ".json");
		
		System.out.println(jsonFile.getAbsolutePath());
		
		if( jsonFile.exists() ) 
		try { return json.readValue(jsonFile, JsonImage.class); }
		catch(Exception xp) { }
		
		
		File dataFile = LocalFileService.getStockFile(idf);
		
		String url = repl + idf;
		try { return createJsonImage(dataFile, jsonFile, url); }
		catch(Exception xp)
		{
			System.out.println("!!! Cannot create json " + xp.getClass().getName()); 
			return JsonImage.error(); 
		}		
	}

	public static JsonImage error() 
	{
		JsonImage res = new JsonImage();
		
		res.imageWidth = 1;
		res.imageHeight = 1;
		res.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7e/1328101953_Symbol-Delete.png";
		
		return res;
	}


}
