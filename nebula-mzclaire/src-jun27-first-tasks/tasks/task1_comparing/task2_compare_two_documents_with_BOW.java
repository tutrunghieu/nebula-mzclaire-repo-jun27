package tasks.task1_comparing;

public class task2_compare_two_documents_with_BOW 
{

	public static void main(String[] args) 
	{
		testString("I love you", "I love you");
		testString("I love you", "I love love you");
		testString("I love you", "I love you I love you");
		testString("I love you", "I love you I love you I love you");
	}

	private static void testString(String d1, String d2) 
	{
		JaccardEngine eng = new JaccardEngineBow();
		double s12 = eng.jaccardIndex(d1, d2);
		System.out.println(d1 + " ---- " + d2 + " ---- " + s12);
	}

	
}


