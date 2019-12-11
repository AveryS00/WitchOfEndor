package lambdas;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import database.VideoSegmentDAO;
import entity.VideoSegment;

public class ListMarkedSegmentsHandler 
	implements RequestHandler<Object, ListMarkedSegmentsHandler.Response> {
	
	public static class Response {
		public List<Segment> segments;
		public int statusCode;
		public String error;
		
		public Response() {
			this.segments = new ArrayList<Segment>();
		}
		
		public Response(List<Segment> segments) {
			this.segments = segments;
			statusCode = 200;
			error = "";
		}
		
		public Response(int statusCode, String error) {
			this.statusCode = statusCode;
			this.error = error;
		}
		
	}

	static class Segment {
		public String url;
		public String character;
		public String text;
		
		public Segment() {
			
		}
		
		public Segment(String url, String character, String text) {
			this.character = character;
			this.url = url;
			this.text = text;
		}
		
		public Segment(VideoSegment rawSegment) {
			this.character = rawSegment.character;
			this.url = rawSegment.location;
			this.text = rawSegment.name;
		}
	}
	
	@Override
	public Response handleRequest(Object input, Context context) {
		try {
			VideoSegmentDAO dao = new VideoSegmentDAO();
			List<VideoSegment> allSegments = dao.listAllVideoSegments();
			List<VideoSegment> markedSegments = filterSegmentList(allSegments);
			List<Segment> translatedSegments = translateSegments(markedSegments);
			Response response = new Response(translatedSegments);
			dao.close();
			return response;
		} catch (Exception e) {
			return new Response(500, e.getMessage());
		}
	}
	
	List<VideoSegment> filterSegmentList(List<VideoSegment> rawList) {
		List<VideoSegment> filteredList = new ArrayList<VideoSegment>();
		
		for(VideoSegment s : rawList) {
			if(s.getIsMarked()) {
				filteredList.add(s);
			}
		}
		
		return filteredList;
	}
	
	List<Segment> translateSegments(List<VideoSegment> originalList) {
		List<Segment> translatedList = new ArrayList<Segment>();
		for(VideoSegment segment : originalList) {
			translatedList.add(new Segment(segment));
		}
		return translatedList;
	}
	
}

