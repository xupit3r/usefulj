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

public class UJDBConnection
{
	private int mk = 0;
	private Connection con;
	public UJDBConnection(String ur, String us, String p, Connection c)
	{
		mk = (ur+us+p).hashCode();
		con = c;
	}
	
	protected UJDBConnection(int k, Connection c)
	{
		mk = k;
		con = c;
	}
	
	public int getMapKey()
	{
		return mk;
	}
	
	protected void setMapKey(int k)
	{
		mk = k;
	}
	
	public Connection getConnection()
	{
		return con;
	}

}
