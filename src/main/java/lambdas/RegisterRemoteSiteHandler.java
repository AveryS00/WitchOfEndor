package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import database.RemoteSiteDAO;
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
			RemoteSiteDAO dao = new RemoteSiteDAO();
			String url = request.url;
			if(dao.listAllRemoteSites().contains(url)) {
				return new RegisterRemoteSiteResponse(409, "Remote Site Already Registered");
			}
			dao.addRemoteSite(url);
			return new RegisterRemoteSiteResponse(200, "");
			
		} catch (Exception e) {
			return new RegisterRemoteSiteResponse(400, e.getMessage());
		}
	}
	
}
