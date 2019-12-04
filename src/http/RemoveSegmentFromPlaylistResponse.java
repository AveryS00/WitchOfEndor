package http;

public class RemoveSegmentFromPlaylistResponse {
	
	public int statusCode;
	public String error;
	
	public RemoveSegmentFromPlaylistResponse() {
		
	}
	
	public RemoveSegmentFromPlaylistResponse(int statusCode, String error) {
		this.statusCode = statusCode;
		this.error = error;
	}
	
}
