package org.naebulae.zendy.guru;

import java.lang.reflect.Method;

import org.naebulae.util.japi.JapiMeaning;
import org.naebulae.util.japi.JapiQueryParams;

public class ZendyDataMethod 
{
	private ZendyDataClass dataGroup;
	private Method dataMethod;

	public static ZendyDataMethod start(Method mk, ZendyDataClass group) 
	{
		ZendyDataMethod res = new ZendyDataMethod();
		res.dataGroup = group;
		res.dataMethod = mk;
		return res;
	}
	
	public Method reflectMethod()
	{
		return dataMethod;
	}
	
	public String reflectMethodName() 
	{
		return dataMethod.getName();
	}

	
	public Class<?> reflectClass()
	{
		return dataGroup.reflectClass();
	}

	public ZendyDataClass reflectClassRef()
	{
		return dataGroup;
	}

	public String japiMeaning(String dv) 
	{
		JapiMeaning m = dataMethod.getAnnotation(JapiMeaning.class);
		return m==null ? dv : m.value();
	}

	public String japiQueryParams(String dv) 
	{
		JapiQueryParams m = dataMethod.getAnnotation(JapiQueryParams.class);
		return m==null ? dv : m.value();
	}

}
