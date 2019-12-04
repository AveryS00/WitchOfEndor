package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import entity.Library;
import exceptions.LibraryException;
import http.RegisterRemoteSiteRequest;
import http.RegisterRemoteSiteResponse;

/*
 * Due for G2.2
 */
public class RegisterRemoteSiteHandler implements 
	RequestHandler<RegisterRemoteSiteRequest, RegisterRemoteSiteResponse> {

	@Override
	public RegisterRemoteSiteResponse handleRequest(RegisterRemoteSiteRequest request, Context context) {
		try {
			Library lib = new Library();
			lib.registerRemoteURL(request.url);
		} catch (LibraryException e) {
			return new RegisterRemoteSiteResponse(400, e.getMessage());
		}
		return new RegisterRemoteSiteResponse(200, "");
	}
	
}
