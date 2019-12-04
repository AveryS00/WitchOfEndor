package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Playlist;
import entity.VideoSegment;

public class TestDatabase {
	
	static Connection conn;
	static TestVSDAO vsDao;
	static TestPlaylistDAO plDao;
	
	/**
	 * Uses personal allocated Oracle server for those that have taken CS3431 Database Systems.
	 */
	@BeforeClass
	public static void oneTimeSetUp () {
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
			vsDao = new TestVSDAO(conn);
			plDao = new TestPlaylistDAO(conn);
			scanner.close();
		} catch (SQLException e) {
			System.out.println("Unable to connect to test database");
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void oneTimeTearDown () {
		try {
			conn.close();
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
			vsDao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
			assertEquals(vsDao.getVideoSegment("/right/here.aws"), 
					new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
			// Make sure you can't add the same video twice.
			assertFalse(vsDao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true)));
			// Check for when you try to get something that isn't there.
			assertEquals(null, vsDao.getVideoSegment("/right/there.aws"));
			// Delete the video from the database.
			assertTrue(vsDao.deleteVideoSegment("/right/here.aws"));
			assertEquals(null, vsDao.getVideoSegment("/right/here.aws"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate () {
		try {
			// Check the full update.
			assertTrue(vsDao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true)));
			assertTrue(vsDao.updateVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", false)));
			assertEquals(vsDao.getVideoSegment("/right/here.aws"), 
					new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", false));
			assertTrue(vsDao.deleteVideoSegment("/right/here.aws"));
			
			// Check the flip mark.
			VideoSegment vs = new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true, false);
			assertTrue(vsDao.addVideoSegment(vs));
			assertTrue(vsDao.flipMark(vs));
			assertEquals(vsDao.getVideoSegment("/right/here.aws"), 
					new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true, true));
			assertTrue(vsDao.deleteVideoSegment("/right/here.aws"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testList () {
		// Populate database
		assertTrue(vsDao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true)));
		assertTrue(vsDao.addVideoSegment(new VideoSegment("/there.aws", "Kirk", "Hi There", true)));
		assertTrue(vsDao.addVideoSegment(new VideoSegment("/somewhere.aws", "Red Shirt", "Help Me", false)));
		
		List<VideoSegment> list = new ArrayList<VideoSegment>();
		list.add(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
		list.add(new VideoSegment("/there.aws", "Kirk", "Hi There", true));
		list.add(new VideoSegment("/somewhere.aws", "Red Shirt", "Help Me", false));
		List<VideoSegment> DAOList = vsDao.listAllVideoSegments();
		assertTrue(list.containsAll(DAOList));
		
		assertTrue(vsDao.deleteVideoSegment("/right/here.aws"));
		assertTrue(vsDao.deleteVideoSegment("/there.aws"));
		assertTrue(vsDao.deleteVideoSegment("/somewhere.aws"));
	}
	
	@Test
	public void testPlaylist () {
		try {
			assertTrue(plDao.createPlaylist("PL1"));
			assertTrue(plDao.getPlaylist("PL1").equals(new Playlist("PL1")));
			assertTrue(plDao.deletePlaylist("PL1"));
			
			assertTrue(plDao.createPlaylist("PL1"));
			assertFalse(plDao.createPlaylist("PL1"));
			
			assertTrue(plDao.createPlaylist("PL2"));
			assertTrue(plDao.createPlaylist("PL3"));
			List<Playlist> pls = new ArrayList<Playlist>();
			pls.add(new Playlist("PL1"));
			pls.add(new Playlist("PL2"));
			pls.add(new Playlist("PL3"));
			assertTrue(plDao.listAllPlaylists().containsAll(pls));
			
			assertTrue(plDao.deletePlaylist("PL1"));
			assertFalse(plDao.deletePlaylist("PL1"));
			assertTrue(plDao.deletePlaylist("PL2"));
			assertTrue(plDao.deletePlaylist("PL3"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteFromPlaylist () {
		try {
			plDao.createPlaylist("PL1");
			vsDao.addVideoSegment(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
			vsDao.addVideoSegment(new VideoSegment("/there.aws", "Kirk", "Hi There", true));
			vsDao.addVideoSegment(new VideoSegment("/somewhere.aws", "Red Shirt", "Help Me", false));
			vsDao.addVideoSegment(new VideoSegment("/hi.aws", "guy", "why", false));
			plDao.appendVideoToPlaylist("PL1", "/right/here.aws");
			plDao.appendVideoToPlaylist("PL1", "/there.aws");
			plDao.appendVideoToPlaylist("PL1", "/somewhere.aws");
			plDao.appendVideoToPlaylist("PL1", "/hi.aws");
			
			List<VideoSegment> list = new ArrayList<VideoSegment>();
			list.add(new VideoSegment("/right/here.aws", "Spock", "Live long and prosper", true));
			list.add(new VideoSegment("/there.aws", "Kirk", "Hi There", true));
			list.add(new VideoSegment("/somewhere.aws", "Red Shirt", "Help Me", false));
			list.add(new VideoSegment("/hi.aws", "guy", "why", false));
			
			Iterator<VideoSegment> it = plDao.getPlaylist("PL1").iterator();
			for (int i = 0; i < 4; i++) {
				assertEquals(it.next(), list.get(i));
			}
			plDao.removeVideoFromPlaylist("PL1", 2);
			list.remove(1);
			it = plDao.getPlaylist("PL1").iterator();
			for (int i = 0; i < 3; i++) {
				assertEquals(it.next(), list.get(i));
			}
			
			assertTrue(plDao.deletePlaylist("PL1"));
			assertTrue(vsDao.deleteVideoSegment("/right/here.aws"));
			assertTrue(vsDao.deleteVideoSegment("/there.aws"));
			assertTrue(vsDao.deleteVideoSegment("/somewhere.aws"));
			assertTrue(vsDao.deleteVideoSegment("/hi.aws"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
