package lambdas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import database.VideoSegmentDAO;
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
		if (vsDao.getVideoSegment("https://cs3733-witch-of-endor.s3.us-east-2.amazonaws.com/VideoSegments/Yesofcourse.ogg") != null) {
			vsDao.deleteVideoSegment("https://cs3733-witch-of-endor.s3.us-east-2.amazonaws.com/VideoSegments/Yesofcourse.ogg");
		}
		UploadVideoSegmentRequest req = new UploadVideoSegmentRequest("Kirk", "Yes of course", encoder("./src/test/resources/Yes of course.ogg"));
		System.out.println("Request: " + req.getCharacter() + " " + req.getText());
		UploadVideoSegmentResponse response = new UploadVideoSegmentHandler().handleRequest(req, createContext("uploadVideo"));
		System.out.println("Response: " + response.toString());
		assertEquals(200, response.statusCode);
	}
	
	private String encoder(String filePath) {
        String base64File = "";
        File file = new File(filePath);
        try (FileInputStream videoFile = new FileInputStream(file)) {
            // Reading a file from file system
            byte fileData[] = new byte[(int) file.length()];
            videoFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }
        return base64File;
    }
}
