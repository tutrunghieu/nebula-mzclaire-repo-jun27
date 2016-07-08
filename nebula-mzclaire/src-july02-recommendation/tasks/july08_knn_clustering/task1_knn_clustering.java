package tasks.july08_knn_clustering;

public class task1_knn_clustering 
{
	
public static void main(String[] args)
throws Exception
{
	ClusteringEngine<double[]> c = new ClusteringEngine<double[]>();
	c.add(new double[] { 100, 0, 0 });
	c.add(new double[] { 101, 0, 0 });
	c.add(new double[] { 102, 0, 0 });
	c.add(new double[] { 105, 0, 0 });
	
	c.add(new double[] { 0, 301, 0 });
	c.add(new double[] { 0, 309, 0 });
	c.add(new double[] { 0, 304, 0 });
	c.add(new double[] { 0, 305, 0 });
	c.add(new double[] { 0, 307, 0 });
	
	c.add(new double[] { 0, 0, 290 });
	c.add(new double[] { 0, 0, 291 });
	c.add(new double[] { 0, 0, 293 });
	c.add(new double[] { 0, 0, 298 });
	
	c.predictWithKmeans(3, 5, ClusterDouble.class);
	
	System.out.println("=================");
	for(int k=0; k<c.size(); k++)
	{
		System.out.println(c.getPoint(k) + " -> " + c.getLabel(k));
	}
}

}
