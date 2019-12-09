package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.RemoteSiteDAO;
import http.UnregisterRemoteSiteRequest;
import http.UnregisterRemoteSiteResponse;

public class UnregisterRemoteSiteHandler implements RequestHandler<UnregisterRemoteSiteRequest, UnregisterRemoteSiteResponse>{

	@Override
	public UnregisterRemoteSiteResponse handleRequest(UnregisterRemoteSiteRequest request, Context context) {
		
		RemoteSiteDAO dao;
		String url = request.url;
		
		try {
			dao = new RemoteSiteDAO();
			if(dao.removeRemoteSite(url)) {
				return new UnregisterRemoteSiteResponse(200, "Remote site unregistered");
			}
				return new UnregisterRemoteSiteResponse(400, "Remote site could not be unregistered");
		} catch (Exception e) {
			return new UnregisterRemoteSiteResponse(400, e.getMessage());
		}
	}
}
