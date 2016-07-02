package apps.recom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandUtils {

	public static Random coin = new Random(197);
	
	public static String nextKey(Map<String, Double> items) 
	{
		double r = coin.nextDouble(), s = 0, ds = 1d/items.size();
		
		for(String nk: items.keySet())
		{
			s += ds;
			if(r < s) return nk;
		}
		
		return null;
	}

	public static String nextKeyCat(Map<String, Double> items) 
	{
		double r = coin.nextDouble(), s = 0;
		
		for(String nk: items.keySet())
		{
			s += items.get(nk);
			if(r < s) return nk;
		}
		
		return null;
	}
	
	public static Map<String, Double> buildMap(Object... args) 
	{
		Map<String, Double> res = new LinkedHashMap<String, Double>();
		double s = 0;
		
		for(int k=0; k+1<args.length; )
		{
			String nk = (String)args[k++];
			Object vk = args[k++];
			
			double sk = (vk instanceof Integer ? (Integer)vk :
				vk instanceof Double ? (Double)vk :
				Double.parseDouble(vk.toString()) );
			
			s += sk;
			res.put(nk, sk);
		}
		
		if(s==0) s = 1;
		
		for(String nk: res.keySet())
		{
			res.put(nk, res.get(nk)/s);
		}
		
		return res;
	}

	public static List<String> buildList(String... args)
	{
		List<String> res = new ArrayList<String>();
		for(String ak: args) res.add(ak);
		return res;
	}

}
