package uj.xml;

import java.io.File;
import java.io.IOException;

import uj.string.StringUtils;

public class XMLUtils
{
	public static String stringifyXML(String uri) throws IOException
	{
		return StringUtils.makeString(new File(uri));
	}
	public static String stringifyXML(File f) throws IOException
	{
		return StringUtils.makeString(f);
	}

}
