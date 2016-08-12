package apps.rc2.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.neabulae.rmap.RequestTarget;

import apps.rc2.engines.FolderSearchEngine;
import apps.rc2.engines.SearchEngine;
import apps.rc2.services.FileMappingService1970;
import apps.rc2.services.JsonHelper;
import apps.rc2.services.JsonSearchResult;

public class BackendController extends RequestTarget
{
	
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

	private void printResult(String q, List<String> items)
	throws Exception
	{
		response.reset();
		response.setContentType("application/json; charset=UTF-8");
		
		JsonSearchResult res = JsonHelper.createSearchResult();
		
		res.queryImage = q;
		res.numberOfItems = items.size();
		
		for(String ik: items)
		{
			String uk = this.baseUrl() + "/image/view?id="+JsonHelper.encodeUrl(ik);
			res.retItems.add(uk);
		}
		
		JsonHelper.renderJson(res, response);
	}
	

}
