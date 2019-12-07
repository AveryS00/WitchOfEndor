package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import http.DeletePlaylistRequest;
import http.DeletePlaylistResponse;

public class DeletePlaylistHandler implements RequestHandler<DeletePlaylistRequest, DeletePlaylistResponse> {

	LambdaLogger logger;
	
	@Override
	public DeletePlaylistResponse handleRequest(DeletePlaylistRequest req, Context context) {
		
		DeletePlaylistResponse response;
		try {
			PlaylistDAO plDao = new PlaylistDAO();
			if (plDao.deletePlaylist(req.getPlaylistName())) {
				response = new DeletePlaylistResponse("Playlist deleted: " + req.getPlaylistName(), 200);
			} else {
				response = new DeletePlaylistResponse("Playlist does not exist: " + req.getPlaylistName(), 400);
			}
			plDao.close();
		} catch (Exception e) {
			response = new DeletePlaylistResponse("Unable to delete Playlist: " + req.getPlaylistName() + "(" + e.getMessage() + ")", 500);
		}
		return response;
	}
	

}
