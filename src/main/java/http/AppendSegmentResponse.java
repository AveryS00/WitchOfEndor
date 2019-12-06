package http;

public class AppendSegmentResponse {
	public String response;
	public int httpCode;
	
	public AppendSegmentResponse (String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}	
}
