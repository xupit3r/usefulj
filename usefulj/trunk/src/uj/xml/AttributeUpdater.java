package uj.xml;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class AttributeUpdater
{
	public static String updateRootAttribute(String xml, String ru, String pu) throws XMLStreamException
	{
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inputFactory.createXMLStreamReader(new ByteArrayInputStream(xml.getBytes()));
		StringWriter result = new StringWriter();
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = outputFactory.createXMLStreamWriter(result);
		
		while(reader.hasNext())
		{
			if(reader.getEventType() == XMLStreamReader.START_ELEMENT)
	    	{
	    		writer.writeStartElement(reader.getName().toString());
	    		for(int i = 0; i < reader.getAttributeCount(); i++)
	    		{
	    			String attName = reader.getAttributeLocalName(i);
			        if(attName.equalsIgnoreCase("CCM_PROVIDING_USER"))
			        	writer.writeAttribute(attName, pu);
			        else if(attName.equalsIgnoreCase("CCM_RECEIVING_USER"))
			        	writer.writeAttribute(attName, ru);
			        else
			        	writer.writeAttribute(attName, reader.getAttributeValue(i));
	    		}
	    	}
			else if(reader.getEventType() == XMLStreamReader.END_ELEMENT)
			{
				writer.writeEndElement();
			}
			else if(reader.getEventType() == XMLStreamReader.CHARACTERS)
			{
				String carr = reader.getText();
				writer.writeCharacters(carr);
			}
	        reader.next();
		}
        writer.close();
        reader.close();
        String done = result.toString();        	
        return done;        
	}

}
