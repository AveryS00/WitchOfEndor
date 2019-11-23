package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.VideoSegment;

public class TestDatabase {
	
	Connection conn;
	TestVSDAO dao;
	
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
			dao = new TestVSDAO(conn);
			scanner.close();
		} catch (SQLException e) {
			System.out.println("Unable to connect to test database");
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanup () {
		try {
			dao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testConnection () {
		assertNotEquals(conn, null);
	}
	
	@Test
	public void testAddGetDelete () {
		try {
			dao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
			assertEquals(dao.getVideoSegment("/right/here.aws"), 
					new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
			// Make sure you can't add the same video twice.
			assertFalse(dao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true)));
			// Check for when you try to get something that isn't there.
			assertEquals(null, dao.getVideoSegment("/right/there.aws"));
			// Delete the video from the database.
			assertTrue(dao.deleteVideoSegment("/right/here.aws"));
			assertEquals(null, dao.getVideoSegment("/right/here.aws"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate () {
		try {
			// Check the full update.
			assertTrue(dao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true)));
			assertTrue(dao.updateVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", false)));
			assertEquals(dao.getVideoSegment("/right/here.aws"), 
					new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", false));
			assertTrue(dao.deleteVideoSegment("/right/here.aws"));
			
			// Check the flip mark.
			assertTrue(dao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true, false)));
			assertTrue(dao.flipMark("/right/here.aws"));
			assertEquals(dao.getVideoSegment("/right/here.aws"), 
					new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true, true));
			assertTrue(dao.deleteVideoSegment("/right/here.aws"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testList () {
		// Populate database
		assertTrue(dao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true)));
		assertTrue(dao.addVideoSegment(new VideoSegment("/there.aws", "Kirk", "Hi There", true)));
		assertTrue(dao.addVideoSegment(new VideoSegment("/somewhere.aws", "Red Shirt", "Help Me", false)));
		
		List<VideoSegment> list = new ArrayList<VideoSegment>();
		list.add(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
		list.add(new VideoSegment("/there.aws", "Kirk", "Hi There", true));
		list.add(new VideoSegment("/there.aws", "Kirk", "Hi There", true));
		assertEquals(list, dao.listAllVideoSegments());
		
		assertTrue(dao.deleteVideoSegment("/right/here.aws"));
		assertTrue(dao.deleteVideoSegment("/there.aws"));
		assertTrue(dao.deleteVideoSegment("/somewhere.aws"));
	}
}
