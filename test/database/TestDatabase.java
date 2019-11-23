package database;

import static org.junit.Assert.assertNotEquals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class TestDatabase {
	
	Connection conn;
	
	/**
	 * Uses personal allocated Oracle server for those that have taken CS3431 Database Systems.
	 */
	@Before
	public void setup () {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("No driver");
			e.printStackTrace();
		}
		
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Username for database: ");
			String username = scanner.nextLine();
			System.out.println("Password: ");
			String password = scanner.nextLine();
			conn = DriverManager.getConnection("jdbc:oracle:thin:@csorcl.cs.wpi.edu:1521:orcl", username, password);
			scanner.close();
		} catch (SQLException e) {
			System.out.println("Unable to connect to test database");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection () {
		assertNotEquals(conn, null);
	}
}
