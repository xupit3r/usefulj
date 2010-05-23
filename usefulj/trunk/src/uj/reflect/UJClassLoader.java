package uj.reflect;

import java.util.HashMap;

public class UJClassLoader extends ClassLoader
{
	private HashMap<String,Class<?>> classes = null;
	
	public UJClassLoader()
	{
		//set the parent class loader.
		super(UJClassLoader.class.getClassLoader());
		classes = new HashMap<String,Class<?>>();
	}
	
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		return loadClass(name, true);
	}
	
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
	 * At some point I want to implement this.  Read class files from a
	 * database server somewhere out in the world!
	 * @param name - class name to load
	 * @return byte array representing the binary format of the class file
	 */
	public Class<?> readClassFileFromDB(String name)
	{
		byte[] cf = null;
		return null;
	}
	
	public Class<?> findPreloadedClass(String name)
	{
		return (Class<?>)classes.get(name);
	}

}
