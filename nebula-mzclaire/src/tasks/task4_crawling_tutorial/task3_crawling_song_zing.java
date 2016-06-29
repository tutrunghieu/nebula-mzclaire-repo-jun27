package tasks.task4_crawling_tutorial;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class task3_crawling_song_zing {

	public static void main(String[] args)
	throws Exception
	{
		String url = "http://mp3.zing.vn/nghe-si/Phan-Manh-Quynh";
		
		Document d = Jsoup.parse(new URL(url), 3000);
		
		for(Element ek: d.getAllElements())
		if(ek.tagName().equals("a"))
		{
			System.out.println( ek.attr("href") );
		}
	}

}
