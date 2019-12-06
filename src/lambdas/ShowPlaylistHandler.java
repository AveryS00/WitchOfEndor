package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import http.CreatePlaylistResponse;
import http.ListPlaylistResponse;

public class ListPlaylistHandler implements RequestHandler<object, ListPlaylistResponse> {
	
	public LambdaLogger logger;

	@Override
	public ListPlaylistResponse handleRequest(ListPlaylistResponse req, Context context) {
		
		ListPlaylistResponse response;
		try {
			PlaylistDAO plDao;
			plDao.close();
			response = new ListPlaylistResponse("List of Video Segments Returned", 200);
		} catch (Exception e) {
			// No 400 error for ListPlaylistResponse
		}
		return response;
	}
}
