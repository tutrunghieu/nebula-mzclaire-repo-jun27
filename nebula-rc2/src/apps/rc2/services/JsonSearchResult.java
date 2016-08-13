package apps.rc2.services;

import java.util.ArrayList;
import java.util.List;

public class JsonSearchResult 
{
	public int error;
	public String errorMessage;
	
	public String queryImage;
	public int numberOfItems;
	
	public List<String> retItems = new ArrayList<String>();

}
