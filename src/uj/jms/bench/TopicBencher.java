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

import java.io.FileNotFoundException;

import uj.jms.bench.helpers.DocumentGenerator;
import uj.jms.bench.helpers.ThreadRegister;
import uj.jms.bench.helpers.ThreadStateManager;
import uj.log.UJ;

/**
 * Topic Based Benchmarker.  Create X Producers, and
 * Y Consumers.  Each Producer publishes to one topic.
 * Each topic can have one or many Consumers.
 * @author Joseph D'Alessandro
 *
 */
public class TopicBencher implements Bencher
{
	private Producer[] pubs = null;
	private BenchConsumer[] subs = null;
	private int threads;
	
	public TopicBencher(int c, int mpc, int s, int sz, long ra, int broker, String of, String bench)
	{
		threads = 0;
		pubs = createPublishers(c,mpc,sz,ra, broker);
		subs = createSubscribers(s, c, broker, of, bench);
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
				temp[i] = new Producer(mpc, i, ra, "test", Producer.TS, broker);
				temp[i].setSendMessage(DocumentGenerator.getDocument(sz));
			}
		}catch(FileNotFoundException fnf){fnf.printStackTrace();}
		return temp;
	}
	
	/**
	 * Create Subscribers that have user desired characteristics.
	 * This will likely result in a one to one relationship with 
	 * a specific publisher.  In the case of the topic benchmark
	 * this is X subscribers for each Y publisher.
	 * @param x - the number of subscribers to create.
	 * @return
	 */
	private BenchConsumer[] createSubscribers(int x, int y, int broker, String of, String bench)
	{
		BenchConsumer[] temp = new TopicConsumer[x*y];
		int index = 0;
		for(int j = 0; j < y; j++)
		{
			for(int i = 0; i < x; i++)
				temp[index+i] = new TopicConsumer("test"+j, of, broker, bench);
			index+=x;
		}
		return temp;
	}
	
	public int numThreads()
	{
		return threads;
	}
	
	public void runIt()
	{
		long start = 0L, current = 0L;
		int diff = 0, pmsg = 0, rmsg = 0;
		
		ThreadStateManager.stopRecord();// don't record yet
		ThreadStateManager.goThreads();// tell the threads they can start when they are ready.
		
		// Run those Publishers.
		for(int i = 0; i < pubs.length; i++)
			pubs[i].start();
		
		UJ.log.out("Warming Up.");
		start = System.currentTimeMillis();
		do{current=System.currentTimeMillis();}
		while((current-start) < 90000); //warm cup of java?
		UJ.log.out("Warm Up Complete.");
		
		UJ.log.out("RECORDING.");
		ThreadStateManager.startRecord();// start recording statistics		
		while(true){if(ThreadRegister.getThreadCount() == 0)break;}
		
		UJ.log.out("Cooling Down.");
		start = System.currentTimeMillis();
		do{current=System.currentTimeMillis();}
		while((current-start) < 90000); //cool your jets!
		UJ.log.out("Cool Down Complete.");
		
		ThreadStateManager.stopThreads(); // Stop the Threads
		ThreadStateManager.stopRecord(); // Stop Recording Statistics
		
		for(int i = 0; i < subs.length; i++)
			subs[i].closeConnection();
		
	}

}

