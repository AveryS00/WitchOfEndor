package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import entity.Playlist;
import http.RemoveSegmentFromPlaylistRequest;
import http.RemoveSegmentFromPlaylistResponse;

/*
 * Due for G2.2
 */
public class RemoveSegmentFromPlaylistHandler 
	implements RequestHandler<RemoveSegmentFromPlaylistRequest, RemoveSegmentFromPlaylistResponse> {

	@Override
	public RemoveSegmentFromPlaylistResponse handleRequest
		(RemoveSegmentFromPlaylistRequest request, Context context) {
		try {
			PlaylistDAO dao = new PlaylistDAO();
			Playlist playlist = dao.getPlaylist(request.playlistName);
			if(playlist.getVideos().size() < request.location || request.location < 1) {
				return new RemoveSegmentFromPlaylistResponse(400, "Invalid position in playlist");
			}
			if(!dao.removeVideoFromPlaylist(request.playlistName, request.location)) {
				return new RemoveSegmentFromPlaylistResponse(200, "Database error");
			}
			dao.close();
			return new RemoveSegmentFromPlaylistResponse(200, "");
		} catch (Exception e) {
			return new RemoveSegmentFromPlaylistResponse(400, e.getMessage());
		}
	}

}
