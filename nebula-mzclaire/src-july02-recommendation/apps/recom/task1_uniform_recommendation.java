package apps.recom;

import java.util.LinkedHashSet;
import java.util.Set;

import apps.mzclaire.ClaireDataAccess;

public class task1_uniform_recommendation 
{

	public static void main(String[] args)
	throws Exception
	{
		UniformRecommemder<ClaireMovie> eng = new UniformRecommemderMovie();
		eng.setNanoCoin();
		eng.setStockFolder( ClaireDataAccess.start().getStockMovieFolder() );

		for(ClaireMovie mk: eng.pickItems(10))
		{
			//System.out.println(mk.fileName);
		}
		
		
//		Set<String> fields = new LinkedHashSet<String>();
//		fields = preprocessFields(fields);
//		
//		for(String fk: fields) System.out.println("\tpublic String m_"+fk+";");
	}

	private static Set<String> preprocessFields(Set<String> fields) 
	{
		Set<String> ignore = createSet("alice_in_wonderland_(1985_tv_film)", 
				"devil's_pond",
				"a_river_runs_through_it", "black_fox:_the_rise_and_fall_of_adolf_hitler");

		Set<String> res = new LinkedHashSet<String>();
		
		for(String fk: fields)
		if(! ignore.contains(fk) )
		{
			String tk = fk.replace(' ', '_').replace("(s)", "").toLowerCase();
			res.add(tk);
		}
		
		return res;
	}

	private static Set<String> createSet(String... args) 
	{
		Set<String> res = new LinkedHashSet<String>();
		for(String ak: args) res.add(ak);
		return res;
	}

}
