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
package uj.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UJClassLoader extends ClassLoader
{
	/**
	 * Map of classnames -> loaded classes
	 */
	private HashMap<String,Class<?>> classes = null;

	/**
	 * holder for the db connection to be used when loading class from a database
	 */
	private Connection dbc = null;
	
	public UJClassLoader()
	{
		//set the parent class loader.
		super(UJClassLoader.class.getClassLoader());
		classes = new HashMap<String,Class<?>>();
	}
	
	public UJClassLoader(Connection conn)
	{
		//set the parent class loader.
		super(UJClassLoader.class.getClassLoader());
		classes = new HashMap<String,Class<?>>();
		dbc = conn;
	}
	
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		return loadClass(name, true);
	}
	
	/**
	 * Given a string describing the desired class this method will find the class file
	 * and load it into the JVM.  If the class file is already loaded in the JVM, then a 
	 * reference to that class will be returned.
	 * 
	 * @param name - the name of the class to be loaded
	 * @param res - flag indicating whether or not this method link the class
	 * 
	 * @return the desired class
	 * 
	 * @throws ClassNotFoundException if the class could not be found on any of paths
	 */
	public synchronized Class<?> loadClass(String name, boolean res) throws ClassNotFoundException
	{
		Class<?> c = null;
		
		c = findPreloadedClass(name);
		
		if(c == null)
		{
			c = readClassFileFromDB(name);
			if(c == null)
			{
				try
				{
					c = super.findSystemClass(name);
					return c;
				}catch(ClassNotFoundException cnf){}
				try
				{
					c = super.loadClass(name);
				}catch(ClassNotFoundException cnf){}
				c = super.findClass(name);
				if(res)
					resolveClass(c);
			}
			classes.put(name, c);
		}
		
		
		return c;
	}
	
	/**
	 * Provided that a DB connection was provided for the instance of this class
	 * this method will load a class file from a database.
	 * @param name - class name to load
	 * @return the loaded class
	 */
	public Class<?> readClassFileFromDB(String name)
	{
		PreparedStatement stmt;
		ResultSet rs;
		byte[] cf = null;
		if(dbc != null) {
			try {
				stmt = dbc.prepareStatement("select cf from test_classes where name = ?");
				stmt.setString(1, name);
				rs = stmt.executeQuery();
				if(rs.next()) {
					cf = rs.getBytes("cf");
				}
			} catch (SQLException e) {} // if an exception occurs just assume that we have the class file locally
			if(cf != null) {
				return defineClass(name, cf, 0, cf.length);
			}
		}
		return null;
	}
	
	public Class<?> findPreloadedClass(String name)
	{
		return (Class<?>)classes.get(name);
	}
	
	
	/**
	 * it is assumed that className is derived from the Class.getName() call
	 *  -- not using the string generated from that call will cause this not to work
	 * @param className
	 * @return a byte array containing the contents of the class file
	 * @throws IOException
	 */
	public byte[] getBinaryContent(String className) throws IOException{
		
		// get a handle on the class file
		InputStream is = this.getClass().getResourceAsStream("/"+className.replaceAll("\\.", "/")+".class");
		
		// first find out how many bytes we are dealing with
		int bytes = 0;
		while (true) {
	        int data = is.read();
	        if (data == -1) {
	           break;
	        }
	        bytes++;
		}
		is.close();
		
		// next prepare data structures for reading the bytes from 
		// the class file
		byte[] ba = new byte[bytes];
		int idx = 0;
		// reset the stream position to the start
		is = this.getClass().getResourceAsStream("/"+className.replaceAll("\\.", "/")+".class");
		while (true) {
	        int data = is.read();
	        if (data == -1) {
	           break;
	        }
	        ba[idx] = (byte) data;
	        idx++;
		}
		return ba;
	}

}
