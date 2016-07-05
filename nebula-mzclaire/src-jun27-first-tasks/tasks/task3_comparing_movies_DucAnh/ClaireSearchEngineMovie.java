package tasks.task3_comparing_movies_DucAnh;

import tasks.task2_structured_comparing.JaccardEngineObject;
import apps.recom.ClaireMovie;
import apps.recom.ClaireSearchEngine;

public class ClaireSearchEngineMovie extends ClaireSearchEngine<ClaireMovie> {
	
	
	JaccardEngineObject e = new JaccardEngineObject();
	
	@Override
	public double jaccardIndex(ClaireMovie sk, ClaireMovie q) throws Exception {
		return e.jaccardIndex(sk, q);
	}

}
