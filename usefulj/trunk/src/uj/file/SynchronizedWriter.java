package uj.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SynchronizedWriter
{
	public static synchronized void writeResult(File f, String line) throws IOException
	{
		PrintWriter w = new PrintWriter(new FileWriter(f, true));
		w.println(line);
		w.close();
	}

}
