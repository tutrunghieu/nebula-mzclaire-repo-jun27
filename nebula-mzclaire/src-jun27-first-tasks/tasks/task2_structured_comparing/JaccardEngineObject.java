package tasks.task2_structured_comparing;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import apps.mzclaire.jaccard.JaccardEngine;
import apps.mzclaire.jaccard.JaccardEngineBow;

public class JaccardEngineObject 
{
	public boolean bin2bin = true;
	
	public double jaccardIndex(Object m1, Object m2) 
	throws Exception
	{
		if(bin2bin) return jaccardIndexBin(m1, m2);
		else return jaccardIndexCross(m1, m2);
	}

	private double jaccardIndexBin(Object m1, Object m2) 
	throws Exception
	{
		Set<Field> keys = new LinkedHashSet<Field>();
		
		for(Field x: m1.getClass().getFields())
			if( Modifier.isPublic(x.getModifiers()) ) keys.add(x);
		
		for(Field x: m2.getClass().getFields())
			if( Modifier.isPublic(x.getModifiers()) ) keys.add(x);
		
		double s = 0;
		
		for(Field k: keys)
		{
			double sk = jaccardIndexForField(k, (k.get(m1) != null) ? k.get(m1) : "", (k.get(m2) != null) ? k.get(m2) : "");
			//System.out.println(k + " -- " + sk);
			s += sk;
		}
		
		return s / keys.size();
	}
	
	private double jaccardIndexForField(Field k, Object a, Object b)
	throws Exception
	{
		JaccardTag hk = k.getAnnotation(JaccardTag.class);
		
		if(hk != null) 
		{
			JaccardHelper eng = hk.value().newInstance();
			return eng.jaccardIndex(a, b);
		}
		
		else 
		{
			JaccardEngine inner = new JaccardEngineBow();
			if (a == null) a = new Object();
			if (b == null) b = new Object();
			return inner.jaccardIndex(a.toString() + "", b.toString() + "");
		}
	}

	private double jaccardIndexCross(Object m1, Object m2) 
	{
		// TODO Auto-generated method stub
		return 0;
	}



}
