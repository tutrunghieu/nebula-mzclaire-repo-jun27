package apps.rc2.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.neabulae.rmap.RequestTarget;

import apps.rc2.engines.FolderSearchEngine;
import apps.rc2.engines.SearchEngine;
import apps.rc2.services.FileMappingService1970;

public class ImageController extends RequestTarget
{
	public void reindexAction()
	{
		FolderSearchEngine eng = new FolderSearchEngine();
		eng.indexFilesInFolder( new File("D:/nebula-rc2/images")  );
	}
	
	
	public void viewAction()
	throws Exception
	{
		FileMappingService1970 eng = new FileMappingService1970();
		
		String id = request.getParameter("id");
        File f = new File("d:/nebula-rc2/images/" + id);
        
        response.reset();
        response.setBufferSize(eng.DEFAULT_BUFFER_SIZE);
        response.setContentType( eng.getMimeType(f) );
        response.setHeader("Content-Length", String.valueOf(f.length()) );
        response.setHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");
        
        eng.copyBytes(f, response);				
	}
	
	private static SearchEngine eng = new FolderSearchEngine();
	
	public void folderAction()
	throws Exception
	{
		String q =  this.readParam("q", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSV0xtrhvHUXfbKauGeDDshxQA41eDUkm0UGJypBv0RJKNQrnkIvg");
		
		FolderSearchEngine eng = new FolderSearchEngine();
		
		List<String> items = eng.findSimilarImages(new URL(q), 30);
		for(int k=0; k<items.size(); k++)
		{
			String fk = items.get(k);
			fk = (new File(fk)).getName();
			items.set(k, fk);		
		}
		
		printResult(q, items);
	}
	
	
	public void searchAction()
	throws Exception 
	{
		String q =  this.readParam("q", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSV0xtrhvHUXfbKauGeDDshxQA41eDUkm0UGJypBv0RJKNQrnkIvg");
		List<String> items = eng.findSimilarImages(q, 30);
		printResult(q, items);
	}
				
	private void printResult(String q, List<String> items) 
	{
		out.println("<form method=get>");
		out.println("<input type='text' name='q' value='"+q+"'>");
		out.println("<input type='submit' value='Tim'>");
		out.println("</form><hr>");
		
		String b = this.baseUri();
		
		out.println("<img src='"+q+"'><br>");
		
		out.println("<p>"+items.size()+" item(s) found<p>");
		for(String ik: items)
		{
			out.println("<img style='width: 64px; margin: 7px;' src='"+b+"/image/view?id="+ik+"'>");			
		}
		
		return;
	}		

}
