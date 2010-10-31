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

public class ThreadStateManager
{
	public static boolean stop = false;
	public static boolean record = false;
	public static boolean pause = false;
	
	public static void stopThreads(){stop=true;}
	public static void goThreads(){stop=false;}
	public static void startRecord(){record=true;}
	public static void stopRecord(){record=false;}
	public static void unPauseThreads(){pause=false;}
	public static void pauseThreads(){pause=true;}
	
}
