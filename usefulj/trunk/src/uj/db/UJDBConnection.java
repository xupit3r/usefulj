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
