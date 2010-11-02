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
package uj.log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class UJLogger 
{
	private static final String NEWLINE = "\n";
	private static Writer ujLog;
	public static void setStandardOut(OutputStream s) {
		ujLog = new BufferedWriter(new OutputStreamWriter(s));
	}
	
	/**
	 * Log OUTPUT
	 */
	
	public static void out(String s){
		try {
			ujLog.append("[OUT]"+s+NEWLINE);
		} catch (IOException e) {System.err.println("Oh noes da log is messed up!");}
	}
	
	public static void error(String s){
		try {
			ujLog.append("[ERROR]"+s+NEWLINE);
		} catch (IOException e) {System.err.println("Oh noes da log is messed up!");}
	}

	public static void debug(String s){
		try {
			ujLog.append("[DEBUG]"+s+NEWLINE);
		} catch (IOException e) {System.err.println("Oh noes da log is messed up!");}
	}
}
