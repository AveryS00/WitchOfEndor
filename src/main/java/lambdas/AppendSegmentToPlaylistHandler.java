package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.PlaylistDAO;
import database.VideoSegmentDAO;
import entity.VideoSegment;
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
			VideoSegmentDAO vsDAO = new VideoSegmentDAO();
			if (vsDAO.getVideoSegment(req.getVideoLocation()) == null) {
				if (!vsDAO.addVideoSegment(new VideoSegment(req.getVideoLocation(), "", "", false, false))) {
					response = new AppendSegmentResponse("Unable to append video segment, " + req.getVideoLocation() + ", to Playlist: " + req.getPlaylistName(), 400);
					return response;
				}
			}
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
