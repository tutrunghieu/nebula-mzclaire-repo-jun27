package org.naebulae.util.dali;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class ByteUtils 
{
	
	public static void copyBytes(File file, OutputStream response, int SIZE) 
	throws Exception 
	{
		int length;
		byte[] buffer = new byte[SIZE];
		
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), SIZE);
		BufferedOutputStream output = new BufferedOutputStream(response, SIZE);
		
		while( (length = input.read(buffer)) > 0 ) { output.write(buffer, 0, length); }
		
		output.close();
		input.close();
	}
	
	public static void copyBytes(File src, File tar) 
	throws Exception
	{
		InputStream input = new FileInputStream(src);
		OutputStream output = new FileOutputStream(tar);
		IOUtils.copy(input, output);
		output.close();
		input.close();
	}
	

	public static void copyBytes(InputStream input, OutputStream output) 
	throws Exception
	{
		IOUtils.copy(input, output);
	}

	public static void copyBytes(InputStream input, File outputFile) 
	throws Exception
	{
		OutputStream output = new FileOutputStream(outputFile);
		IOUtils.copy(input, output);
		output.close();
	}
}
