package http;

import java.util.List;

import entity.VideoSegment;

public class ListSegmentsResponse {
	
	public List<VideoSegment> videoSegments;
	public int statusCode;
	public String error;
	
	
	public ListSegmentsResponse(List<VideoSegment> videoSegments, int statusCode, String error) {
		this.videoSegments = videoSegments;
		this.statusCode = statusCode;
		this.error = error;
	}

}
