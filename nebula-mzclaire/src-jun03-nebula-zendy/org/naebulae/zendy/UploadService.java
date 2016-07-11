package org.naebulae.zendy;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadService 
{
    public static final int DEFAULT_BUFFER_SIZE = 10240;        
	
    public static void writeImage(BufferedImage img, HttpServletResponse out)
    throws Exception
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", buf);
        buf.flush();
         
        out.setContentType("image/jpeg");
        out.getOutputStream().write(buf.toByteArray()); 
         
        buf.close();
    }
    
	public static void writeBytes(HttpServletResponse response, File file) 
	throws Exception 
	{
		copyBytes(file, response, DEFAULT_BUFFER_SIZE);
	}

	public static void copyBytes(File file, HttpServletResponse response, int DEFAULT_BUFFER_SIZE) 
	throws Exception 
	{
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

		BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		
		int length;
		while( (length = input.read(buffer)) > 0 ) 
		{
			output.write(buffer, 0, length);
		}
		
		output.close();
		input.close();
	}

	public static String contentType(File file, HttpServletRequest request) 
	{
        String contentType = request.getServletContext().getMimeType(file.getName());
        return (contentType == null ? "application/octet-stream" : contentType);
	}

	public static void writeHeader(HttpServletResponse response, String type, File file)
	{
        response.reset();
        response.setBufferSize(UploadService.DEFAULT_BUFFER_SIZE);
        response.setContentType(type);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	}

}
