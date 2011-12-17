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
package uj.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;


public class UJConnectionPool
{
	private HashMap<Integer,ArrayList<Connection>> pool = null;
	public UJConnectionPool()
	{
		pool = new HashMap<Integer,ArrayList<Connection>>();
	}
	
	public UJDBConnection getConnection(String url, String u, String p)
	{
		String tmp = new String(url+u+p);
		int hk = tmp.hashCode();
		return lookupConnection(hk);		
	}
	
	private UJDBConnection lookupConnection(int k)
	{
		ArrayList<Connection> al = pool.get(k);
		if(al == null)
			al = new ArrayList<Connection>();
		return al.size() > 0 ? new UJDBConnection(k,al.remove(0)) : null;
	}
	
	public void addConnection(UJDBConnection c)
	{
		ArrayList<Connection> al = pool.get(c.getMapKey());
		if(al == null)
			al = new ArrayList<Connection>();
		al.add(c.getConnection());
		pool.put(c.getMapKey(), al);
	}

}
