package lambdas;

import http.DeleteVideoSegmentRequest;
import http.DeleteVideoSegmentResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.VideoSegmentDAO;

public class DeleteVideoSegmentHandler implements RequestHandler<DeleteVideoSegmentRequest,DeleteVideoSegmentResponse> 
{
	public LambdaLogger logger;
	
	@Override
	public DeleteVideoSegmentResponse handleRequest(DeleteVideoSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteVideoSegmentResponse response = null;
		logger.log(req.toString());

		VideoSegmentDAO dao;
		
		try {
			dao = new VideoSegmentDAO();
			if (dao.deleteVideoSegment(req.location)) {
				response = new DeleteVideoSegmentResponse(req.getLocation(), 200);
			} else {
				response = new DeleteVideoSegmentResponse(req.getLocation(), 422, "Unable to delete constant.");
			}
		} catch (Exception e) {
			response = new DeleteVideoSegmentResponse(req.getLocation(), 403, "Unable to delete constant: " + req.name + "(" + e.getMessage() + ")");
		}
		return response;
	}
	
}
