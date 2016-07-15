package tasks.july15_spaitial_clustering_review;

import java.util.Set;
import java.util.TreeSet;

public class PixelGrouper {


	Set<String> used = new TreeSet<String>();

	public boolean used(int x, int y) 
	{
		return used.contains(x + "/" + y);
	}

	public void markAsUsed(int x, int y) 
	{
		used.add(x + "/" + y);		
	}

	public int newGroup(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

}
