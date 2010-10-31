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
package uj.jms.bench.main;

import uj.jms.bench.Bencher;
import uj.jms.bench.QueueBencher;
import uj.jms.bench.TopicBencher;
import uj.jms.bench.helpers.BenchPropertyManager;
import uj.jms.bench.helpers.DocumentGenerator;

public class BencherMain
{
	public static void main(String args[])
	{

		int amq = BenchPropertyManager.ACTIVEMQ;
		
		int topItr = args.length > 0 && args[0] != null ? Integer.parseInt(args[0]) : 100;
		int mstItr = args.length > 1 && args[1] != null ? Integer.parseInt(args[1]) : 1;
		
		System.out.println("Topic Benchmark Iterations: "+topItr);
		System.out.println("Max Sustainable Throughput Iterations: "+mstItr);

		// Topic Based Benchmarks
		System.out.println("*****************************************************");
		System.out.println("***************** Topic Benchmark *******************");
		System.out.println("*****************************************************");
		runTopicBench(amq, "AMQ",topItr);

		// Queue Based Benchmarks
		System.out.println("*****************************************************");
		System.out.println("****************** MST Benchmark ********************");
		System.out.println("*****************************************************");
		findMST(amq, "AMQ",mstItr);

	}
	
	public static void oneTopRun(int broker, String bs, int iterations)
	{
		Bencher bencher = null;
		bencher = new TopicBencher(1, 1000, 1, DocumentGenerator.small, 0L,
				broker, System.getProperty("user.home")+"/Desktop/testTopSmall", "B1" + bs);
		bencher.runIt();
		
		bencher = new TopicBencher(1, 1000, 1, DocumentGenerator.small, 0L,
				broker,System.getProperty("user.home")+"/Desktop/testTopSmall", "B1" + bs);
		bencher.runIt();
	}

	public static void runTopicBench(int broker, String bs, int iterations)
	{

		for (int i = 0; i < iterations; i++)
		{
			// Small Message Sizes
			Bencher bencher = null;
			bencher = new TopicBencher(1, 1000, 1, DocumentGenerator.small, 0L,
					broker, System.getProperty("user.home")+"/Desktop/testTopSmall", "B1" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 5, DocumentGenerator.small, 0L,
					broker, System.getProperty("user.home")+"/Desktop/testTopSmall", "B2" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 10, DocumentGenerator.small,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopSmall", "B3"+ bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 20, DocumentGenerator.small,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopSmall", "B4"+ bs);
			bencher.runIt();
			bencher = null;

			// Medium Message Sizes
			bencher = new TopicBencher(1, 1000, 1, DocumentGenerator.medium,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopMed", "B1" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 5, DocumentGenerator.medium,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopMed", "B2" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 10, DocumentGenerator.medium,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopMed", "B3" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 20, DocumentGenerator.medium,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopMed", "B4" + bs);
			bencher.runIt();
			bencher = null;

			// Large Message Sizes
			bencher = new TopicBencher(1, 1000, 1, DocumentGenerator.large, 0L,
					broker, System.getProperty("user.home")+"/Desktop/testTopLarge", "B1" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 5, DocumentGenerator.large, 0L,
					broker, System.getProperty("user.home")+"/Desktop/testTopLarge", "B2" + bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 10, DocumentGenerator.large,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopLarge", "B3"
							+ bs);
			bencher.runIt();
			bencher = new TopicBencher(1, 1000, 20, DocumentGenerator.large,
					0L, broker, System.getProperty("user.home")+"/Desktop/testTopLarge", "B4"
							+ bs);
			bencher.runIt();
			bencher = null;
		}
	}

	/**
	 * Will find MST, in the process will capture latency as well.
	 * 
	 * @param broker
	 * @param bs
	 * @param iterations
	 */
	public static void findMST(int broker, String bs, int iterations)
	{
		QueueBencher bencher = null;

		for (int i = 1; i <= 4; i++)
		{
			// Small Message Sizes {MST, Latency}
			for (int j = 0; j < iterations; j++)
			{
				bencher = new QueueBencher(i, 1000000000, i,
						DocumentGenerator.small, 0L, broker,
						System.getProperty("user.home")+"/Desktop/testLatencySmall"+bs, "B"
								+ (5 + i)+bs, System.getProperty("user.home")+"/Desktop/mstSmall"
								+ bs);
				bencher.runMST();
			}

			// Medium Message Sizes {MST, Latency}
			for (int j = 0; j < iterations; j++)
			{
				bencher = new QueueBencher(i, 1000000000, i,
						DocumentGenerator.medium, 0L, broker,
						System.getProperty("user.home")+"/Desktop/testLatencyMedium"+bs, "B"
								+ (5 + i), System.getProperty("user.home")+"/Desktop/mstMedium"
								+ bs);
				bencher.runMST();
			}

			// Large Message Sizes {MST, Latency}
			for (int j = 0; j < iterations; j++)
			{
				bencher = new QueueBencher(i, 1000000000, i,
						DocumentGenerator.large, 0L, broker,
						System.getProperty("user.home")+"/Desktop/testLatencyLarge"+bs, "B"
								+ (5 + i), System.getProperty("user.home")+"/Desktop/mstLarge"
								+ bs);
				bencher.runMST();
			}
		}

	}

}
