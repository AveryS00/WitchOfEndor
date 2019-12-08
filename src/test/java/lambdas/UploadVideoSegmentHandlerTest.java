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
import database.VideoSegmentDAO;
import entity.VideoSegment;
import http.CreatePlaylistRequest;
import http.CreatePlaylistResponse;
import http.DeletePlaylistRequest;
import http.DeletePlaylistResponse;
import http.UploadVideoSegmentRequest;
import http.UploadVideoSegmentResponse;

public class UploadVideoSegmentHandlerTest extends LambdaTest {
	static String characterName;
	static String text;
	static String base64Encoded;

	@BeforeClass
	public static void setUp() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Testing Uploading Video Segment");
		characterName = "TestVS:" + UUID.randomUUID().toString();
		try {
			VideoSegmentDAO vsDao = new VideoSegmentDAO();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			VideoSegmentDAO vsDao = new VideoSegmentDAO();
			vsDao.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	UploadVideoSegmentResponse generateResp (String statusCode, String response) {
		UploadVideoSegmentRequest req = new UploadVideoSegmentRequest(characterName, text, base64Encoded);
		System.out.println("Requesting: " + req.toString());
		return new UploadVideoSegmentHandler().handleRequest(req, createContext("uploadVideoSegment"));
	}
	
	@Test
	public void testUploadVideoSegment () {
		VideoSegmentDAO vsDao = new VideoSegmentDAO();
		UploadVideoSegmentResponse response1 = new generateResp(statusCode, response);
		assertEquals(200, response1.statusCode);
		List<VideoSegment> vsNames = vsDao.listAllVideoSegments();
		assertTrue(vsNames.contains(characterName));
		System.out.println("Response1: " + response1.toString());
		
		// Make sure unable to duplicate a video segment
		UploadVideoSegmentResponse dupResponse = new generateResp(statusCode, response);
		assertEquals(400, dupResponse.statusCode);
		System.out.println("Duplicate Response: " + dupResponse.toString());
	}
}
