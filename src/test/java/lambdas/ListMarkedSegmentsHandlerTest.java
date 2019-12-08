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

public class ListMarkedSegmentsHandlerTest extends LambdaTest {

	int randomSegmentsToAdd = 20;
	
	List<VideoSegment> addedSegments;
	
	VideoSegmentDAO dao;
	
	@Before
	public void setUp() throws Exception {
		dao = new VideoSegmentDAO();
		addedSegments = new ArrayList<VideoSegment>();
		for(int i = 0; i < randomSegmentsToAdd; i++) {
			boolean marked = (Math.random() > 0.5);
			VideoSegment segment = new VideoSegment("Location: " + i, "" + i, "" + i, true, marked);
			dao.addVideoSegment(segment);
			addedSegments.add(segment);
		}
		
	}

	@After
	public void tearDown() throws Exception {
		for(VideoSegment s : addedSegments) {
			dao.deleteVideoSegment(s.location);
		}
		dao.close();
	}

	@Test
	public void test() {
		Context context = createContext("getMarkedSegments");
		ListMarkedSegmentsHandler handler = new ListMarkedSegmentsHandler();
		ListMarkedSegmentsHandler.Response response = handler.handleRequest(null, context);
		for(VideoSegment s : dao.listAllVideoSegments()) {
			boolean contains = false;
			for(ListMarkedSegmentsHandler.Segment v : response.segments) {
				if(s.location.equals(v.url)) {
					contains = true;
					break;
				}
			}
			assertEquals(contains, s.getIsMarked());
		}
	}

}
