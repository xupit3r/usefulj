/**
 * Copyright 2010 Joseph D'Alessandro
 * This program is distributed under the GNU Public License.
 * 
 *  This file is part of the UsefulJ library.
 *
 *   UsefulJ is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   UsefulJ is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with UsefulJ.  If not, see <http://www.gnu.org/licenses/>
 */
package uj.jms.bench.send;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uj.jms.bench.helpers.BenchPropertyManager;


/**
 * Class to handle the most basic sending case.
 * Use the JNDI interface to create a generic ConnectionFactory.
 * @author Joseph D'Alessandro
 *
 */
public class QueueSender implements BenchSender
{
	// Setting Strings
    private String destinationName = null;
    
    // JMS Objects
    private Session session;    
    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Destination destination = null;
    private MessageProducer producer = null;
    private TextMessage message = null;
    
    // JNDI Objects
    private Context jndiContext = null;
    
    // Property Objects
    private Properties props = new Properties();
    
    /**
     * Base Constructor. Accepts a queue name.
     */
    public QueueSender(String dn, int broker)
    {
    	destinationName = dn;
		loadProps(broker);
		connect();
    }
    
    /**
     * Connect to a queue.
     * @return true if connection successfully established, false otherwise.
     */
    public void connect()
	{
    	try
    	{
    		jndiContext = new InitialContext(props); // Generic JNDI Context
            connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
    	}
		catch(NamingException nex) {nex.printStackTrace();} //JNDI Naming Problem
		catch(JMSException jex){jex.printStackTrace();}// JMS Connection Problem
	}
    
    /**
     * Close a queue connection.
     */
    public void closeConnection()
    {
    	if(connection != null)
    	{
    		try{connection.close();}
    		catch(JMSException jme){jme.printStackTrace();}
    	}
    }
    
    /**
     * Send this classes text message to its destination.
     * @return true if successfully sent, false otherwise.
     */
    public boolean send()
	{
		boolean flag = false;
		try
		{
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(destinationName);
			producer = session.createProducer(destination);
			message = session.createTextMessage();
			producer.send(message);
			flag = true;
			producer.close();
			session.close();
		} catch (JMSException jex)
		{
			jex.printStackTrace();
		}
		return flag;
	}
    
    /**
     * Send this classes text message to its destination.
     * @return true if successfully sent, false otherwise.
     */
    public boolean send(String msg)
	{
		boolean flag = false;
		try
		{			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(destinationName);
			producer = session.createProducer(destination);
			message = session.createTextMessage();
			message.setText(msg);
			producer.send(message);
			flag = true;
			producer.close();
			session.close();
		} catch (JMSException jex)
		{
			jex.printStackTrace();
		}
		return flag;
	}
    
    /**
     * Set the message to be associated with this class.
     * @param s - the string representation of the message to be sent.
     */
    public void setMsgText(String s) throws JMSException
    {
    	try{message.setText(s);}
    	catch(JMSException jex){jex.printStackTrace();}
    }
    
    public void setMsgTime(long time)throws JMSException
    {
    	message.setLongProperty("sendTime", time);
    }
    
	/**
	 * Load Connection Properties from a properties file.
	 */
	private void loadProps(int broker)
	{
		props = BenchPropertyManager.getJNDIProps(broker);

	}

}
