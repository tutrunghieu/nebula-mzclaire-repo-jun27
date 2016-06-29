package tasks.task4_crawling_tutorial;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class task2_crawling_sidebar {

	public static void main(String[] args)
	throws Exception
	{
		String url = "https://en.wikipedia.org/wiki/X-Men:_First_Class";
		
		Document d = Jsoup.parse(new URL(url), 3000);
		
		Element  t = firstTable(d);
		if(t == null) return;
		
		for(Element ek: t.getAllElements())
		if(ek.tagName().equals("tr"))
		{
			System.out.println(ek.tagName() + ":" + ek.text());
		}
	}

	private static Element firstTable(Document d) 
	{
		for(Element ek: d.getAllElements())
			if(ek.tagName().equals("table")
					&& ek.className().equals("infobox vevent") )
						return ek;
		
		return null;
	}

}
