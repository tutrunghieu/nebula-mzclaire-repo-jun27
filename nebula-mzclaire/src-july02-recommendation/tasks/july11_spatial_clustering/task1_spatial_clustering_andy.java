package tasks.july11_spatial_clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

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

		for (int x = 0; x < Rx; x++)
		for (int y = 0; y < Ry; y++) 
		{
			int cxy = img.getRGB(x, y);
			Color ck = new Color(cxy);
			String hex = formatColor(ck);
			if(hex.equals("#00a2e8")) c.train(new double[]{x, y}, hex);
			if(hex.equals("#ed1c24")) c.train(new double[]{x, y}, hex);
		}
		
		//String res = c.predict(new double[] { 0, 14 });
		//System.out.println(res);
		
		File outputfile = FolderAccess.start().getDesktopFile("output.png");
		BufferedImage bufferedImage = ImageIO.read(f);
		for (int x = 0; x < Rx; x++)
		for (int y = 0; y < Ry; y++) 
		{
			String res = c.predict(new double[] { x, y });
			bufferedImage.setRGB(x, y, Color.decode(res).getRGB());
		}
		ImageIO.write(bufferedImage, "png", outputfile);

		
	}
	
	private static String formatColor(Color ck) {
		return String.format("#%02x%02x%02x", ck.getRed(), ck.getGreen(), ck.getBlue());
	}
	
}
