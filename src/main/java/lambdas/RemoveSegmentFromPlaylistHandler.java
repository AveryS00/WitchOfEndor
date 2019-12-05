package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import entity.Library;
import entity.Playlist;
import exceptions.LibraryException;
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
			Library library = new Library();
			Playlist playlist = library.getPlaylist(request.playlistName);
			if(playlist.getVideos().size() < request.location || request.location < 1) {
				return new RemoveSegmentFromPlaylistResponse(400, "Invalid position in playlist");
			}
			try {
				PlaylistDAO dao = new PlaylistDAO();
				if(!dao.removeVideoFromPlaylist(request.playlistName, request.location)) {
					return new RemoveSegmentFromPlaylistResponse(500, "Failed to remove segment from playlist");
				}
			} catch (Exception e) {
				return new RemoveSegmentFromPlaylistResponse(500, e.getMessage());
			}
			
			return new RemoveSegmentFromPlaylistResponse(200, "");
		} catch (LibraryException e) {
			return new RemoveSegmentFromPlaylistResponse(400, e.getMessage());
		}
	}

}
