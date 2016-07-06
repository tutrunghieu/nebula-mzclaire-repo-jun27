package apps.mzclaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;

public class ClaireDataAccess extends FolderDataAccess {
	public static ClaireDataAccess start() {
		return new ClaireDataAccess();
	}
	
	public File getDesktopFile() 
	{
		return new File(System.getProperty("user.home") + "/Desktop"); 	
	}
	
	public File getDesktopFile(String fname) 
	{
		return new File(System.getProperty("user.home") + "/Desktop/" + fname); 	
	}
		

	public File getMovieCatalogFile() {
		return new File(System.getProperty("user.home") + "/Desktop/movie-list.txt");
	}

	public List<String> getMovieCatalogLinks() throws Exception {
		File path = getMovieCatalogFile();

		BufferedReader br = new BufferedReader(new FileReader(path));

		List<String> res = br.lines().collect(Collectors.toList());
		br.close();

		return res;
	}
	
	public File getSongCatalogFile() {
		return new File(System.getProperty("user.home") + "/Desktop/song-list.txt");
	}
	
	public List<String> getSongCatalogLinks() throws Exception {
		File path = getSongCatalogFile();
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		 
		List<String> res = br.lines().collect(Collectors.toList());
		br.close();
		
		return res;
	}

	public<T1> T1 readJsonObject(File f1, Class<T1> class1)
	throws Exception
	{
		ObjectMapper json = new ObjectMapper();
		return json.readValue(f1, class1);
	}

	public<T1> List<T1> readJsonList(File f, Class<T1> class1)
	throws Exception
	{
		ObjectMapper json = new ObjectMapper();
		
		List<T1> res = new ArrayList<T1>();
		
		File[] files = f.listFiles();
		
		if(files != null)
		for(File fk: files)
		{
			T1 rk = json.readValue(fk, class1);
			res.add(rk);
		}
		
		return res;
	}
}
