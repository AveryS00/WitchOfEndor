package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	
	/*
	private static final String rdsMySqlDatabaseUrl = System.getenv("rdsMySqlDatabaseUrl");
	private static final String dbUsername = System.getenv("dbUsername");
	private static final String dbPassword = System.getenv("dbPassword");
	*/
	
	private static final String rdsMySqlDatabaseUrl = "woe-calculator-db.cyxj4coffoqr.us-east-2.rds.amazonaws.com";
	private static final String dbUsername = "calcAdmin";
	private static final String dbPassword = "FlyingColours";
	
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
	protected static Connection connect () throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			conn = DriverManager.getConnection(
					jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbName + multiQueries,
					dbUsername,
					dbPassword);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Helper to close the pstmt and rset used by the other DAO objects after finished.
	 * @param pstmt The PreparedStatement to close.
	 * @param rset The ResultSet to close.
	 */
	protected static void closeStmt (PreparedStatement pstmt, ResultSet rset) {
		try {
			if (rset != null) {
				rset.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
