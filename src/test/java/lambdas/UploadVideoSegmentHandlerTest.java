package lambdas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Base64;
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
	static VideoSegmentDAO vsDao;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Testing Uploading Video Segment");
		try {
			vsDao = new VideoSegmentDAO();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			vsDao.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testUploadVideoSegment () {
		UploadVideoSegmentRequest req = new UploadVideoSegmentRequest("Kirk", "Yes of course", "noencoding");
		UploadVideoSegmentResponse response = new UploadVideoSegmentHandler().handleRequest(req, createContext("uploadVideo"));
		assertEquals(200, response.statusCode);
		//assertTrue(vsDao.);
		System.out.println("Response: " + response.toString());
	}
}
