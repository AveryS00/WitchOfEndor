package lambdas;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.PlaylistDAO;
import database.VideoSegmentDAO;
import entity.Playlist;
import entity.VideoSegment;
import http.RemoveSegmentFromPlaylistRequest;
import http.RemoveSegmentFromPlaylistResponse;

public class RemoveSegmentFromPlaylistHandlerTest extends LambdaTest {

	PlaylistDAO playlistDAO;
	VideoSegmentDAO segmentDAO;
	List<VideoSegment> testSegments;
	Playlist testPlaylist;
	String testPlaylistName = "removeSegmentTestPlaylist";
	int numSegments = 5;
	
	@Before
	public void setUp() throws Exception {
		playlistDAO = new PlaylistDAO();
		segmentDAO = new VideoSegmentDAO();
		testSegments = new ArrayList<VideoSegment>();
		if(playlistDAO.listAllPlaylistNames().contains(testPlaylistName)) {
			playlistDAO.deletePlaylist(testPlaylistName);
		}
		playlistDAO.createPlaylist(testPlaylistName);
		List<String> locationsInLibrary = new ArrayList<String>();
		for(VideoSegment vs : segmentDAO.listAllVideoSegments()) {
			locationsInLibrary.add(vs.location);
		}
		for(int i = 0; i < numSegments; i++) {
			VideoSegment s = new VideoSegment("location" + i, "character" + i, "" + i, true);
			testSegments.add(s);
			if(locationsInLibrary.contains(s.location)) {
				segmentDAO.deleteVideoSegment(s.location);
			}
			segmentDAO.addVideoSegment(s);
			playlistDAO.appendVideoToPlaylist(testPlaylistName, s.location);
		}
	}

	@After
	public void tearDown() throws Exception {
		playlistDAO.deletePlaylist(testPlaylistName);
		for(VideoSegment vs : testSegments) {
			segmentDAO.deleteVideoSegment(vs.location);
		}
		playlistDAO.close();
		segmentDAO.close();
	}

	@Test
	public void test() throws Exception {
		RemoveSegmentFromPlaylistHandler handler = new RemoveSegmentFromPlaylistHandler();
		RemoveSegmentFromPlaylistRequest request = new RemoveSegmentFromPlaylistRequest(testPlaylistName, 2);
		Context context = createContext("removeSegmentFromPlaylist");
		RemoveSegmentFromPlaylistResponse response = handler.handleRequest(request, context);
		assertEquals(response.statusCode, 200);
		Playlist playlist = playlistDAO.getPlaylist(testPlaylistName);
		assertEquals(4, playlistDAO.getPlaylist(testPlaylistName).getVideos().size());
		assertFalse(playlist.getVideos().contains(testSegments.get(1)));
		request = new RemoveSegmentFromPlaylistRequest(testPlaylistName, 5);
		response = handler.handleRequest(request, context);
		assertNotEquals(response.statusCode, 200);
	}

}
