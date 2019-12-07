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
			if (plDAO.appendVideoToPlaylist(req.getPlaylistName(), req.getVideoLocation())) {
				response = new AppendSegmentResponse("VideoSegment " + req.getVideoLocation() + " Added to Playlist " + req.getPlaylistName(), 200);
			} else {
				response = new AppendSegmentResponse("Unable to append video segment, " + req.getVideoLocation() + ", to Playlist: " + req.getPlaylistName(), 400);
			}
			plDAO.close();
		} catch (Exception e) {
			response = new AppendSegmentResponse("Unable to append video segment, " + req.getVideoLocation() + ", to Playlist: " + req.getPlaylistName() + "(" + e.getMessage() + ")", 500);
		}
		return response;
	}
}
