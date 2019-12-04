package lambdas;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.RemoteSiteDAO;
import http.ListRemoteSitesResponse;

public class ListRemoteSitesHandler implements RequestHandler<Object,ListRemoteSitesResponse>{

	
	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<String> getRemoteSites() throws Exception {
		logger.log("in getRemoteSites");
		RemoteSiteDAO dao = new RemoteSiteDAO();
		return dao.listAllRemoteSites();
	}
	
	@Override
	public ListRemoteSitesResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all Remote Sites");

		ListRemoteSitesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<String> list = getRemoteSites();
			for (String rs : systemConstants()) {
				if (!list.contains(systemConstants())) {
					list.add(rs);
				}
			}
			response = new ListRemoteSitesResponse(list, 200);
		} catch (Exception e) {
			response = new ListRemoteSitesResponse(403, e.getMessage());
		}
		
		return response;
	}

}
