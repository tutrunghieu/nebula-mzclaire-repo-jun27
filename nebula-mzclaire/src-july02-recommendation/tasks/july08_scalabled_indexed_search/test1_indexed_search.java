package tasks.july08_scalabled_indexed_search;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class test1_indexed_search {

	public static void main(String[] args) 
	{
		String[] docs = { "w1 w2 w3", 
				"w3 w4",
				"w1 w2", 
				"w4 w3",
				"w1",
		};
		
		Map<String, Set<String>> index = new TreeMap<String, Set<String>>();
		
		for(String dk: docs)
		{
			System.out.println("====== " + dk.hashCode() + ": " + dk);
			for(String wj: dk.split("\\s+"))
			{
				Set<String> sj = index.get(wj);
				if(sj == null) index.put(wj, sj = new TreeSet<String>());
				sj.add(dk.hashCode() + "");
			}
		}
		
		for(String wj: index.keySet())
			System.out.println(wj + " -> " + index.get(wj));
		

		String q = "w1 w2";
		Map<String, Integer> R = new TreeMap<String, Integer>(); 
		for(String wj: q.split("\\s+"))
		{
			Set<String> sj = index.get(wj);
			//System.out.println("Querying " + wj + " -> " + sj);
			
			if(sj != null)
			for(String dk: sj)
			{
				Integer ck = R.get(dk);
				R.put(dk, ck==null ? 1 : ck+1);
				//System.out.println(dk + ": " + R.get(dk));
			}
		}
		
		for(String dk: R.keySet())
		System.out.println(dk + ":" + R.get(dk));
	}

}
