package lambdas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.RemoteSiteDAO;
import http.UnregisterRemoteSiteRequest;
import http.UnregisterRemoteSiteResponse;

public class UnregisterRemoteSiteHandlerTest extends LambdaTest {
	
	RemoteSiteDAO dao;
	String testSuccessName = "testRemoveSuccess";
	String testFailName = "testRemoveFail";

	@Before
	public void setUp() throws Exception {
		dao = new RemoteSiteDAO();
		dao.addRemoteSite(testSuccessName);
	}

	@After
	public void tearDown() throws Exception {
		dao.removeRemoteSite(testSuccessName);
		dao.close();
	}

	@Test
	public void test() {
		UnregisterRemoteSiteRequest request = new UnregisterRemoteSiteRequest(testSuccessName);
		UnregisterRemoteSiteHandler handler = new UnregisterRemoteSiteHandler();
		Context context = createContext("unregisterSite");
		UnregisterRemoteSiteResponse response = handler.handleRequest(request, context);
		assertEquals(response.statusCode, 200);
		request = new UnregisterRemoteSiteRequest(testFailName);
		response = handler.handleRequest(request, context);
		assertNotEquals(response.statusCode, 200);
	}

}
