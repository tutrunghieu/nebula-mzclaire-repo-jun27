package tasks.task3_comparing_movies_DucAnh;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassUtils {

	public static void printDeclared(Object src) 
	throws Exception
	{
		
		for(Field fk: src.getClass().getDeclaredFields())
		if( Modifier.isPublic(fk.getModifiers()) )	
		{
			System.out.println(fk.getName() + " = " + fk.get(src) );
		}
		
		return;
	}

}
