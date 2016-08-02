package apps.rc2;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppMain 
{
	public static void viewImage(HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		MappingService1970 eng = new MappingService1970();
		
		String id = request.getParameter("id");
        File f = new File("d:/nebula-rc2/images/" + id);
        
        response.reset();
        response.setBufferSize(eng.DEFAULT_BUFFER_SIZE);
        response.setContentType( eng.getMimeType(f) );
        response.setHeader("Content-Length", String.valueOf(f.length()) );
        response.setHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");
        
        eng.copyBytes(f, response);		
	}

	
	public static void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		PrintWriter out = response.getWriter();
		
		out.println("<form method=get>");
		out.println("<input type='text' name='q' value='https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSV0xtrhvHUXfbKauGeDDshxQA41eDUkm0UGJypBv0RJKNQrnkIvg'>");
		out.println("<input type='submit' value='Tim'>");
		out.println("</form><hr>");
		
		String q = request.getParameter("q");
		
		out.println("<img src='"+q+"'><br>");
		
		SearchEngine eng = new SearchEngine();
		
		List<String> items = eng.findSimilarImages(q, 30);
		out.println("<p>"+items.size()+" item(s) found<p>");
		for(String ik: items)
		{
			out.println("<img style='width: 64px; margin: 7px;' src='"+ik+"'>");			
		}
		
	}

}
