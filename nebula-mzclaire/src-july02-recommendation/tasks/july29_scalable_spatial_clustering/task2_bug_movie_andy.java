package tasks.july29_scalable_spatial_clustering;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.naebulae.writers.HtmlWriter;

import tasks.july20_spatial_clustering.NearestPair;
import tasks.task3_comparing_movies_DucAnh.ClaireSearchEngineMovie;
import apps.mzclaire.ClaireDataAccess;
import apps.recom.ClaireMovie;
import apps.recom.ClaireSearchEngine;
import apps.recom.UniformRecommemder;
import apps.recom.UniformRecommemderMovie;

public class task2_bug_movie_andy {
	
	static File f = ClaireDataAccess.start().getDesktopFile("out1.html");
	static HtmlWriter out;
	static Integer group = 0;
	
	public static void main(String[] args)
		throws Exception {
		 	out = new HtmlWriter(f);
			List<Object> items = readItems();
			
			hclust(items);
			
			out.close();
			Desktop.getDesktop().open(f);
			
		}
	
	

	private static void hclust(List<Object> items) 
		throws Exception {
			
			int size = items.size();
		
			for(int n=items.size(); n>1; n=items.size())
			{
				NearestPair pk = findNearestPair(items);
				items.remove(pk.left);
				items.remove(pk.right);
				items.add(pk);
				
				out.println( "Group = " + (size - n) );
				
				System.out.println( println(getMembers(pk, new ArrayList<ClaireMovie>())) );
			}
			
		}

	
	private static String println(List<ClaireMovie> members) 
	{
		String res = "";
		out.printStyles(".image { width: 100px; height: 200px; magrin: 10px;}",
				".red { color: red}",
				".box { display:inline-block; border: dashed 1px #aeaeae; vertical-align: top; margin: 5px; padding: 7px; }");
		out.print("<div class='box'>");
		out.br();
		for(ClaireMovie mk: members) {
			out.print(String.format("<img class=image src=%s>", mk.m_poster));
			res += mk.fileName + ", ";
		}
		out.print("</div>");
		out.br();
		return "[ " + res + " ]";
	}

	private static NearestPair findNearestPair(List<Object> items) 
		throws Exception {
			double dmax = Double.MIN_VALUE;
			
			Object lmin = null, rmin = null;
			
			//TODO: items*items or Oh(n*n) or 13000*13000 = 169'000'000 pairs
			//to complete this matrix, we need 169000 seconds (46 hours)
			Set<Object> used = new HashSet<Object>();
			
			for(Object lj: items)
			for(Object rk: items)
			if(lj != rk && !used.contains(rk))
			{
				used.add(lj);
				double djk = distance(lj, rk);
				if(djk > dmax) { dmax = djk; lmin = lj; rmin = rk; }
			}
				
			
			return new NearestPair(dmax, lmin, rmin);
		}
	
	
	static ClaireSearchEngine<ClaireMovie> eng = new ClaireSearchEngineMovie();
	private static double distance(Object ql, Object qr) 
		throws Exception {
			List<ClaireMovie> left = getMembers(ql, new ArrayList<ClaireMovie>());
			List<ClaireMovie> right = getMembers(qr, new ArrayList<ClaireMovie>());
			
			double dmax = Double.MIN_VALUE;
			
			//TODO: in the worse case, we have 6500 members of the left and 65000 members on the right
			//oh(n*n*(n/2)*(n/2)) = oh(n^4/4) = oh(n^4)
			for(ClaireMovie lj: left)
			for(ClaireMovie rk: right)
			{
				double s12 = eng.jaccardIndex(lj, rk);
				if(s12 > dmax) dmax = s12;
			}
			
			return dmax;
		}
	
	private static List<ClaireMovie> getMembers(Object lj, List<ClaireMovie> res) 
	{
		if(lj instanceof ClaireMovie) 
		{
			res.add((ClaireMovie)lj);
		}
		
		if(lj instanceof NearestPair) 
		{
			NearestPair t = (NearestPair)lj;
			getMembers(t.left, res);
			getMembers(t.right, res);
		}
		 
		return res;
	}
	

	private static List<Object> readItems() 
		throws Exception {
			UniformRecommemder<ClaireMovie> uk = new UniformRecommemderMovie();
			uk.setStockFolder( ClaireDataAccess.start().getStockMovieFolder() );
			uk.setNanoCoin();
			
			List<Object> res = new ArrayList<Object>();
			uk.pickItems(50).stream().forEach(x -> res.add(x));
			
			return res;
		}
	
	
}
