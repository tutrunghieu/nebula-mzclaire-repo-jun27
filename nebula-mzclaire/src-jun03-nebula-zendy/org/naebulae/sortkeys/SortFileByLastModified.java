package org.naebulae.sortkeys;

import java.io.File;
import java.util.Comparator;

public class SortFileByLastModified implements Comparator<File> 
{

	@Override
	public int compare(File a, File b)
	{
		int ak = (int)a.lastModified();
		int bk = (int)b.lastModified();
		return bk - ak;
	}

}
