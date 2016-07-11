package tasks.july08_knn_clustering;

public class task2_knn_clustering_string 
{
	
public static void main(String[] args)
throws Exception
{
	ClusteringEngine<String> c = new ClusteringEngine<String>();
	c.add("red red red red go");
	c.add("red red red red to");
	c.add("red red red red red red hell");
	c.add("red red red red red please");
	c.add("red red red red please");
	
	c.add("green green green green go");
	c.add("green green green green to");
	c.add("green green green green green green hell");
	c.add("green green green green green please");
	c.add("green green green green please");
	
	c.add("blue blue blue blue go");
	c.add("blue blue blue blue to");
	c.add("blue blue blue blue blue blue hell");
	c.add("blue blue blue blue blue please");
	c.add("blue blue blue blue please");
	
//	c.add("blue blue blue blue please  green green green ");
	
	c.predictWithKmeans(3, 20, ClusterStringHenry.class);
	
	System.out.println("=================");
	for(int k=0; k<c.size(); k++)
	{
		System.out.println(c.getPoint(k) + " -> " + c.getLabel(k));
	}
}

}
