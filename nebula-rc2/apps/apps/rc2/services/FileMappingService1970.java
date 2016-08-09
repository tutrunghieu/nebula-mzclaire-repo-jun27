package apps.rc2.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;


public class FileMappingService1970 
{
	   public final int DEFAULT_BUFFER_SIZE = 10240;        
		
		public void copyBytes(File file, HttpServletResponse response) 
		throws Exception 
		{
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(file) );

			BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream() );

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			
			int length;
			while( (length = input.read(buffer)) > 0 ) 
			{
				output.write(buffer, 0, length);
			}
			
			output.close();
			input.close();
		}



		public String getMimeType(File f) 
		throws Exception
		{
			 Path source = Paths.get(f.getAbsolutePath());
			 return Files.probeContentType(source);
		}
		
}
