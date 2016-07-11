package org.naebulae.sortkeys;

import java.util.Comparator;

public class SortClassByName implements Comparator<Class<?>> 
{

	@Override
	public int compare(Class<?> a, Class<?> b)
	{
		String ak = a.getName();
		String bk = b.getName();
		return ak.compareTo(bk);
	}

}
