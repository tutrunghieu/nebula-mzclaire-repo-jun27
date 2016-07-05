package apps.recom;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;

public class UniformRecommemderMovie extends UniformRecommemder<ClaireMovie> 
{

	@Override
	protected ClaireMovie readItem(File fk) throws Exception 
	{
			ClaireMovie res = new ClaireMovie();
			ObjectMapper mapper = new ObjectMapper();
			res = mapper.readValue(fk, ClaireMovie.class);
			res.fileName = fk.getName();
			return res;
	}
	
}
