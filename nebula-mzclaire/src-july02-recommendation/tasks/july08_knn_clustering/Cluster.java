package tasks.july08_knn_clustering;

import java.util.ArrayList;
import java.util.List;

public class Cluster<T> 
{
	public int number;
	public List<T> members = new ArrayList<T>();
	
	public void add(T pk) 
	{
		members.add(pk);				
	}

	public void average() 
	{
		System.out.println("-----------" + number);
		for(T mk: members) System.out.println(mk);
	}

	public double distance(T pk) 
	{
		return 0;
	}

}
