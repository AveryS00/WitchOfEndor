package lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.VideoSegmentDAO;
import http.MarkVideoSegmentRequest;
import http.MarkVideoSegmentResponse;

public class FlipMarkVideoSegmentHandler implements RequestHandler<MarkVideoSegmentRequest, MarkVideoSegmentResponse>
{
	public LambdaLogger logger;
	
	@Override
	public MarkVideoSegmentResponse handleRequest(MarkVideoSegmentRequest req, Context context) 
	{
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to mark");
		MarkVideoSegmentResponse response = null;
		logger.log(req.toString());
		
		
				
		try {
			VideoSegmentDAO dao = new VideoSegmentDAO();
			if (dao.flipMark(req.location)) {
				response = new MarkVideoSegmentResponse(req.getLocation(), 200);
			} else {
				response = new MarkVideoSegmentResponse(req.getLocation(), 422, "Unable to change mark.");
			}
			dao.close();
		} catch (Exception e) {
			response = new MarkVideoSegmentResponse(req.getLocation(), 403, "Unable to change mark: " + req.name + "(" + e.getMessage() + ")");
		}
		return response;
	}

}
