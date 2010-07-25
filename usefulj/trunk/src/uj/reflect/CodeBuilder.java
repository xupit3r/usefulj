package uj.reflect;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class CodeBuilder 
{
	private InputStream xml = null;
	public CodeBuilder(String x)
	{
		xml = new ByteArrayInputStream(x.getBytes()); 
	}
	
	public void generateJavaCode() throws FileNotFoundException, TransformerException
	{
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer(new StreamSource(new FileInputStream("src/uj/reflect/res/xslt/xml2java.xsl")));	
		File codeOutput = new File("JavaCode.txt");
		FileOutputStream os = new FileOutputStream(codeOutput);
		StreamResult result = new StreamResult(os);
		transformer.transform(new StreamSource(xml), result);

	}
	
	

}
