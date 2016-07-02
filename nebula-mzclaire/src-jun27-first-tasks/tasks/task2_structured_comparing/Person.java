package tasks.task2_structured_comparing;

public class Person {

	@JaccardTag(JaccardHelperTen.class)
	public String ten;
	
	@JaccardTag(value=JaccardHelperDiachi.class)
	public String diachi;
	
	public String abc = "123 456";
	
	public String xyz = "" + Math.random();

	public Person()
	{
		
	}
	
	public Person(String t, String dc)
	{
		ten = t;
		diachi = dc;
	}

}
