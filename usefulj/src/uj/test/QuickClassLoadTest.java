package uj.test;

import java.sql.SQLException;

import uj.db.DatabaseConfigurationManager;
import uj.db.UJDBConnection;
import uj.reflect.UJClassLoader;

public class QuickClassLoadTest {

	private static String u = "root";
	private static String pass = "overlord";
	private static String host = "localhost";
	private static String db = "test_cl";
	private static int type = DatabaseConfigurationManager.MYSQL;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		UJDBConnection con = DatabaseConfigurationManager.getConnection(u, pass, host, db, type);
		UJClassLoader ujc = new UJClassLoader(con.getConnection());
		ujc.loadClass("uj.jms.bench.helpers.FileHelper");
		System.out.println("Done loading.");
		System.out.println("FileHelper: "+Class.forName("uj.jms.bench.helpers.FileHelper"));

	}

}
