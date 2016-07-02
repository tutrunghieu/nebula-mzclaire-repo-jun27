package apps.recom;

import java.io.File;

public class UniformRecommemderMovie extends UniformRecommemder<ClaireMovie> 
{

	@Override
	protected ClaireMovie readItem(File fk) throws Exception 
	{
			ClaireMovie res = new ClaireMovie();
			//res.fileName = fk.getName();
			//res.fileSize = fk.length() + "";
			return res;
	}
	
}
