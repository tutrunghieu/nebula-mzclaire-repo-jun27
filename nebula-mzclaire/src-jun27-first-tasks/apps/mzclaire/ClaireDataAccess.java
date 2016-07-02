package apps.mzclaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class ClaireDataAccess extends FolderDataAccess {
	public static ClaireDataAccess start() {
		return new ClaireDataAccess();
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

}
