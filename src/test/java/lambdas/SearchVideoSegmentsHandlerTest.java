package lambdas;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.VideoSegmentDAO;
import entity.VideoSegment;
import http.SearchSegmentRequest;
import http.SearchSegmentResponse;

public class SearchVideoSegmentsHandlerTest extends LambdaTest{

	VideoSegmentDAO segmentDAO;
	int numSegments = 5;
	List<VideoSegment> testSegments;
	
	@Before
	public void setUp() throws Exception {
		testSegments = new ArrayList<VideoSegment>();
		segmentDAO = new VideoSegmentDAO();
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
		}
	}

	@After
	public void tearDown() throws Exception {
		for(VideoSegment vs : testSegments) {
			segmentDAO.deleteVideoSegment(vs.location);
		}
		segmentDAO.close();
	}

	@Test
	public void test() {
		SearchSegmentRequest request = new SearchSegmentRequest("character", "");
		SearchVideoSegmentsHandler handler = new SearchVideoSegmentsHandler();
		Context context = createContext("searchVideoSegments");
		SearchSegmentResponse response = handler.handleRequest(request, context);
		assertEquals(numSegments, response.segments.size());
		assertEquals(200, response.statusCode);
		
		request = new SearchSegmentRequest("ChaRacTer", "");
		response = handler.handleRequest(request, context);
		assertEquals(numSegments, response.segments.size());
		assertEquals(200, response.statusCode);
		
		request = new SearchSegmentRequest("3", "");
		response = handler.handleRequest(request, context);
		assertEquals(1, response.segments.size());
		assertEquals(200, response.statusCode);
		
		request = new SearchSegmentRequest("", "3");
		response = handler.handleRequest(request, context);
		assertEquals(1, response.segments.size());
		assertEquals(200, response.statusCode);
		
		request = new SearchSegmentRequest("" + (numSegments+1), "");
		response = handler.handleRequest(request, context);
		assertEquals(null, response.segments);
		assertNotEquals(200, response.statusCode);
		
		request = new SearchSegmentRequest("", "");
		response = handler.handleRequest(request, context);
		assertEquals(409, response.statusCode);
	}

}
