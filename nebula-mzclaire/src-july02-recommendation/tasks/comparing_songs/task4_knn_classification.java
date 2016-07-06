package tasks.comparing_songs;

public class task4_knn_classification 
{
public static void main(String[] args)
{
	Classifier<double[], String> c = new MyClassifier();
	c.train(new double[] { 100, 0, 0 }, "red");
	c.train(new double[] { 101, 0, 0 }, "red");
	c.train(new double[] { 102, 0, 0 }, "red");
	c.train(new double[] { 105, 0, 0 }, "red");
	
	c.train(new double[] { 0, 301, 0 }, "green");
	c.train(new double[] { 0, 309, 0 }, "green");
	c.train(new double[] { 0, 304, 0 }, "green");
	c.train(new double[] { 0, 305, 0 }, "green");
	c.train(new double[] { 0, 307, 0 }, "green");
	
	c.train(new double[] { 0, 0, 290 }, "blue");
	c.train(new double[] { 0, 0, 291 }, "blue");
	c.train(new double[] { 0, 0, 293 }, "blue");
	c.train(new double[] { 0, 0, 298 }, "blue");
	
	String res = c.predict(new double[] { 105, 0, 0 });
	System.out.println(res);
	
	res = c.predict(new double[] { 120, 0, 0 });
	System.out.println(res);
	
	String res1 = c.predict(new double[] { 100, 301, 0 });
	System.out.println(res1);
}

}
