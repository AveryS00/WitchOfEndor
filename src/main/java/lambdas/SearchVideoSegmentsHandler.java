package lambdas;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.VideoSegmentDAO;
import entity.VideoSegment;
import http.SearchSegmentRequest;
import http.SearchSegmentResponse;

/*
 * Due for G2.2
 */
public class SearchVideoSegmentsHandler 
	implements RequestHandler<SearchSegmentRequest, SearchSegmentResponse>{

	@Override
	public SearchSegmentResponse handleRequest(SearchSegmentRequest request, Context context) {
		if(request.character.equals("")) {
			request.character = null;
		}
		if(request.phrase.equals("")) {
			request.phrase = null;
		}
		if(request.character == null && request.phrase == null) {
			return new SearchSegmentResponse(409, "No Search Terms Specified");
		}
		try {
			VideoSegmentDAO dao = new VideoSegmentDAO();
			List<VideoSegment> segments = dao.listAllVideoSegments();
			List<VideoSegment> matchingSegments = new ArrayList<VideoSegment>();
			for(VideoSegment s : segments) {
				if(request.character != null &&
						s.character.toLowerCase().contains(request.character.toLowerCase())) {
					matchingSegments.add(s);
				} else if(request.phrase != null && 
						s.name.toLowerCase().contains(request.phrase.toLowerCase())) {
					matchingSegments.add(s);
				}
			}
			if(matchingSegments.size() == 0) {
				dao.close();
				return new SearchSegmentResponse(409, "No Video Segments found");
			}
			return new SearchSegmentResponse(matchingSegments);
		} catch (Exception e) {
			return new SearchSegmentResponse(500, e.getMessage());
		}
	}

}
