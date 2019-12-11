package lambdas;

import http.ListSegmentsResponse;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import database.VideoSegmentDAO;
import entity.VideoSegment;

/*
 * Needs to be done for G2.1
 */
public class ListSegmentsHandler implements RequestHandler<Object, ListSegmentsResponse> {

	@Override
	public ListSegmentsResponse handleRequest(Object arg0, Context arg1) {
		ListSegmentsResponse response;
		try {
			VideoSegmentDAO dao = new VideoSegmentDAO();
			List<VideoSegment> segments = dao.listAllVideoSegments();
			List<VideoSegment> localSegments = new ArrayList<VideoSegment>();
			for(VideoSegment s : segments) {
				if(s.getIsLocal()) {
					localSegments.add(s);
				}
			}
			response = new ListSegmentsResponse(localSegments, 200, "");
			dao.close();
		} catch (Exception ex) {
			response = new ListSegmentsResponse(null, 400, ex.getMessage());
		}
		return response;
	}

}
