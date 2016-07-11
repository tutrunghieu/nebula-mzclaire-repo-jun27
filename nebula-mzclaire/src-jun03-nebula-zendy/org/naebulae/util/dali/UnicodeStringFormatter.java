package org.naebulae.util.dali;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

public class UnicodeStringFormatter extends JsonSerializer<String>
{

	@Override
	public void serialize(String value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException 
	{
		ObjectMapper eng = new ObjectMapper();
		value = eng.writeValueAsString(value);
		value = unicode(value);
		jgen.writeRawValue(value);

	}
	
	private static String unicode(String s) 
	{
		String res = "";
		for(int n=s.length(), k=0; k<n; k++)
		{
			char ck = s.charAt(k);
			
			if(ck < 256) res += ck;
			else res += "\\u" + unicode(ck);
		}
		
		return res;
	}
	
	private static String unicode(char ck) 
	{
		String res = Integer.toHexString(ck);
		while(res.length() < 4) res = '0' + res;
		return res;
	}
		
	
}