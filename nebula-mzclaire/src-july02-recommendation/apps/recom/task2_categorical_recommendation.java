package apps.recom;

import java.util.Map;

public class task2_categorical_recommendation 
{
	public static void main(String[] args) 
	{
		Map<String, Double> pC = RandUtils.buildMap("action", 1, "drama", 2, "comedy", 7);
		//System.out.println(pC);
		
		for(int k=0; k<100; k++)
		{
			String nk = RandUtils.nextKeyCat(pC);
			System.out.print(nk + " ");			
		}
		
	}

}
