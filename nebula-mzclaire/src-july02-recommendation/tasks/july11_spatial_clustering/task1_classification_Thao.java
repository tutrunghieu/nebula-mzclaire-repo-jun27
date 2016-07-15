package tasks.july11_spatial_clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;
import org.naebulae.util.Joiner;

import tasks.comparing_songs.Classifier;
import tasks.comparing_songs.MyClassifier;

public class task1_classification_Thao {

	public static void main(String[] args) 
			throws Exception
	{
		File f = FolderAccess.start().getDesktopFile("classification-1.png");
		
		BufferedImage img = ImageIO.read(f);

		int Rx = img.getWidth(), Ry = img.getHeight();

		Classifier<double[], String> classifier = new MyClassifier();

		//Training
		{
			for(int x=0; x<Rx; x++)
				for(int y=0; y<Ry; y++)
				{
					int cxy = img.getRGB(x, y);
					Color ck = new Color(cxy);
					String colorString = Joiner.start(":").join(ck.getRed(), ck.getGreen(), ck.getBlue());
					if (!colorString.equals("255:255:255")) { //Check color string is different from white color
						classifier.train(new double[] {x, y}, colorString);
					}
				}
		}

		//Predict
		{
			for (int x = 0; x < Rx; x++) 
				for (int y = 0; y < Ry; y++) {
					String colorString = classifier.predict(new double[] {x,y});
					String[] colorParts = colorString.split(":");
					int red = Integer.parseInt(colorParts[0]);
					int green = Integer.parseInt(colorParts[1]);
					int blue = Integer.parseInt(colorParts[2]);
					
					Color ck = new Color(red, green, blue);
					img.setRGB(x, y, ck.getRGB());
				}
			File clasificatedFile = FolderAccess.start().getDesktopFile("clasificatedFileImage.png");
			ImageIO.write(img, "png", clasificatedFile);
		}
		return;
	}
}
