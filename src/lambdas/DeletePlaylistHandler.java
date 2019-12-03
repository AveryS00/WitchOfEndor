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
			plDao.deletePlaylist(req.playlistName);
			response = new DeletePlaylistResponse("Playlist deleted", 200);
			plDao.close();
		} catch (Exception e) {
			response = new DeletePlaylistResponse("Unable to delete Playlist: " + req.playlistName + "(" + e.getMessage() + ")", 400);
		}
		return response;
	}
	

}
