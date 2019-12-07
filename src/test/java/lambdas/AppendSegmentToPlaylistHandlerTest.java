package lambdas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.PlaylistDAO;
import database.VideoSegmentDAO;
import entity.VideoSegment;
import http.AppendSegmentRequest;
import http.AppendSegmentResponse;

public class AppendSegmentToPlaylistHandlerTest extends LambdaTest {
	
	static VideoSegmentDAO vsDao;
	static PlaylistDAO plDao;
	static List<VideoSegment> videoSegmentList;
	static String playlist1;
	static String playlist2;
	
	@BeforeClass
	public static void setUp () {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Testing Append Segments to Playlist");
		try {
			vsDao = new VideoSegmentDAO();
			plDao = new PlaylistDAO();
			initSegments();
			playlist1 = "TestPL1:" + UUID.randomUUID().toString();
			playlist2 = "TestPL2:" + UUID.randomUUID().toString();
			plDao.createPlaylist(playlist1);
			plDao.createPlaylist(playlist2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}	
	}
	
	/**
	 * Private Helper method to initialize the segments to add to the playlist.
	 */
	private static void initSegments() {
		videoSegmentList = new ArrayList<VideoSegment>();
		videoSegmentList.add(new VideoSegment("here.aws", "Luke Skywalker", "who?", true));
		videoSegmentList.add(new VideoSegment("there.com", "Darth Vader", "what?", true));
		videoSegmentList.add(new VideoSegment("//somewhere.aws", "Obi-Wan Kenobi", "when?", false));
		for (VideoSegment vs: videoSegmentList) {
			vsDao.addVideoSegment(vs);
		}
	}
	
	@AfterClass
	public static void tearDown () {
		try {
			plDao.deletePlaylist(playlist1);
			plDao.deletePlaylist(playlist2);
			for (VideoSegment vs: videoSegmentList) {
				vsDao.deleteVideoSegment(vs.location);
			}
			vsDao.close();
			plDao.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Private helper method for testing to generate a response object.
	 * @param videoLocation
	 * @param playlistName
	 * @return
	 */
	private AppendSegmentResponse generateResp (String videoLocation, String playlistName) {
		AppendSegmentRequest req = new AppendSegmentRequest(playlistName, videoLocation);
		System.out.print("Request: " + req.toString());
		return new AppendSegmentToPlaylistHandler().handleRequest(req, createContext("AppendSegment"));
	}
	
	@Test
	public void testAppend () {
		// Add all segments to PL1
		for (VideoSegment vs: videoSegmentList) {
			AppendSegmentResponse response = generateResp(vs.location, playlist1);
			System.out.println(response.toString());
			assertEquals(200, response.httpCode);
		}
		// Insert duplicates into PL2 to ensure allowability
		AppendSegmentResponse resp1 = generateResp(videoSegmentList.get(0).location, playlist2);
		AppendSegmentResponse resp2 = generateResp(videoSegmentList.get(1).location, playlist2);
		AppendSegmentResponse resp3 = generateResp(videoSegmentList.get(0).location, playlist2);
		assertEquals(200, resp1.httpCode);
		assertEquals(200, resp2.httpCode);
		assertEquals(200, resp3.httpCode);
		System.out.println(resp1.toString());
		System.out.println(resp2.toString());
		System.out.println(resp3.toString());
		
		List<VideoSegment> pl2List = new ArrayList<VideoSegment>();
		pl2List.add(videoSegmentList.get(0));
		pl2List.add(videoSegmentList.get(1));
		pl2List.add(videoSegmentList.get(0));
		// Ensure proper ordering
		try {
			assertTrue(videoSegmentList.equals(plDao.getPlaylist(playlist1).getVideos()));
			assertTrue(pl2List.equals(plDao.getPlaylist(playlist2).getVideos()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSegmentDoesntExist () {
		AppendSegmentResponse response = generateResp("SomeLocationThatCantExist", playlist1);
		System.out.println(response.toString());
		assertEquals(400, response.httpCode);
	}
}
