package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import http.AppendSegmentRequest;
import http.AppendSegmentResponse;

public class AppendSegmentToPlaylistHandler implements RequestHandler<AppendSegmentRequest, AppendSegmentResponse> {

	public LambdaLogger logger;

	@Override
	public AppendSegmentResponse handleRequest(AppendSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		
		AppendSegmentResponse response;
		try {
			PlaylistDAO plDAO = new PlaylistDAO();
			plDAO.appendVideoToPlaylist(req.playlistName, req.videoLocation);
			response = new AppendSegmentResponse("VideoSegment " + req.videoLocation + "added to Playlist " + req.playlistName, 200);
			plDAO.close();
		} catch (Exception e) {
			response = new AppendSegmentResponse("Unable to append video segment, " + req.videoLocation + ", to Playlist: " + req.playlistName + "(" + e.getMessage() + ")", 400);
		}
		return response;
	}
}
