package tasks.task2_structured_comparing;

import java.util.LinkedHashMap;
import java.util.Map;

public class task1_compare_maps {

	public static void main(String[] args) 
	{
		Map<String, Object> m1 = buildMap("ten", "Hoang Anh", "dc", "120 Ha noi 2");
		System.out.println(m1);
		
		Map<String, Object> m2 = buildMap("ten", "Duc Anh", "dc", "450 Ha nam ninh");
		System.out.println(m2);
		
		JaccardEngineMap e = new JaccardEngineMap();
		System.out.println( e.jaccardIndex(m1, m2) );
	}
	

	private static Map<String, Object> buildMap(String... args)
	{
		Map<String, Object> res = new LinkedHashMap<String, Object>();
		
		for(int k=0; k+1<args.length; )
		{
			String nk = args[k++];
			String vk = args[k++];
			res.put(nk, vk);
		}
		
		return res;
	}

}
