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

import javax.jms.JMSException;

import uj.jms.bench.helpers.ThreadRegister;
import uj.jms.bench.helpers.ThreadStateManager;
import uj.jms.bench.send.BenchSender;
import uj.jms.bench.send.QueueSender;
import uj.jms.bench.send.TopicSender;

/**
 * The Producer of messages in the simulation.
 * @author Joseph D'Alessandro
 *
 */
public class Producer extends Thread
{
	public static final int QS = 0;
	public static final int TS = 1;
	
	private int numMsgs = 0;
	@SuppressWarnings("unused")
	private int tid = 0;
	private long wait = 1000L;
	
	private int sentMsgs = 0; //how many messages has this producer sent for some time interval.
	
	private BenchSender sender = null;
	
	public Producer(int m, int id, long w, String dest, int sd, int broker)
	{
		numMsgs = m;
		tid = id;
		wait = w; 
		if(sd == QS)
			sender = new QueueSender(dest+id, broker);
		else
			sender = new TopicSender(dest+id, broker);
	}
	
	/*
	 * FOR QUEUE TESTING
	 */
	public Producer(int m, long w, String dest, int sd, int broker)
	{
		numMsgs = m;
		wait = w; 
		if(sd == QS)
			sender = new QueueSender(dest, broker);
		else
			sender = new TopicSender(dest, broker);
	}
	
	/**
	 * Set the text of the message to be sent by this publisher.
	 * @param st - string representation of the message.
	 */
	public void setSendMessage(String st)
	{
		try{sender.setMsgText(st);}
		catch(JMSException jex){jex.printStackTrace();}
	}
	
	/**
	 * Overridden method representing the behavior of this publisher.
	 */
	public void run()
	{
		ThreadRegister.registerThread();
		for(int i = 0; i < numMsgs && !ThreadStateManager.stop; i++)
		{
			try
			{
					sender.setMsgTime(System.currentTimeMillis());
					if(!pubSend())System.out.println("[Producer] Problem Sending!");
					if(ThreadStateManager.record) sentMsgs++;
					else numMsgs++; //increase the number of messages to send until we are ready to record.
					sleep(wait);
			}
			catch(InterruptedException ie)
			{	
				System.out.println("[Producer] Interrupted, Stopping Myself.");
				Thread.currentThread().interrupt();
			}
			catch(JMSException jex){System.out.println("[Producer] Could not set message time property!");}
		}
		closeBrokerConnection();
		ThreadRegister.unregisterThread();
	}
	
	/**
	 * Send the message.
	 * @return
	 */
	private boolean pubSend()
	{
		return sender.send();
	}
	
	/**
	 * Close the connection to the broker.
	 */
	private void closeBrokerConnection()
	{
		sender.closeConnection();
	}
	
	public int getSentMsgs()
	{
		return sentMsgs;
	}
	
	@SuppressWarnings("unused")
	private void resetSentMsgs()
	{
		sentMsgs=0;
	}

}
