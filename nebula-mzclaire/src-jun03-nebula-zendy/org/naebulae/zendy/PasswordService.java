package org.naebulae.zendy;

import org.naebulae.util.String2;

public class PasswordService {

	public static boolean validPassword(String pwd, String noise, String hash) 
	{
		 String h = String2.identifier256(pwd + "|||" + noise);
		 return h.equals(hash);
	}
	
	public static String hidePassword(String pwd, String noise) 
	{
		 return String2.identifier256(pwd + "|||" + noise);
	}

}
