package uj.string;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StringUtils 
{
	public static String makeString(File f) throws IOException
	{
	    byte[] buffer = new byte[(int) f.length()];
	    BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(f));
	    bufIn.read(buffer);
	    bufIn.close();
	    return new String(buffer);
	}
	
	public static String[] deComma(String s)
	{
		String[] arr = null;
		if(!s.contains(","))
		{
			arr = new String[1];
			arr[0] = s;
		}
		else
			arr = s.split(",");
		return arr;
	}

}
