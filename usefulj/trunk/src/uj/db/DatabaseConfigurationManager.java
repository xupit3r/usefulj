package uj.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import uj.reflect.UJClassLoader;

public class DatabaseConfigurationManager
{
	public static final int MYSQL=1;
	private static UJClassLoader loader = new UJClassLoader();
	private static UJConnectionPool pool = new UJConnectionPool();
	
	public static UJDBConnection getConnection(String u, String pass, String host, String db, int type) throws ClassNotFoundException, SQLException 
	{
		loader.loadClass(getDriverURI(type));
		String url = getProtocol(type)+host+"/"+db;
		return lookupConnection(url,u,pass);
	}
	
	public static UJDBConnection getConnection(String cloc, String u, String pass, String host, String db, String type) throws ClassNotFoundException, IllegalArgumentException, SQLException, IllegalAccessException
	{
		loader.loadClass(cloc);
		String url = getProtocol(getIType(type))+host+"/"+db;
		return lookupConnection(url,u,pass);
		
	}
	
	private static UJDBConnection lookupConnection(String url, String u, String p) throws SQLException
	{
		UJDBConnection dbcon = pool.getConnection(url, u, p);
		if(dbcon == null)
		{
			Connection con = DriverManager.getConnection(url,u,p);
			dbcon = new UJDBConnection(url,u,p,con);
			pool.addConnection(dbcon);
		}
		return dbcon;
	}
	
	private static int getIType(String t) throws IllegalArgumentException, IllegalAccessException
	{
		int it = 0;
		Field[] fs = DatabaseConfigurationManager.class.getDeclaredFields();
		for(int i = 0; i < fs.length; i++)
			if(fs[i].getName().toLowerCase().equals(t.toLowerCase()))
				it = fs[i].getInt(null);
		return it;
	}
	
	//default for initial tests -> use to test other JDBC drivers
	private static String getDriverURI(int type)
	{
		String driverLoc = "";
		switch(type)
		{
			case MYSQL: driverLoc = "com.mysql.jdbc.Driver";break;
			default: driverLoc = "com.mysql.jdbc.Driver";
		}
		return driverLoc;
	}
	
	public static String getProtocol(int type)
	{
		String proto = "";
		switch(type)
		{
			case MYSQL: proto = "jdbc:mysql://";break;
			default: proto = "jdbc:mysql://";
		}
		return proto;
	}
	
	public static void recycleConnection(UJDBConnection c)
	{
		pool.addConnection(c);
	}

}
