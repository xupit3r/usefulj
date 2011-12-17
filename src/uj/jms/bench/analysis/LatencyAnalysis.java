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
package uj.jms.bench.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Class to calculate latency statistics from benchmark runs.
 * @author Joseph D'Alessandro
 *
 */
public class LatencyAnalysis
{
	public static void analyzeFile(File f, int fnum) throws IOException
	{
		Scanner in = new Scanner(f);
		PrintWriter myOut = new PrintWriter(new FileWriter(new File(System.getProperty("user.home")+"/Desktop/latency"+fnum)));
		HashMap<String, ArrayList<Integer>> theMap = new HashMap<String, ArrayList<Integer>>();
		
		while(in.hasNext())
		{
			String[] lineTokens = in.nextLine().split(",");
			String blab = lineTokens[0];
			Integer bval = Integer.valueOf(lineTokens[1]);
			if(theMap.containsKey(blab))theMap.get(blab).add(bval);
			else
			{
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(bval);
				theMap.put(blab,temp);
			}
		}
		
		Iterator<Map.Entry<String,ArrayList<Integer>>> entryItr = theMap.entrySet().iterator();
		while(entryItr.hasNext())
		{
			Map.Entry<String, ArrayList<Integer>> entry = entryItr.next();
			String bl = (String) entry.getKey();
			ArrayList<Integer> vl = (ArrayList<Integer>) entry.getValue();
			int mean = MeanAnalyzer.getMean(vl);
			int median = MedianAnalyzer.getMedian(vl);
			int mode = ModeAnalyzer.getMode(vl);
			myOut.println(bl+","+mean+","+median+","+mode);
		}
		myOut.close();
	}
	
	public static void splitFile(File f) throws IOException
	{
		Scanner in = new Scanner(f);
		HashMap<String, ArrayList<Integer>> theMap = new HashMap<String, ArrayList<Integer>>();
		
		while(in.hasNext())
		{
			String[] lineTokens = in.nextLine().split(",");
			String blab = lineTokens[0];
			Integer bval = Integer.valueOf(lineTokens[1]);
			if(theMap.containsKey(blab))theMap.get(blab).add(bval);
			else
			{
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(bval);
				theMap.put(blab,temp);
			}
		}
		
		Iterator<Map.Entry<String,ArrayList<Integer>>> entryItr = theMap.entrySet().iterator();
		while(entryItr.hasNext())
		{
			PrintWriter myOut = null;
			Map.Entry<String, ArrayList<Integer>> entry = entryItr.next();
			String key = (String) entry.getKey(); 
			ArrayList<Integer> vl = (ArrayList<Integer>) entry.getValue();
			Collections.sort(vl);
			myOut = new PrintWriter(new FileWriter(new File(System.getProperty("user.home")+"/Desktop/"+key)));
			Iterator listItr = vl.iterator();
			myOut.println(key);
			while(listItr.hasNext()){myOut.println(listItr.next());}
			myOut.close();			
		}
	}

}
