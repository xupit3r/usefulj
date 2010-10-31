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
package uj.string;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StringUtils 
{
	public static String makeString(File f) throws IOException
	{
	    byte[] buffer = new byte[(int) f.length()];
	    BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(f));
	    bufIn.read(buffer);
	    bufIn.close();
	    return new String(buffer);
	}
	
	public static String[] deComma(String s)
	{
		String[] arr = null;
		if(!s.contains(","))
		{
			arr = new String[1];
			arr[0] = s;
		}
		else
			arr = s.split(",");
		return arr;
	}

}
