package org.naebulae.util.func;

import java.util.ArrayList;
import java.util.List;

public class JsacJoint2<L, R> 
{
	public List<L> left;
	public List<R> right;
	
	public JsacJoint2()
	{
		left = new ArrayList<L>();
		right = new ArrayList<R>();
	}
	
}
