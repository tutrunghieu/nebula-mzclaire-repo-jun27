package tasks.july11_spatial_clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;

import tasks.comparing_songs.Classifier;

public class task1_classification_Thao {

	public static void main(String[] args) 
			throws Exception
	{
		File f = FolderAccess.start().getDesktopFile("classification-1.png");
		

		BufferedImage img = ImageIO.read(f);

		Classifier<double[], Color> classifier = new ColorClassifier();
		
		train(classifier, img);
		predict(classifier, img);
	}

	private static void predict(Classifier<double[], Color> classifier, BufferedImage img)
	throws Exception
	{
		int Rx = img.getWidth(), Ry = img.getHeight();

		for (int x = 0; x < Rx; x++) 
		for (int y = 0; y < Ry; y++) 
		{
			Color ck = classifier.predict(new double[] {x,y});
			img.setRGB(x, y, ck.getRGB());
		}
		
		File clasificatedFile = FolderAccess.start().getDesktopFile("out-classifier1.png");
		ImageIO.write(img, "png", clasificatedFile);
	}

	private static void train(Classifier<double[], Color> classifier, BufferedImage img) 
	{
		int Rx = img.getWidth(), Ry = img.getHeight();
		
		for(int x=0; x<Rx; x++)
		for(int y=0; y<Ry; y++)
		{
			Color ck = new Color(img.getRGB(x, y) );
			double dk = ColorClassifier.colorDiff(ck, Color.white);
			if( dk > 0.1 ) classifier.train(new double[] {x, y}, ck);
		}
		
		return;
	}


}
