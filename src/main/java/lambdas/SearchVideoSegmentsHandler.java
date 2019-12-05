package lambdas;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.VideoSegmentDAO;
import entity.Library;
import entity.VideoSegment;
import exceptions.LibraryException;
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
				if(request.character != null && s.character.contains(request.character)) {
					matchingSegments.add(s);
				} else if(request.phrase != null && s.name.contains(request.phrase)) {
					matchingSegments.add(s);
				}
			}
			if(matchingSegments.size() == 0) {
				return new SearchSegmentResponse(409, "No Video Segments found");
			}
			return new SearchSegmentResponse(matchingSegments);
		} catch (LibraryException e) {
			return new SearchSegmentResponse(400, e.getMessage());
		} catch (Exception e) {
			return new SearchSegmentResponse(500, e.getMessage());
		}
	}

}
