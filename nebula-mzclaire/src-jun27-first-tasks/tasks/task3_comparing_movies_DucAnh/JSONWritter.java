package tasks.task3_comparing_movies_DucAnh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.codehaus.jackson.map.ObjectMapper;

public class JSONWritter {
	public static void main(String[] args) throws Exception {
	}
	
	public static void pushJSON(String fname, Object obj) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		File f = new File(fname);
		if (!f.exists()) f.createNewFile();
		mapper.writeValue(f, obj);
	}
	
	
	
	public static String readJSON(String fname) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fname));
		String content = br.readLine();
		br.close();
		return content;
	}
	
}
