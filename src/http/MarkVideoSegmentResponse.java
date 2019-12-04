package http;

import java.util.List;
import entity.VideoSegment;

public class MarkVideoSegmentResponse 
{
	public final String location;
	public final int statusCode;
	public final String error;
	
	public MarkVideoSegmentResponse (String location, int statusCode) {
		this.location = location;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public MarkVideoSegmentResponse (String location, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.location = location;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "MarkVideoSegment(" + location + ")";
		} else {
			return "ErrorResult(" + location + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
