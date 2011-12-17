package uj.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uj.db.DatabaseConfigurationManager;
import uj.db.UJDBConnection;
import uj.jms.bench.helpers.FileHelper;
import uj.reflect.UJClassLoader;

public class LoadSomeClasses {

	private static String u = "root";
	private static String pass = "overlord";
	private static String host = "localhost";
	private static String db = "test_cl";
	private static int type = DatabaseConfigurationManager.MYSQL;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("FileHelper.getName(): "+FileHelper.class.getName().replaceAll("\\.", "/"));
		String className = FileHelper.class.getName();
		UJClassLoader cl = new UJClassLoader();
		
		byte[] ba = cl.getBinaryContent(className);
		
		UJDBConnection ujc = DatabaseConfigurationManager.getConnection(u, pass, host, db, type);
		Connection c = ujc.getConnection();
		PreparedStatement ps = c.prepareStatement("insert into test_classes(name,cf) values(?,?);");
		ps.setString(1,FileHelper.class.getName());
		ps.setBytes(2,ba);
		ps.executeUpdate();
		c.close();

	}

}
