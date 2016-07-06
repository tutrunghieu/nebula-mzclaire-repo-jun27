package tasks.task1_comparing;

import apps.mzclaire.jaccard.JaccardEngine;

public class task1_compare_two_documents 
{
	public static void main(String[] args) 
	{
		testString("I love you", "I love you");
		testString("I love you", "I love love you");
		
		testString("I love you", "I love her");
		testString("I love you", "I love her more");
		testString("I love you", "they hate her");
	}

	private static void testString(String d1, String d2) 
	{
		JaccardEngine eng = new JaccardEngine();
		double s12 = eng.jaccardIndex(d1, d2);
		System.out.println(d1 + " ---- " + d2 + " ---- " + s12);
	}
}


