package tasks.task4_crawling_tutorial;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class task1_first_crawling_task {

	public static void main(String[] args)
	throws Exception
	{
		String url = "https://en.wikipedia.org/wiki/X-Men:_First_Class";
		
		Document d = Jsoup.parse(new URL(url), 3000);
		for(Element ek: d.getAllElements())
		{
			if(ek.tagName().equals("h2"))
				System.out.println(ek.text());
		}

	}

}
