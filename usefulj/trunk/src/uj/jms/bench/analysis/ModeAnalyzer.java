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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class ModeAnalyzer
{
	public static void main(String args[])
	{
		try
		{
			Scanner reader = new Scanner(new File(args[0]));
			HashMap<Integer, Integer> freq = new HashMap<Integer, Integer>();
			while(reader.hasNext())
			{
				String line = reader.nextLine();
				int val = Integer.parseInt(line);
				if(freq.get(val) == null)freq.put(val, 1);
				else{int temp = freq.get(val); freq.put(val,temp++);}
					
			}
			Iterator<Map.Entry<Integer, Integer>> itr = freq.entrySet().iterator();
			int mode = 0, max = 0;
			while(itr.hasNext())
			{
				Map.Entry<Integer, Integer> entry = itr.next();
				int K = entry.getKey();
				int V = entry.getValue();
				if(V >= max)
					mode = K;
			}
			System.out.println("Mode: "+mode);
		}
		catch(FileNotFoundException fnf){fnf.printStackTrace();}
	}
	
	public static int getMode(ArrayList<Integer> list)
	{
		Iterator<Integer> lstItr = list.iterator();
		HashMap<Integer, Integer> freq = new HashMap<Integer, Integer>();
		while(lstItr.hasNext())
		{
			Integer line = lstItr.next();
			int val = line;
			if(freq.get(val) == null)freq.put(val, 1);
			else{int temp = freq.get(val); freq.put(val,temp++);}
				
		}
		Iterator<Map.Entry<Integer, Integer>> itr = freq.entrySet().iterator();
		int mode = 0, max = 0;
		while(itr.hasNext())
		{
			Map.Entry<Integer, Integer> entry = itr.next();
			int K = entry.getKey();
			int V = entry.getValue();
			if(V >= max)
				mode = K;
		}
		return mode;
		
	}
	

}
