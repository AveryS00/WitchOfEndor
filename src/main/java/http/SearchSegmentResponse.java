package http;

import java.util.List;

import entity.VideoSegment;

public class SearchSegmentResponse {
	
	public List<VideoSegment> segments;
	public int statusCode;
	public String error;
	
	public SearchSegmentResponse() {
		
	}
	
	public SearchSegmentResponse(List<VideoSegment> segments) {
		this.segments = segments;
		statusCode = 200;
		error = "";
	}
	
	public SearchSegmentResponse(int statusCode, String error) {
		this.statusCode = statusCode;
		this.error = error;
	}

}
