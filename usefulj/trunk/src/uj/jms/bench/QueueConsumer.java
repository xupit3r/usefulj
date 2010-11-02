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
package uj.jms.bench;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uj.jms.bench.helpers.BenchPropertyManager;
import uj.jms.bench.helpers.ThreadStateManager;
import uj.jms.bench.io.SynchronizedWriter;
import uj.log.UJ;

public class QueueConsumer implements BenchConsumer
{
	private String theQ = null;
	private String theBench = null;

	// JMS Objects
	private Session session;
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;

	// JNDI Objects
	private Context jndiContext = null;

	// Property Objects
	private Properties props = new Properties();
	
	private int recMsgs = 0;
	
	private File logFile = null;
	
	public QueueConsumer(String q, String l, int broker, String bench)
	{
		theQ = q;
		theBench = bench;
		logFile = new File(l);
		loadProps(broker);
		connect();
	}

	public void onMessage(Message msg)
	{
		long ct = System.currentTimeMillis();
		long st = 0L;
		try
		{
			st = msg.getLongProperty("sendTime");
			if(!ThreadStateManager.stop && ThreadStateManager.record)recMsgs++;
		}
		catch(JMSException jex){UJ.log.out("Could not get send time!");}
		try{if(ThreadStateManager.record)SynchronizedWriter.writeResult(logFile, theBench+","+String.valueOf((ct-st)));}
		catch(IOException ioe){UJ.log.out("Could NOT write to file!");}
	}

	/**
	 * Connect to a queue.
	 * 
	 * @return true if connection successfully established, false otherwise.
	 */
	public void connect()
	{
		try
		{
			jndiContext = new InitialContext(props); // Generic JNDI Context
			connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(theQ);
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
			connection.start();
		} catch (NamingException nex)
		{
			UJ.log.out("Naming Exception: "+theQ);
			//nex.printStackTrace();
		} // JNDI Naming Problem
		catch (JMSException jex)
		{
			jex.printStackTrace();
		}// JMS Connection Problem
	}

	/**
	 * Close a queue connection.
	 */
	public void closeConnection()
	{
		if (connection != null)
		{
			try
			{
				connection.stop();
				connection.close();
			} catch (JMSException jme)
			{
				jme.printStackTrace();
			}
		}
	}
	
	public int getRecMsgs()
	{
		return recMsgs;
	}
	
	public void resetRecMsgs()
	{
		recMsgs = 0;
	}
	
	/**
	 * Load Connection Properties from a properties file.
	 */
	private void loadProps(int broker)
	{
		props = BenchPropertyManager.getJNDIProps(broker);

	}
}
