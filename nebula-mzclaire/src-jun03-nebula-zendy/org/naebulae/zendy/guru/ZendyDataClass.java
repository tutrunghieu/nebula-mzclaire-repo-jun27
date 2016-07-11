package org.naebulae.zendy.guru;

import org.naebulae.util.japi.JapiNumber;

public class ZendyDataClass 
{
	private Class<?> dataClass;

	public static ZendyDataClass start(Class<?> ck) 
	{
		ZendyDataClass res = new ZendyDataClass();
		res.dataClass = ck;
		return res;
	}

	public Class<?> reflectClass() 
	{
		return this.dataClass;
	}


	public String japiNumber(String dv) 
	{
		JapiNumber m = dataClass.getAnnotation(JapiNumber.class);
		return m==null ? dv : m.value();
	}

}
