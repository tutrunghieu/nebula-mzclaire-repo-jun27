package tasks.task3_comparing_songs_Thao;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import apps.mzclaire.ClaireDataAccess;
import tasks.task3_comparing_movies_DucAnh.ClaireMovieCrawler;

public class ClaireSongCrawler extends ClaireMovieCrawler {

	public static ClaireSongCrawler start() {
		return new ClaireSongCrawler();
	}

	public ClaireSong crawlSongWithLink(String linkSong) throws Exception {
		ClaireSong res = new ClaireSong();

		Field fk = null;
		Document d = Jsoup.parse(new URL(linkSong), 3000);
		for (Element dk: d.getAllElements()) {
			if( dk.tagName().equals("th") 
					&& outerContextContains(dk, "infobox vevent")
					&& !outerContextContains(dk, "th#summary;") 
					&& !outerContextContains(dk, "th#description;")
					&& !outerContextContains(dk, "th#summary album;")) {
				Field[] fields = ClaireSong.class.getFields();
				for (Field f: fields) {
					String fieldName = "s_" + dk.text().replace(" ", "_").replace("(s)", "").toLowerCase();
					if (f.getName().equals(fieldName)) {
						fk = ClaireSong.class.getDeclaredField("s_" + dk.text().replace(" ", "_").replace("(s)", "").toLowerCase());
						break;
					}
				}
			}

			if( dk.tagName().equals("td") 
					&& outerContextContains(dk, "infobox vevent")
					&& !outerContextContains(dk, "th#summary;")) {
				if (dk.getElementsByTag("table").size() == 0 && dk.getElementsByTag("img").size() == 0) {
					if(fk != null) fk.set(res, dk.text());
				}
			}

			if (dk.tagName().equals("th") && outerContextContains(dk, "th#summary")) {
				fk = ClaireSong.class.getField("s_title");
				if(fk!= null) fk.set(res, dk.text());
			}
		}

		File tar = ClaireDataAccess.start().getStockSongFileForUrl(linkSong, ".jss");
		System.out.println("From: " + linkSong);
		System.out.println("To: " + tar.getAbsolutePath());

		ObjectMapper json = new ObjectMapper();
		json.writeValue(tar, res);

		return res;
	}


}
