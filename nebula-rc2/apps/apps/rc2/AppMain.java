package apps.rc2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neabulae.rmap.RequestHeplers;
import org.neabulae.rmap.RequestMapper;
import org.neabulae.rmap.RequestTarget;

import apps.rc2.controllers.AndyController;

public class AppMain 
{
	private static RequestMapper mapper = RequestMapper.start(AndyController.class);
	private static RequestHeplers helpers = RequestHeplers.start();
	
	public static void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			Class<? extends RequestTarget> r =  mapper.findTargetController(request.getRequestURI(), AndyController.class);
			System.out.println("Switching to: " + r.getName());
		
			RequestTarget tar = r.newInstance();
			tar.enrichRequest(mapper, helpers, request, response);			
			tar.processRequest();
		}
		
		catch(Exception xp)
		{
			System.out.println("============ Controller mapping error");
			xp.printStackTrace(System.out);
		}		
	}
	
}
