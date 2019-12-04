package lambdas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.PlaylistDAO;
import entity.Playlist;

public class RemoveSegmentFromPlaylistHandlerTest {
	
	PlaylistDAO dao;
	String dummyPlaylistName = "dummyPlaylist";
	int playlistSize = 5;
	
	
	@Before
	public void setUp() throws Exception {
		dao = new PlaylistDAO();
		// create dummy playlist
		dao.createPlaylist(dummyPlaylistName);
		for(int i = 0; i < playlistSize; i++) {
			dao.appendVideoToPlaylist(dummyPlaylistName, Integer.toString(i));
		}
	}

	@After
	public void tearDown() throws Exception {
		dao.deletePlaylist(dummyPlaylistName);
		dao.close();
	}

	@Test
	public void test() {
	}

}
