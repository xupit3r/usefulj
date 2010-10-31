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

public class ThreadRegister {
	private static int threadCount;
	private static int totalThreadsRegistered;
	private static int totalThreadsUnregistered;
	
	public static synchronized int registerThread() {
		threadCount++;
		totalThreadsRegistered++;
		
		return totalThreadsRegistered;
	}
	
	public static synchronized void unregisterThread() {
		threadCount--;
		totalThreadsUnregistered++;
	}
	
	public static void resetCounts()
	{
		threadCount              = 0;
		totalThreadsRegistered   = 0;
		totalThreadsUnregistered = 0;
		
	}
	
	public static synchronized int getThreadCount() {
		return threadCount;
	}
	
	public static synchronized int getTotalThreadsRegistered() {
		return totalThreadsRegistered;
	}
	
	public static synchronized int getTotalThreadsUnregistered() {
		return totalThreadsUnregistered;
	}
}

