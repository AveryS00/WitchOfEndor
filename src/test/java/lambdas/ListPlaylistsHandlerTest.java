package lambdas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.PlaylistDAO;
import entity.Playlist;
import http.ListPlaylistResponse;

public class ListPlaylistsHandlerTest extends LambdaTest {

	PlaylistDAO dao;
	Playlist testPlaylist;
	
	@Before
	public void setUp() throws Exception {
		dao = new PlaylistDAO();
		testPlaylist = new Playlist("test");
		dao.createPlaylist(testPlaylist.getName());
	}

	@After
	public void tearDown() throws Exception {
		dao.deletePlaylist(testPlaylist.getName());
		dao.close();
	}

	@Test
	public void test() {
		ListPlaylistsHandler handler = new ListPlaylistsHandler();
		Context context = createContext("listPlaylists");
		ListPlaylistResponse response = handler.handleRequest(null, context);
		assertEquals(response.statusCode, 200);
		boolean containsTest = false;
		for(Playlist p : response.list) {
			if(p.getName().equals(testPlaylist.getName())) {
				containsTest = true;
			}
		}
		assertTrue(containsTest);
	}

}
