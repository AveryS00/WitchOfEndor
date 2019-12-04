package lambdas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import http.RegisterRemoteSiteRequest;
import http.RegisterRemoteSiteResponse;

public class TestRegisterRemoteSite {
	
	@Test
	public void testRegister() {
		RegisterRemoteSiteRequest request = new RegisterRemoteSiteRequest("testurl");
		RegisterRemoteSiteHandler handler = new RegisterRemoteSiteHandler();
		RegisterRemoteSiteResponse response = handler.handleRequest(request, null);
		assertEquals(response.statusCode, 200);
	}

}
