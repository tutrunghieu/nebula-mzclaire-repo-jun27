package apps.mzclaire;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class String2 
{
	public static String box(String nk) 
	{
		return "[[" + nk + "]]";
	}

	public static void boxAndShow(String nk) 
	{
		System.out.println(box(nk));
	}
	
	
	public static String toHexString(byte[] t)
	{
		final StringBuilder builder = new StringBuilder();
		for(byte b : t) builder.append(String.format("%02x", b) );
		return builder.toString();
	}

	public static String join(String cm, double... v) 
	{
		String res = "";
		for(int k=0; k<v.length; k++) 
		{
			res += (k==0 ? "" : cm) + fmt4(v[k]);
		}
		
		return res;
	}
	
	public static String join(String cm, Object... v) 
	{
		String res = "";
		for(int k=0; k<v.length; k++) 
		{
			res += (k==0 ? "" : cm) + v[k];
		}
		
		return res;
	}
	
	public static String joinList(String cm, List<String> v) 
	{
		String res = "";
		for(int k=0; k<v.size(); k++) 
		{
			res += (k==0 ? "" : cm) + v.get(k);
		}
		
		return res;
	}	
	public static String fmt4(double d) 
	{
		return d + "";
	}

	public static boolean notNullNorBlank(String sk) 
	{
		return sk != null && sk.trim().length() > 0;
	}

	public static String defval(String v, String dval)
	{
		return v==null ? dval : v;
	}

	public static String beforeLast(char dot, String name) 
	{
		int pk = name.lastIndexOf(dot);
		return pk<0 ? name : name.substring(0, pk);
	}

	public static String afterLast(char dot, String name) 
	{
		int pk = name.lastIndexOf(dot);
		return pk<0 ? "" : name.substring(pk);
	}
	
	public static String afterLast1(char dot, String name) 
	{
		int pk = name.lastIndexOf(dot);
		return pk<0 ? "" : name.substring(pk+1);
	}
	
	
	private static byte[] sha256(String bytes) throws Exception 
	{
		return sha256(bytes.getBytes());
	}
	
	private static byte[] sha256(byte[] bytes) throws Exception 
	{
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
		 return md.digest(bytes);
	}	
	
	public static String identifier256(String key) 
	{
		try { return toHexString( sha256(key) ); }
		catch(Exception xp) { return null; }
	}
	
	public static String identifier5(String key) 
	{
		String res = identifier256(key);
		return res.substring(0, 5);
	}
	

	public static String identifierNanoHash(Object obj) 
	{
		String key = obj.hashCode() + "@" + System.nanoTime();
		try { return toHexString( sha256(key) ); }
		catch(Exception xp) { return null; }
	}
	
	public static String urlEncode(String name) 
	{
		try { return URLEncoder.encode(name, "UTF-8"); }
		catch(Exception xp) { return name; }
	}

	public static double filterDigitsToDouble(String s) 
	{
		s = s.replaceAll("\\D+","");
		return Double.parseDouble(s);
	}

	public static List<String> splitAndRemove(String s, String cm)
	{
		List<String> res = new ArrayList<String>();
		
		for(String sk: s.split(cm))
		{
			String tk = sk.trim();
			if(!tk.isEmpty()) res.add(tk);
		}
		
		return res;
	}

	public static boolean notNullNorNull(String s) 
	{
		return s != null && !s.equals("null");
	}

	
	public static boolean notNullAndEquals(String a, String b) 
	{
		return a != null && a.equals(b);
	}

	
	public static String dotdot(String str, int len) 
	{
		if(str == null) return str;
		if(str.length() > len) str = str.substring(0, len) + "...";
		return str;
	}

	public static List<String> sort(List<String> items) 
	{
		return items.stream().sorted().collect( Collectors.toList() );
	}

}
