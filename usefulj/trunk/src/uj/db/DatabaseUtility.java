package uj.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtility
{
	private UJDBConnection con = null;
	
	public DatabaseUtility(String u, String pass, String host, String db, int type) throws ClassNotFoundException, SQLException
	{
		con = DatabaseConfigurationManager.getConnection(u, pass, host, db, type);
	}
	
	public DatabaseUtility(String cloc, String u, String pass, String host, String db, String type) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException
	{
		con = DatabaseConfigurationManager.getConnection(cloc, u, pass, host, db, type);
	}
	
	public DatabaseUtility(String url, String u, String p,Connection c)
	{
		con = new UJDBConnection(url,u,p,c);
	}
	
	public DatabaseUtility(UJDBConnection dbc)
	{
		con = dbc;
	}
	
	public ResultSet runStatement(String s) throws SQLException
	{
		Connection c = con.getConnection();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(s);
		return rs;
	}
	
	public int runUpdate(String s) throws SQLException
	{
		Connection c = con.getConnection();
		Statement stmt = c.createStatement();
		int result = stmt.executeUpdate(s);
		return result;
	}
	
	public void closeConnection() throws SQLException
	{
		DatabaseConfigurationManager.recycleConnection(con);
	}

}
