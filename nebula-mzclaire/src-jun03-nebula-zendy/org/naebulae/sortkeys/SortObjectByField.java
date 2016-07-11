package org.naebulae.sortkeys;

import java.lang.reflect.Field;
import java.util.Comparator;

public abstract class SortObjectByField implements Comparator<Object> 
{
	private Field dataField;

	public SortObjectByField(Field fk)
	{
		dataField = fk;
	}

	private static Object getValue(Object a, Field f) 
	{
		try { return f.get(a); }
		catch(Exception xp) { return null; }
	}
	
	@Override
	public int compare(Object a, Object b)
	{
		Object ak = getValue(a, dataField);
		Object bk = getValue(b, dataField);
		return compareFields(ak, bk);
	}

	protected abstract int compareFields(Object ak, Object bk); 

}
