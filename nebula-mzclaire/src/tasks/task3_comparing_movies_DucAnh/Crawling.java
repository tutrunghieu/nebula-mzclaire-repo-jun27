package tasks.task3_comparing_movies_DucAnh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import apps.mzclaire.ClaireDataAccess;

public class Crawling {
	
	public static void main(String[] args) throws Exception {
		
		File currentDirFile = new File("");
		String path = currentDirFile.getAbsolutePath();
		System.out.println(path);
	
		File f = ClaireDataAccess.start().getStockMovieFolder();
		if (!f.exists()) {
			f.mkdir();
		}
		
		readAndWritterListMovie(path + "/data-tutorials/movie-list.txt");
		
		//actionCrawling("https://en.wikipedia.org/wiki/(500)_Days_of_Summer");
	}
	
	public static void readAndWritterListMovie(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String urlLine = "";
		while ((urlLine = br.readLine()) != null) {
			//System.out.println(urlLine.substring("https://en.wikipedia.org/wiki/".length()).replaceAll("[^\\p{Alpha}]+",""));
			actionCrawling(urlLine);
		}
		br.close();
	}
	
	
	public static void actionCrawling(String url) throws Exception {
		
		Document d = Jsoup.parse(new URL(url), 3000);
		
		Element t = firstTable(d);
		if(t == null) return;
		
		Map<String, String> m = new HashMap<String, String>();
		
		String k = "";
		String c = "";
		for(Element ek: t.getAllElements()) {
			if(ek.tagName().equals("th")) {
				k = ek.text().replace(" ", "_").toLowerCase();
			}
			if(ek.tagName().equals("td")) {
				for (Element ek1: ek.children()) {
					if (ek1.tagName().equals("div") 
							&& ek1.className().equals("plainlist")) {
						for (Element ek2: ek1.getAllElements()) {
							if (ek2.tagName().equals("li")) {
								c += ek2.text() + "|";
							}
						}
						break;
					}
				}
				if(c.length() == 0) c = ek.text();
			}
			if (k.length() > 0 && c.length() > 0) {
				m.put(k, c);
				k = "";
				c = "";
			}
			
		}
		
		for (Element ek: getContent(d).getAllElements()) {
			if(ek.tagName().equals("h2")) {
				for (Element ek1: ek.getAllElements()) {
					if (ek1.tagName().equals("span") && ek1.className().equals("mw-headline")) {
						k = ek1.text() ;
					}
				}
			}
			
			if (k.length() > 0 && ek.tagName().equals("p")) {
				String before_content = (m.get(k) != null ) ? m.get(k) : "";
				m.put(k, before_content + ek.text());
			}
		}
		
		String film_name = url.substring("https://en.wikipedia.org/wiki/".length());
		File f = ClaireDataAccess.start().getStockMovieFileForUrl(url, ".json");
		System.out.println("crawling..." + f.getAbsolutePath());
		if ( film_name.length() > 0 ) JSONWritter.pushJSON(f.getAbsolutePath(), m);
		
	}
	
	private static Element firstTable(Document d) {
		for(Element ek: d.getAllElements())
			if(ek.tagName().equals("table")
					&& ek.className().equals("infobox vevent") )
						return ek;
		
		return null;
	}
	
	private static Element getContent(Document d) {
		for (Element ek: d.getAllElements())
			if(ek.tagName().equals("div") && ek.className().equals("mw-content-ltr")) 
				return ek;
		return null;
	}
	
}
