package tasks.july14_spaitial_clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.naebulae.access.FolderAccess;

import tasks.july08_knn_clustering.ClusteringEngine;

public class task2_spatial_clustering_andy {
	
	static ClusteringEngine_Andy<double[]> c = new ClusteringEngine_Andy<double[]>();
	static final Integer numOfCenter = 4;
	
	public static void main(String[] args) 
		throws Exception {
		
			File f = FolderAccess.start().getDesktopFile("auto-divide-4.png");
			
			BufferedImage img = ImageIO.read(f);
			
			int Rx = img.getWidth(), Ry = img.getHeight();
			
			for(int x=0; x<Rx; x++)
			for(int y=0; y<Ry; y++)
			{
				int cxy = img.getRGB(x, y);
				Color ck = new Color(cxy);
				if(!formatColor(ck).equals("#ffffff")) 
					c.add(new double[]{ x, y, x*x, y*y });
			}
			
			c.predictWithKmeans(numOfCenter, 100, ClusterDouble_Andy.class);
			
			
			/**
			 * Test
			 */
			
			double[] z = ClusterDouble_Andy.centroid.get(1);
			
			System.out.println(z[0] + ":" + z[1]);
			
			File outputfile = FolderAccess.start().getDesktopFile("output.png");
			BufferedImage bufferedImage = ImageIO.read(f);
			List<String> res = randomListColor();
			for(int k=0; k<c.size(); k++)
			{
				double[] r = (double[]) c.getPoint(k);
				int x = (int) r[0]; int y = (int) r[1];
				for (int i = 0; i < numOfCenter; i++) {
					if(c.getLabel(k) == i) 
						bufferedImage.setRGB(x, y, Color.decode(res.get(i)).getRGB());
				}
			}
			ImageIO.write(bufferedImage, "png", outputfile);
			
		}
	
	private static List<String> randomListColor() {
		List<String> res = new ArrayList<String>();
		for(int i = 0; i < numOfCenter; i++) res.add(randomColor());
		return res;
	}
	
	private static String randomColor() {
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		return formatColor(randomColor);
	}



	private static String formatColor(Color ck) {
		return String.format("#%02x%02x%02x", ck.getRed(), ck.getGreen(), ck.getBlue());
	}
		
}
