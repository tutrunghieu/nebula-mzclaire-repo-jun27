package org.naebulae.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils 
{
	public static Date addYears(int yy) 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, yy); 
		return c.getTime();
	}

	public static Date addYears(Date t, int yy) 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(t);
		c.add(Calendar.YEAR, yy); 
		return c.getTime();
	}
	
	public static Date parseYMD(String dt) 
	throws Exception
	{		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.parse(dt);
	}
	
	public static String toStringGMT(Date d)
	{
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return fmt.format(d);
	}

	public static Date fromStringGmt(String gmt) 
	throws Exception
	{
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		System.out.println(gmt);
		return fmt.parse(gmt);		
	}

	public static String toStringDMY(Date d) 
	{
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		return fmt.format(d);
	}
	
	public static Date addWithOptions(Date d, String opt) 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		int s = 1;
		if(opt.startsWith("+")) opt = opt.substring(1); 
		else if(opt.startsWith(";")) { opt = opt.substring(1); s = +1; }
		else if(opt.startsWith("-")) { opt = opt.substring(1); s = -1; }
		
		int n = opt.length()-1;
		int dmy = opt.charAt(n);
		s *= Integer.parseInt(opt.substring(0, n));
		
		if(dmy == 'd') c.add(Calendar.DATE, s);
		else if(dmy == 'm') c.add(Calendar.MONTH, s);
		else if(dmy == 'y') c.add(Calendar.YEAR, s);
		
		return c.getTime();
	}
	
	public static String toStringDMY(String d) 
	throws Exception
	{
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		return fmt.format(fromStringGmt(d));
	}

	public static boolean smaller(Date a, Date b) 
	{
		return  a.compareTo(b) <= 0;
	}

	public static Date fromGmtOrLong(String time) 
	throws Exception
	{
		try {
			Long l = Long.parseLong(time);
			return new Date(l);
		}
		catch(Exception xp) {}
		
		return fromStringGmt(time);
	}

	public static String currentTimeMillis() 
	{
		return "MIL:" + System.currentTimeMillis();
	}

	public static String currentTimeYMDHMS(long l)
	{
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
        return df2.format(new Date(l) );
	}
		
	public static String currentTimeYMDHMS()
	{
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
        return df2.format(new Date(System.currentTimeMillis() ) );
	}
		
}
