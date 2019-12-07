package http;

public class UploadVideoSegmentResponse 
{
	public final int statusCode;
	public final String response;
	
	public UploadVideoSegmentResponse (int statusCode, String response) {
		this.statusCode = statusCode;
		this.response = response;
	}
	
	public String toString() {
		return "RESPONSE (" + this.response + ")";
	}
}
