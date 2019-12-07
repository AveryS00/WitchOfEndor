package http;

public class UnregisterRemoteSiteResponse {
	
	public int statusCode;
	public String error;
	
	public UnregisterRemoteSiteResponse(int statusCode, String error) {
		this.statusCode = statusCode;
		this.error = error;
	}

}
