package tasks.task3_comparing_movies_DucAnh;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import apps.mzclaire.ClaireDataAccess;
import apps.recom.*;

public class ClaireMovieCrawler {

	public static ClaireMovieCrawler start() 
	{
		return new ClaireMovieCrawler();
	}

	public ClaireMovie crawlMovieInside(String url) throws Exception
	{
		ClaireMovie res = new ClaireMovie();
		
		Field fk = null;
		
		Document d = Jsoup.parse(new URL(url), 3000);
		for(Element dk: d.getAllElements())
		{
			
			if( dk.tagName().equals("th") 
					&& outerContextContains(dk, "infobox vevent")
					&& !outerContextContains(dk, "th#summary;"))
			{
				//String tk = "public String m_" + dk.text().replace(" ", "_").toLowerCase() + ";";
				String tk = dk.text().replace(" ", "_").toLowerCase();
				try {
					if(tk.length() > 0) fk = ClaireMovie.class.getField("m_" + tk);
				}
				catch (Exception e) {
					System.out.println("java.lang.NoSuchFieldException: " + tk);
				}
			}
			
			if( dk.tagName().equals("td") 
					&& outerContextContains(dk, "table#infobox vevent;")
					&& !outerContextContains(dk, "th#summary;")
					&& !interContextContains(dk, "div#plainlist;"))
			{
				if(fk != null) fk.set(res, dk.text());
			}
			
			if (dk.tagName().equals("img") && outerContextContains(dk, "table#infobox vevent;"))
			{
				fk = ClaireMovie.class.getField("m_poster");
				if (fk != null)
				{
					fk.set(res, dk.absUrl("src"));
				}
			}
			
			if( dk.tagName().equals("h2") 
					&& outerContextContains(dk, "div#mw-body; body#mediawiki") )
			{
//				System.out.println(outerContext(dk) );
				
				String tk = dk.text().replace(' ', '_').toLowerCase();
				
				if(tk.startsWith("cast") ) fk = ClaireMovie.class.getField("m_cast");			
				else if(tk.startsWith("plot") ) fk = ClaireMovie.class.getField("m_plot");			
				else if(tk.startsWith("reception") ) fk = ClaireMovie.class.getField("m_reception");			
				else if(tk.startsWith("production") ) fk = ClaireMovie.class.getField("m_production");
				else if(tk.startsWith("sequel") ) fk = ClaireMovie.class.getField("m_sequel");
				else if(tk.startsWith("marketing") ) fk = ClaireMovie.class.getField("m_marketing");
				else if(tk.startsWith("reference") ) fk = ClaireMovie.class.getField("m_reference");
				else if(tk.startsWith("external_link") ) fk = ClaireMovie.class.getField("m_external_link");
				else fk = null;
				
			}
			
			if( dk.tagName().equals("p")) 
				if(fk != null)
				{
					Object vk = fk.get(res);
					String new_vk = (vk==null ? dk.text() : vk + " | " + dk.text());
					fk.set(res, new_vk);
				}
			
			if( dk.tagName().equals("li") && outerContextContains(dk, "div#plainlist; td#;")) {
				if(fk != null)
				{
					Object vk = fk.get(res);
					String new_vk = (vk==null ? dk.text() : vk + " | " + dk.text());
					fk.set(res, new_vk);
				}
			}
			
			if( dk.tagName().equals("li") && 
					(outerContextContains(dk, "ol#references;") 
					|| outerContextContains(dk, "div#mw-body; body#mediawiki"))) {
				if(fk != null)
				{
					Object vk = fk.get(res);
					String new_vk = (vk==null ? dk.text() : vk + " | " + dk.text());
					fk.set(res, new_vk);
				}
			}
			
			
		} //for each element in Jsoup

		{
			File tar = ClaireDataAccess.start().getStockMovieFileForUrl(url, ".jsmv"); 
			System.out.println("From: " + url);
			System.out.println("To: " + tar.getAbsolutePath());
			
			ObjectMapper json = new ObjectMapper();
			json.writeValue(tar, res);			
		}
		
		return res;
	}

	private boolean interContextContains(Element dk, String patt) {
		return interContext(dk).contains(patt);
	}

	public boolean outerContextContains(Element dk, String patt)
	{
		return outerContext(dk).contains(patt);
	}

	public String outerContext(Element dk) 
	{
		String res = "";
		
		Element cur = dk;
		
		for(int k=0; k<6 && cur != null; k++)
		{
			res = res + cur.tagName() + "#" + cur.className() + "; ";
			cur = cur.parent();
		}
		
		return res;
	}
	
	public String interContext(Element dk) 
	{
		String res = "";
		
		Element cur = dk;
		
		for(int k=0; k<6 && cur != null; k++)
		{
			res = res + cur.tagName() + "#" + cur.className() + "; ";
			try {
				cur = cur.child(0);
			} catch (Exception e) {
				break;
			}
		}
		
		return res;
	}

}
