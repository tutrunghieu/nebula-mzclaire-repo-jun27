package org.naebulae.zendy;

import java.io.PrintWriter;

public abstract class HtmlResult<T> 
{
	public int error;
	
	public T data;
	
	public abstract void invokeResultAction(PrintWriter out);
}
