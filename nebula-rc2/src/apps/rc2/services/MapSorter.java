package apps.rc2.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapSorter {

	public static List<Entry<String, Integer>> sortValueWithStream(Map<String, Integer> hist) 
	{
		return hist.entrySet().stream()
		.sorted((x,y) -> sortedByCount(x, y))
		.collect(Collectors.toList());
	}

	private static int sortedByCount(Entry<String, Integer> x, Entry<String, Integer> y) 
	{
		int xk = x.getValue();
		int yk = y.getValue();
		return yk - xk;
	}
	
}
