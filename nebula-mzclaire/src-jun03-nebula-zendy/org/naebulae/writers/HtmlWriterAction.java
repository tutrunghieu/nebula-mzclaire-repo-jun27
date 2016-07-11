package org.naebulae.writers;

public interface HtmlWriterAction<T1> 
{
	void invokeWriterAction(T1 src, HtmlWriter out);
}
