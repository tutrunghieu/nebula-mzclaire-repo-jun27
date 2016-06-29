package tasks.task2_structured_comparing;

public class task2_compare_objects {

	public static void main(String[] args) 
	throws Exception
	{
		Person m1 = new Person("Hoang Anh", "120 Ha noi 2");
		System.out.println(m1);
		
		Person m2 = new Person("Duc Anh", "450 Ha nam ninh");
		System.out.println(m2);
		
		JaccardEngineObject e = new JaccardEngineObject();
		System.out.println( e.jaccardIndex(m1, m2) );
	}
	

}
