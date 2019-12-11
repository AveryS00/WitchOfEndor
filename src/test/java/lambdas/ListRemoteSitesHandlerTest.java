package lambdas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.RemoteSiteDAO;
import http.ListRemoteSitesResponse;

public class ListRemoteSitesHandlerTest extends LambdaTest {

	RemoteSiteDAO dao;
	String testSite;
	
	@Before
	public void setUp() throws Exception {
		dao = new RemoteSiteDAO();
		testSite = "google.com";
		dao.addRemoteSite(testSite);
	}

	@After
	public void tearDown() throws Exception {
		dao.removeRemoteSite(testSite);
		dao.close();
	}

	@Test
	public void test() {
		ListRemoteSitesHandler handler = new ListRemoteSitesHandler();
		Context context = createContext("listRemoteSites");
		ListRemoteSitesResponse response = handler.handleRequest(null, context);
		assertEquals(response.statusCode, 200);
		boolean containsTest = false;
		for(String RS : response.list) {
			if(RS.equals(testSite)) {
				containsTest = true;
			}
		}
		response.toString();
		assertTrue(containsTest);
		ListRemoteSitesResponse response2 = new ListRemoteSitesResponse (403, "Can't List Sites");
		assertEquals(403, response2.statusCode);
	}

}