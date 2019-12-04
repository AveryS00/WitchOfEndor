package lambdas;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import database.RemoteSiteDAO;
import http.RegisterRemoteSiteRequest;
import http.RegisterRemoteSiteResponse;

public class RegisterRemoteSiteHandlerTest extends LambdaTest {

	RemoteSiteDAO dao;
	
	String testURLSuccess = "testURLSuccess";
	String testURLFail = "testURLFail";
	
	
	@Before
	public void setupTest() {
		//Setup database to test
		try {
			dao = new RemoteSiteDAO();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		List<String> urls = dao.listAllRemoteSites();
		if(urls.contains(testURLSuccess)) {
			dao.removeRemoteSite(testURLSuccess);
		}
		if(!urls.contains(testURLFail)) {
			dao.addRemoteSite(testURLFail);
		}
	}

	@Test
	public void testSuccess() {
		RegisterRemoteSiteHandler handler = new RegisterRemoteSiteHandler();
		Context context = createContext("registerRemoteSite");
		RegisterRemoteSiteRequest request = new RegisterRemoteSiteRequest(testURLSuccess);
		RegisterRemoteSiteResponse response = handler.handleRequest(request, context);
		if(response.statusCode != 200) {
			System.err.println("Error " + response.statusCode + ": " + response.error);
			fail();
		}
		assertEquals(response.statusCode, 200);
	}
	
	@Test
	public void testFailure() {
		RegisterRemoteSiteHandler handler = new RegisterRemoteSiteHandler();
		Context context = createContext("registerRemoteSite");
		RegisterRemoteSiteRequest request = new RegisterRemoteSiteRequest(testURLFail);
		RegisterRemoteSiteResponse response = handler.handleRequest(request, context);
		assertEquals(response.statusCode, 409);
	}

	@After
	public void cleanupTest() {
		List<String> urls = dao.listAllRemoteSites();
		if(urls.contains(testURLSuccess)) {
			dao.removeRemoteSite(testURLSuccess);
		}
		if(urls.contains(testURLFail)) {
			dao.removeRemoteSite(testURLFail);
		}
		try {
			dao.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

}
