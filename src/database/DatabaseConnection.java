package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static final String rdsMySqlDatabaseUrl = System.getenv("rdsMySqlDatabaseUrl");
	private static final String dbUsername = System.getenv("dbUsername");
	private static final String dbPassword = System.getenv("dbPassword");
	
	private static final String jdbcTag = "jdbc:mysql://";
	private static final String rdsMySqlDatabasePort = "3306";
	private static final String multiQueries = "?allowMultiQueries=true";
	private static final String dbName = "innodb";
	
	static Connection conn;
	
	/**
	 * Creates the connection to the database
	 * @return The database connection
	 * @throws SQLException
	 */
	protected static Connection connect () throws SQLException {
		try {
			conn = DriverManager.getConnection(jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbName + multiQueries,
					dbUsername,
					dbPassword);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
