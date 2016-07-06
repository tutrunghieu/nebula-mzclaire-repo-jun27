package org.naebulae.access;

import java.io.File;

public class FolderLevel 
{
	public File dataFile;
	public int dataLevel;

	public FolderLevel(File file, int level) 
	{
		dataFile = file;
		dataLevel = level;
	}

}
