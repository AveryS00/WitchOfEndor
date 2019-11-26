package lambdas;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import entity.Library;
import entity.Playlist;
import http.ListPlaylistResponse;

/*
 * Needs to be done for G2.1
 */
public class ListPlaylistsHandler implements RequestHandler<Object,ListPlaylistResponse> {

	public LambdaLogger logger;
	
	@Override
	public ListPlaylistResponse handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all Playlists");
		
		ListPlaylistResponse response;
		try {
			logger.log("Getting Playlists from RDS");
			Library library = new Library();
			List<Playlist> list = library.getPlaylists();
			
			response = new ListPlaylistResponse(list, 200);
		} catch (Exception e) {
			response = new ListPlaylistResponse(403, e.getMessage());
		}
		
		return response;
	}
	
}
