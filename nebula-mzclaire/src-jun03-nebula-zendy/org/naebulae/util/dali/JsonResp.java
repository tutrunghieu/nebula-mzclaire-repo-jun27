package org.naebulae.util.dali;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JsonResp<T> 
{
	public int error;
	
	@JsonSerialize(using = UnicodeStringFormatter.class)	
	public String errorMessage = "";
	
	public String userLoggedIn = false + ""; 
	public T data;
	
	private JsonResp()
	{
		
	}
	
	private JsonResp(Class<T> cl) 
	{
		try { this.data = cl.newInstance(); }
		catch(Exception xp) { xp.printStackTrace(); }
	}
		
	public static<T1> JsonResp<T1> start(Class<T1> cl) 
	{
		return new JsonResp<T1>(cl);
	}
	
	public static<T1> JsonResp<List<T1>> startList(Class<T1> cl) 
	{
		JsonResp<List<T1>> res = new JsonResp<List<T1>>();
		
		try { res.data = new ArrayList<T1>(); }
		catch(Exception xp) { xp.printStackTrace(); }
		
		return res;
	}
	
	public JsonResp<T> init(JsonAction<T> lf) 
	{
		lf.invokeJsonAction(this);
		return this;		
	}

	public static Object error() 
	{
		return start(String.class)
				.init(x -> { x.error = 0; x.data = ""; } );
	}
	
	public static Object error(int code, String msg) 
	{
		return start(String.class)
				.init(x -> { x.error = code; x.data = msg; } );
	}

	public static Object error(int code, Exception xp) 
	{
		xp.printStackTrace();
		
		return start(String.class)
				.init(x -> { x.error = code; x.data = xp.getClass().getName(); } );
	}

	
}
