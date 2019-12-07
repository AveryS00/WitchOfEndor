package lambdas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.PlaylistDAO;
import http.CreatePlaylistRequest;
import http.CreatePlaylistResponse;
import http.DeletePlaylistRequest;
import http.DeletePlaylistResponse;

public class CreateDeletePlaylistHandlerTest extends LambdaTest {
	
	static String randomName1;
	static String randomName2;
	static String randomName3;
	static PlaylistDAO plDao;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Testing Creating and Deleting Playlists");
		randomName1 = "TestPL:" + UUID.randomUUID().toString();
		randomName2 = "TestPL2:" + UUID.randomUUID().toString();
		randomName3 = "TestPL3:" + UUID.randomUUID().toString();
		try {
			plDao = new PlaylistDAO();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			plDao.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	CreatePlaylistResponse generateResp (String playlistName) {
		CreatePlaylistRequest req = new CreatePlaylistRequest(playlistName);
		System.out.println("Requesting: " + req.toString());
		return new CreatePlaylistHandler().handleRequest(req, createContext("createPlaylist"));
	}
	
	DeletePlaylistResponse generateDelResp (String playlistName) {
		DeletePlaylistRequest req = new DeletePlaylistRequest(playlistName);
		System.out.println("Requesting: " + req.toString());
		return new DeletePlaylistHandler().handleRequest(req, createContext("deletePlaylist"));
	}

	@Test
	public void testCreateDeletePlaylist () {
		CreatePlaylistResponse response1 = generateResp(randomName1);
		CreatePlaylistResponse response2 = generateResp(randomName2);
		CreatePlaylistResponse response3 = generateResp(randomName3);
		assertEquals(200, response1.httpCode);
		assertEquals(200, response2.httpCode);
		assertEquals(200, response3.httpCode);
		List<String> plNames = plDao.listAllPlaylistNames();
		assertTrue(plNames.contains(randomName1));
		assertTrue(plNames.contains(randomName2));
		assertTrue(plNames.contains(randomName3));
		System.out.println("Response1: " + response1.toString());
		System.out.println("Response2: " + response2.toString());
		System.out.println("Response3: " + response3.toString());
		
		// Make sure unable to duplicate a playlist
		CreatePlaylistResponse dupResponse = generateResp(randomName1);
		assertEquals(400, dupResponse.httpCode);
		System.out.println("Duplicate Response: " + dupResponse.toString());
		
		// Delete the playlists
		DeletePlaylistResponse delResp1 = generateDelResp(randomName1);
		DeletePlaylistResponse delResp2 = generateDelResp(randomName2);
		DeletePlaylistResponse delResp3 = generateDelResp(randomName3);
		assertEquals(200, delResp1.httpCode);
		assertEquals(200, delResp2.httpCode);
		assertEquals(200, delResp3.httpCode);
		plNames = plDao.listAllPlaylistNames();
		assertFalse(plDao.listAllPlaylistNames().contains(randomName1));
		assertFalse(plDao.listAllPlaylistNames().contains(randomName2));
		assertFalse(plDao.listAllPlaylistNames().contains(randomName3));
		System.out.println("Delete Response1: " + delResp1.toString());
		System.out.println("Delete Response2: " + delResp2.toString());
		System.out.println("Delete Response3: " + delResp3.toString());
		
		// Try to delete playlist that doesn't exist
		DeletePlaylistResponse response = generateDelResp("TestPL:" + UUID.randomUUID().toString());
		assertEquals(400, response.httpCode);
		System.out.println("Non-existant Playlist Delete Response: " + response.toString());
	}
}
