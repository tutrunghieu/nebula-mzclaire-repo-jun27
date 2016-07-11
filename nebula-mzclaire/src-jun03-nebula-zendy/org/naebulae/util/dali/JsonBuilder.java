package org.naebulae.util.dali;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonBuilder 
{
	private Map<String, Object> data = new LinkedHashMap<String, Object>();
	
	public Map<String, Object> unbox()
	{
		return data;
	}

	public void copyFieldsFrom(Object src) throws Exception
	{
		for(Field fk: getPublicFields(src))
			data.put(fk.getName(), fk.get(src));
	}


	public static List<Field> getPublicFields(Object ik) 
	{
		List<Field> res = new ArrayList<Field>();
		
		for(Field fk: ik.getClass().getFields())
			if( Modifier.isPublic(fk.getModifiers()) ) res.add(fk);
		
		return res;
	}

	public<T1> void addList(List<T1> items, String prefix) throws Exception
	{
		List<Object> res = new ArrayList<Object>();
		
		for(T1 ik: items) 
		{
			if(ik instanceof LinkedHashMap) 
			{
				res.add(ik);
			}
			
			else if(ik instanceof JsonBuilder) 
			{
				JsonBuilder t = (JsonBuilder)ik; 
				res.add( t.unbox() );
			}
			
			else
			{
				Map<String, Object> rk = new LinkedHashMap<String, Object>(); 
				for(Field fk: getPublicFields(ik))
					rk.put(fk.getName(), fk.get(ik));
				
				res.add(rk);
			}
		}
		
		data.put(prefix, res);		
	}

}
