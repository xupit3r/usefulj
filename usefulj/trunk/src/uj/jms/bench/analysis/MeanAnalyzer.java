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
import java.util.Iterator;
import java.util.Scanner;

public class MeanAnalyzer
{
	public static void main(String args[])
	{
		try
		{
			Scanner reader = new Scanner(new File(args[0]));
			int sum = 0;
			int count = 0;
			while(reader.hasNext()){sum += Integer.parseInt(reader.nextLine());count++;}
			double mean = sum/count;
			System.out.println("Mean: "+mean);
		}
		catch(FileNotFoundException fnf){fnf.printStackTrace();}
	}
	
	public static int getMean(ArrayList<Integer> list)
	{
		double sum = 0;
		int count = 0;
		Iterator<Integer> itr = list.iterator();
		while(itr.hasNext()){sum+=itr.next();count++;}
		System.out.println("[MEAN] Count: "+count);
		return (int)(sum/count);
	}

}
