package tasks.july14_spaitial_clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;

import tasks.comparing_songs.Classifier;
import tasks.comparing_songs.MyClassifier;

public class task1_spatial_clustering_andy {
	
	static Classifier<double[], String> c = new MyClassifier();
	
	public static void main(String[] args) 
			throws Exception {
		File f = FolderAccess.start().getDesktopFile("classification-1.png");

		BufferedImage img = ImageIO.read(f);

		int Rx = img.getWidth(), Ry = img.getHeight();

		Set<String> res = new TreeSet<String>();
		
		for (int x = 0; x < Rx; x++)
		for (int y = 0; y < Ry; y++) 
		{
			String hex = formatColor(new Color(img.getRGB(x, y)));
			if(!hex.equals("#ffffff")) res.add(hex);
		}
		
		for (int x = 0; x < Rx; x++)
		for (int y = 0; y < Ry; y++) 
		{
			int cxy = img.getRGB(x, y);
			Color ck = new Color(cxy);
			String hex = formatColor(ck);
			if(res.contains(hex) && !hex.equals("#ffffff")) c.train(new double[]{x, y}, hex);
		}
		
		File outputfile = FolderAccess.start().getDesktopFile("output.png");
		BufferedImage bufferedImage = ImageIO.read(f);
		for (int x = 0; x < Rx; x++)
		for (int y = 0; y < Ry; y++) 
		{
			String out = c.predict(new double[] { x, y });
			bufferedImage.setRGB(x, y, Color.decode(out).getRGB());
		}
		ImageIO.write(bufferedImage, "png", outputfile);
		
	}
	
	private static String formatColor(Color ck) {
		return String.format("#%02x%02x%02x", ck.getRed(), ck.getGreen(), ck.getBlue());
	}
	
}
