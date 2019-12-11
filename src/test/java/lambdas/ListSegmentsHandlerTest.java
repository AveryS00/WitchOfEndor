package lambdas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.VideoSegmentDAO;
import http.ListSegmentsResponse;

public class ListSegmentsHandlerTest extends LambdaTest {

	static VideoSegmentDAO vsDao;
	
	@BeforeClass
	public static void setUp () {
		try {
			vsDao = new VideoSegmentDAO();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@AfterClass
	public static void tearDown () {
		try {
			vsDao.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testListSegments () {
		ListSegmentsResponse response = new ListSegmentsHandler().handleRequest(null, createContext("list segments"));
		assertEquals(200, response.statusCode);
		System.out.println(response.toString());
	}
}
