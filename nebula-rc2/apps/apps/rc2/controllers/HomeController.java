package apps.rc2.controllers;

import org.neabulae.rmap.RequestTarget;

public class HomeController extends RequestTarget
{
	public void indexAction()
	throws Exception
	{
		this.response.sendRedirect(this.baseUri() + "/image/search");
	}
	

}
