package tasks.capturing_output;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class task1_executing_R 
{

	public static void main(String[] args)
	throws Exception
	{
		Runtime rt = Runtime.getRuntime();
		String[] commands = {"cmd.exe","/c dir /b c:\\opt\\*.*"};
		Process proc = rt.exec(commands);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		System.out.println("Here is the standard output of the command:\n");
		
		while (true) 
		{
			String s = stdInput.readLine();
			if(s == null) break;
		    System.out.println(s);
		}
//		
//		
//		BufferedReader stdError = new BufferedReader(new 
//		     InputStreamReader(proc.getErrorStream()));
//
//
//		// read any errors from the attempted command
//		System.out.println("Here is the standard error of the command (if any):\n");
//		while ((s = stdError.readLine()) != null) {
//		    System.out.println(s);
//		}
	}

}
