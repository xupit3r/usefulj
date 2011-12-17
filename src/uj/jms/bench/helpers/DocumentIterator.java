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

import java.io.File;
import java.util.Iterator;


public class DocumentIterator implements Iterator<File>
{
	
	private File dir = null;
	private File[] files = null;
	private int idx = 0;
	
	public DocumentIterator(){}
	
	public DocumentIterator(String d)
	{
		if(setDir(d)){files = dir.listFiles();idx = 0;}
		else System.err.println("[DocumentIterator]: Not a Directory.");
	}
	
	public boolean setDir(String d)
	{
		dir = new File(d);
		dir = dir.isDirectory() ? dir : null;
		return dir != null;
	}
	
	@Override
	public boolean hasNext()
	{
		return idx < files.length;
	}

	@Override
	public File next()
	{
		return files[idx++];
	}

	@Override
	/**
	 * I know, I know, this is a terrible way to handle this, but
	 * when the hell am I even going to use this method?
	 */
	public void remove()
	{
		files[idx] = null;
		
	}
	
	public File current()
	{
		return files[(idx - 1) < 0 ? 0 : idx-1];
	}

}
