package server;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {

	private static Connection cn;
	
	public static Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String url = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=Torneo(YXYK0001);"    
                    + "integratedSecurity=true;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";
			
			cn = DriverManager.getConnection(url);
			
		} catch (Exception e) {
			cn = null;
		}
		
		return cn;
	}
}
