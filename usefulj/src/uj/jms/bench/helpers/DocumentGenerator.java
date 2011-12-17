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
package uj.jms.bench.helpers;

import java.io.FileNotFoundException;

/**
 * Generate a Document of a Specified Size
 * @author Joseph D'Alessandro
 *
 */
public class DocumentGenerator
{
	// Doc. Size Variables
	public final static int small  = 1;
	public final static int medium = 2;
	public final static int large  = 3;
	
	public static String getDocument(int size) throws FileNotFoundException
	{
		switch(size)
		{
			case 1: return getDoc(1024);  
			case 2: return getDoc(4096);  
			case 3: return getDoc(8192); 
		}
		return getDoc(256);
	}
	
	private static String getDoc(int s) throws FileNotFoundException
	{
		String temp = "";
		for(int i = 0; i < s; i++){temp+='a';}
		return temp;
	}

}
