package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import entity.Library;
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
			library.removeSegmentFromPlaylist(request.playlistName, request.location);
			return new RemoveSegmentFromPlaylistResponse(200, "");
		} catch (LibraryException e) {
			return new RemoveSegmentFromPlaylistResponse(400, e.getMessage());
		}
	}

}
