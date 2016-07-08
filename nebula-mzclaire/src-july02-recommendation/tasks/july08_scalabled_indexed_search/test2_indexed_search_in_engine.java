package tasks.july08_scalabled_indexed_search;

public class test2_indexed_search_in_engine 
{
	public static void main(String[] args) 
	{
		String[] docs = { 
				"w1 w2 w3 I love you very much", 
				"w3 w4",
				"w1 w2", 
				"w4 w3",
				"w1",
		};
		
		IndexedSearchEngine eng = new IndexedSearchEngine(); 
		eng.index(docs);
		
		eng.printIndex();

		String q = "w1 w2";
		eng.searchAndPrint(q);
	}

}
