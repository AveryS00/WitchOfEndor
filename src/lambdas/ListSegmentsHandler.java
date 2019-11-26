package lambdas;

import http.ListSegmentsResponse;

import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import entity.Library;
import entity.VideoSegment;
import exceptions.LibraryException;

/*
 * Needs to be done for G2.1
 */
public class ListSegmentsHandler implements RequestHandler<Object, ListSegmentsResponse> {

	@Override
	public ListSegmentsResponse handleRequest(Object arg0, Context arg1) {
		ListSegmentsResponse response;
		try {
			Library library = new Library();
			List<VideoSegment> segments;
			segments = library.getAllSegments();
			response = new ListSegmentsResponse(segments, 200, "");
		} catch (LibraryException ex) {
			response = new ListSegmentsResponse(null, 400, "No Segments Found in Library");
		}
		return response;
	}

}
