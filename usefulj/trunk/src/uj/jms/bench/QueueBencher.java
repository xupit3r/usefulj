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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import uj.jms.bench.helpers.DocumentGenerator;
import uj.jms.bench.helpers.ThreadRegister;
import uj.jms.bench.helpers.ThreadStateManager;

/**
 * Queue based benchmarker.  Each Producer sends to
 * one queue.  Each queue has one and only one consumer
 * associated with it.
 * @author Joseph D'Alessandro
 *
 */
public class QueueBencher implements Bencher
{
	private Producer[] pubs = null;
	private BenchConsumer[] subs = null;
	private int threads;
	private PrintWriter mstWriter = null;
	
	public QueueBencher(int c, int mpc, int s, int sz, long ra, int broker, String of, String bench)
	{
		threads = 0;
		pubs = createPublishers(c,mpc,sz,ra, broker);
		subs = createSubscribers(s, broker, of, bench);
	}
	
	public QueueBencher(int c, int mpc, int s, int sz, long ra, int broker, String of, String bench, String mf)
	{
		threads = 0;
		try{mstWriter = new PrintWriter(new FileWriter(new File(mf), true));}
		catch(IOException io){System.out.println("Could not open MST file.");System.exit(0);}
		pubs = createPublishers(c,mpc,sz,ra, broker);
		subs = createSubscribers(s, broker, of, bench);
	}
	
	/**
	 * Create Publishers that have the desired characteristics.
	 * @param x - the number of publishers to create.
	 * @param mpc - the number of messages to send.
	 * @param sz - the size of the messages to send.
	 * @param ra - the rate at which to send messages.
	 * @return
	 */
	private Producer[] createPublishers(int x, int mpc, int sz, long ra, int broker)
	{
		Producer[] temp = new Producer[x];
		try
		{
			for(int i = 0; i < x; i++)
			{
				temp[i] = new Producer(mpc, ra, "test", Producer.QS, broker);
				temp[i].setSendMessage(DocumentGenerator.getDocument(sz));
			}
		}catch(FileNotFoundException fnf){fnf.printStackTrace();}
		return temp;
	}
	
	/**
	 * Create Subscribers that have user desired characteristics.
	 * This will likely result in a one to one relationship with 
	 * a specific publisher.
	 * @param x - the number of subscribers to create.
	 * @return
	 */
	private BenchConsumer[] createSubscribers(int x, int broker, String of, String bench)
	{
		BenchConsumer[] temp = new QueueConsumer[x];
		for(int i = 0; i < x; i++)
			temp[i] = new QueueConsumer("test", of, broker, bench);
		return temp;
	}
	
	public int numThreads()
	{
		return threads;
	}
	
	public void closeSubs()
	{
		for(int i = 0; i < subs.length; i++)
			subs[i].closeConnection();
	}
	
	public void runIt()
	{
		int cnt = 0;
		ThreadStateManager.goThreads();
		// Run the Producers.
		for(int i = 0; i < pubs.length; i++)
			pubs[i].start();
		while(cnt < 100000){cnt++;}
		while(true){if(ThreadRegister.getThreadCount() == 0)break;}
		long start = System.currentTimeMillis();
		long elapsed = 2000*subs.length;
		while((System.currentTimeMillis() - start) < elapsed){}
		closeSubs();

	}
	
	public void runMST()
	{
		long start = 0L, current = 0L;
		int diff = 0, pmsg = 0, rmsg = 0;
		
		ThreadStateManager.stopRecord();// don't record yet
		ThreadStateManager.goThreads();// tell the threads they can start when they are ready.
		
		// Run the Producers.
		for(int i = 0; i < pubs.length; i++)
			pubs[i].start();
		
		// One Minute Warmup Period
		System.out.println("Warming Up.");
		start = System.currentTimeMillis();
		do{current = System.currentTimeMillis();}
		while((current - start) < 60000L);
		System.out.println("Warm Up Complete.");
		
		// Reset the Received Messages.
		for(int i = 0; i < subs.length; i++)
			((QueueConsumer)subs[i]).resetRecMsgs();
		
		// Five Minute Measurement Period.
		start = System.currentTimeMillis();
		ThreadStateManager.startRecord();// start recording statistics
		do{current = System.currentTimeMillis();}
		while((current - start) < 300000L);
		
		ThreadStateManager.stopThreads(); // Stop the Threads
		ThreadStateManager.stopRecord(); // Stop Recording Statistics
		
		// Five Minute Cool down (to make sure queues are emptied)
		System.out.println("Cooling Down.");
		start = System.currentTimeMillis();
		do{current = System.currentTimeMillis();}
		while((current - start) < 30000L);
		System.out.println("Cool Down Complete.");		
		closeSubs();
		
		// Gather Statistics
		for(int i = 0; i < pubs.length; i++)
			pmsg+=pubs[i].getSentMsgs();
		for(int i = 0; i < subs.length; i++)
			rmsg+=((QueueConsumer)subs[i]).getRecMsgs();
		
		System.out.println("Sent: "+pmsg+" Received: "+rmsg);
		
		// Write Statistics to File
		mstWriter.println(pubs.length+","+pmsg+","+rmsg);
		mstWriter.close();
	}
	
	public void coolIt()// need to ensure that the queues are completely empty!
	{
		long start = 0L, current = 0L;
		start = System.currentTimeMillis();
		do{current = System.currentTimeMillis();} //cool your jets!
		while((current - start) < 300000L);
		ThreadStateManager.stopThreads();
		closeSubs();
	}

}
