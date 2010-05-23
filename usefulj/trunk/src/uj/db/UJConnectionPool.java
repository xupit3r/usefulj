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
