package uj.xml;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLTransformer
{
	/**
	 * Apply XSLT transform using Java's internal XSLT processor.
	 */
	public static String applyXSLT(String xslt, String xml) throws TransformerException, TransformerConfigurationException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		String transDoc = "";
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(new StringReader(xslt)));
		transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(byteOut));
		transDoc = byteOut.toString();		
		return transDoc;
	}
}
