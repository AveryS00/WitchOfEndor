package lambdas;

import java.sql.SQLException;

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
	PlaylistDAO plDAO;
	VideoSegmentDAO vsDAO;

	@Override
	public AppendSegmentResponse handleRequest(AppendSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		try {
			plDAO = new PlaylistDAO();
			vsDAO = new VideoSegmentDAO();
			// Check if in database already
			if (vsDAO.getVideoSegment(req.getVideoLocation()) == null) {
				return addToDatabase(req);
			} else {
				return appendSegment(req);
			}
		} catch (Exception e) {
			return createResponse("Unable to append video segment, " + req.getVideoLocation() + ", to Playlist: " + req.getPlaylistName() + "(" + e.getMessage() + ")", 500);
		} finally {
			try {
				vsDAO.close();
				plDAO.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private AppendSegmentResponse createResponse (String text, int statusCode) {
		return new AppendSegmentResponse(text, statusCode);
	}
	
	private AppendSegmentResponse addToDatabase (AppendSegmentRequest req) {
		if (vsDAO.addVideoSegment(new VideoSegment(req.getVideoLocation(), "", "", false))) {
			return appendSegment(req);
		} else {
			return createResponse("Unable to append video segment, " + req.getVideoLocation() + ", to Playlist: " + req.getPlaylistName(), 400);
		}
	}
	
	private AppendSegmentResponse appendSegment (AppendSegmentRequest req) {
		if (plDAO.appendVideoToPlaylist(req.getPlaylistName(), req.getVideoLocation())) {
			return createResponse("VideoSegment " + req.getVideoLocation() + " Added to Playlist " + req.getPlaylistName(), 200);
		} else {
			return createResponse("Unable to append video segment, " + req.getVideoLocation() + ", to Playlist: " + req.getPlaylistName(), 400);
		}
	}
}
