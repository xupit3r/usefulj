package uj.test;


import java.io.File;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import uj.reflect.CodeBuilder;
import uj.string.StringUtils;

public class CodeBuilderTest 
{	
	public static void main(String args[]) throws IOException, TransformerException
	{
		String xml = StringUtils.makeString(new File("src/uj/test/res/JavaTestFile.xml"));
		CodeBuilder builder = new CodeBuilder(xml);
		builder.generateJavaCode();
		
		System.out.println(StringUtils.makeString(new File("JavaCode.txt")));
	}
}
