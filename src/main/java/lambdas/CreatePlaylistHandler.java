package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import http.CreatePlaylistRequest;
import http.CreatePlaylistResponse;

public class CreatePlaylistHandler implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResponse> {

	public LambdaLogger logger;
	
	@Override
	public CreatePlaylistResponse handleRequest(CreatePlaylistRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		
		CreatePlaylistResponse response;
		try {
			PlaylistDAO plDao = new PlaylistDAO();
			if (plDao.createPlaylist(req.getPlaylistName())) {
				response = new CreatePlaylistResponse("Playlist created", 200);
			} else {
				response = new CreatePlaylistResponse("Playlist already exists: " + req.getPlaylistName(), 400);
			}
			plDao.close();
		} catch (Exception e) {
			response = new CreatePlaylistResponse("Unable to create playlist(" + e.getMessage() + ")", 500);
		}
		return response;
	}

}
