package org.naebulae.zendy.guru;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.naebulae.util.Class2;

public class ZendyDataAccess 
{
	public static ZendyDataAccess start(Class<?> c0) 
	{
		ZendyDataAccess res = new ZendyDataAccess();
		res.addReflectedControllers(c0);
		return res;
	}

	private Map<String, Object> lut = new LinkedHashMap<String, Object>();  
	private Map<String, ZendyDataClass> lutClasses = new LinkedHashMap<String, ZendyDataClass>();  


	protected void addReflectedControllers(Class<?> c0) 
	{
		for(Class<?> ck: Class2.fetchClasses(c0))
		if( checkZendyClass(ck) )
		{
			ZendyDataClass sk = ZendyDataClass.start(ck);
			String uj = translateZendyClass(ck);
			lut.put(uj, sk);
			lutClasses.put(uj, sk);
			addReflectedMethods(uj, sk);
		}
		
		return;
	}

	protected void addReflectedMethods(String path, ZendyDataClass group) 
	{
		for(Method mk: group.reflectClass().getMethods())
		if( checkZendyMethod(mk) )
		{
			ZendyDataMethod sk = ZendyDataMethod.start(mk, group);
			String uj = translateZendyMethod(mk); 
			lut.put(path + uj, sk);
		} //for each spring method
		
		return;
	}
	
	protected boolean checkZendyClass(Class<?> ck) 
	{
		return ck.getName().endsWith("Controller")
				&& org.naebulae.zendy.BaseController.class.isAssignableFrom(ck);
	}
	

	protected String translateZendyMethod(Method mk) 
	{
		String name = mk.getName();
		return "/" + name.substring(0, name.length() - 6).toLowerCase();
	}
	
	protected String translateZendyClass(Class<?> ck) 
	{
		String name = ck.getSimpleName();
		return "/" + name.substring(0, name.length() - 10).toLowerCase();
	}
	
	protected boolean checkZendyMethod(Method mk) 
	{
		return Modifier.isPublic(mk.getModifiers())
				&& mk.getName().endsWith("Action");
	}
	
	public Set<String> getZendyClassLinks() 
	{
		return lutClasses.keySet();
	}
	
	public Set<String> getZendyLinks() 
	{
		return lut.keySet();
	}
	
	public ZendyDataClass getZendyClassFor(String p1) 
	{
		return (ZendyDataClass)lut.get(p1);
	}
	
	public ZendyDataMethod getZendyMethodFor(String p1) 
	{
		return (ZendyDataMethod)lut.get(p1);
	}
	
	public Object getZendyObjectFor(String p1) 
	{
		return lut.get(p1);
	}
	
	public List<ZendyDataMethod> getZendyMethodsFor(Class<?> cl) 
	{
		return getZendyMethodsFor(ZendyDataClass.start(cl));
	}
	
	public List<ZendyDataMethod> getZendyMethodsFor(ZendyDataClass gr) 
	{
		List<ZendyDataMethod> res = new ArrayList<ZendyDataMethod>();
		
		for(String nk: lut.keySet())
		{
			Object vk = lut.get(nk);
			if(vk instanceof ZendyDataMethod
					&& ((ZendyDataMethod)vk).reflectClassRef() == gr)
				res.add((ZendyDataMethod)vk);
		}
		
		return res;
	}

	public List<String> getZendyLinksFor(Class<?> cl) 
	{
		return getZendyLinksFor(ZendyDataClass.start(cl));
	}
	
	public List<String> getZendyLinksFor(ZendyDataClass gr) 
	{
		List<String> res = new ArrayList<String>();
		
		for(String nk: lut.keySet())
		{
			Object vk = lut.get(nk);
			if(vk instanceof ZendyDataMethod
					&& ((ZendyDataMethod)vk).reflectClass() == gr.reflectClass())
				res.add(nk);
		}
		
		return res;
	}

	public List<String> orderByJapiNumber(Set<String> src) 
	{
		Map<String, String> res = new TreeMap<String, String>();
		for(String sk: src)
		{
			ZendyDataClass ck = this.getZendyClassFor(sk);
			res.put(ck.japiNumber("") + " " + sk, sk);
		}
		
		return res.values().stream().collect(Collectors.toList());
	}

	public Map<String, Object> getZendyMap() 
	{
		return lut;
	}

	public void dump() 
	{
		for(String lk: this.orderByJapiNumber(this.getZendyClassLinks()) )
		{
			ZendyDataClass vk = this.getZendyClassFor(lk);
			System.out.println(lk + " -> " + vk.reflectClass().getName());
			
			for(String mj: this.getZendyLinksFor(vk))
			{
				System.out.println(mj + " -> " + this.getZendyMethodFor(mj).reflectMethodName());
			} //for each action
		} //for each controller
		
		return;
	}



}
