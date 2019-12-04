package lambdas;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

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
			Library library = new Library();
			List<VideoSegment> segments = library.searchForSegments(request.character, request.phrase);
			if(segments.size() == 0) {
				return new SearchSegmentResponse(409, "No Video Segments found");
			}
			return new SearchSegmentResponse(segments);
		} catch (LibraryException e) {
			return new SearchSegmentResponse(400, e.getMessage());
		}
	}

}
