package uj.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import uj.string.StringUtils;


public class FileIterator implements Iterator<File>
{
	
	private File dir = null;
	private File[] files = null;
	private int idx = 0;
	
	public FileIterator(){}
	
	public FileIterator(String d)
	{
		dir=new File(d);
		files = dir.listFiles();
		Arrays.sort(files);
		idx = 0;
	}	
	
	public FileIterator(String d, FileFilter ff)
	{
		dir=new File(d);
		files = dir.listFiles(ff);
		Arrays.sort(files);
		idx = 0;
	}
	
	public boolean setDir(String d)
	{
		dir = new File(d);
		dir = dir.isDirectory() ? dir : null;
		return dir != null;
	}
	
	public boolean hasNext()
	{
		return idx < files.length;
	}

	public File next()
	{
		return files[idx++];
	}
	
	public String getNextString() throws IOException
	{
		File f = files[idx++];
		return fToS(f);
	}
	
	public long getSizeKB()
	{
		return getBytes(files[idx-1])/1000;
	}
	
	public long getSizeKB(File f)
	{
		return getBytes(f)/1000;
	}
	
	public long getTextSize()
	{
		return getBytes(files[idx-1]);
	}
	
	public long getTextSize(File f)
	{
		return getBytes(f);
	}
	
	public long getFileSize()
	{
		return Math.round(files[idx-1].length());
	}
	
	private long getBytes(File f)
	{
		long result = 0L;
		try
		{
			String fs = fToS(f);
			result = fs.length()*(Character.SIZE / 8);
		}catch(IOException ioe){result = -1L;};
		return result;
	}
	
	private String fToS(File f) throws IOException
	{
		return StringUtils.makeString(f);
	}
	

	
	public int getNumberOfFiles()
	{
		return files.length;
	}

	
	/**
	 * I know, I know, this is a terrible way to handle this, but
	 * when the hell am I even going to use this method?
	 */
	public void remove()
	{
		files[idx] = null;
		
	}
	
	public File current()
	{
		return files[(idx - 1) < 0 ? 0 : idx-1];
	}

}
