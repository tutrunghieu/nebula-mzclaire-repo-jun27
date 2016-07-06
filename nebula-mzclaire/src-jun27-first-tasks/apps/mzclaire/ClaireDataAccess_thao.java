package apps.mzclaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class ClaireDataAccess_thao extends FolderDataAccess {
	public static ClaireDataAccess_thao start() {
		return new ClaireDataAccess_thao();
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
}
