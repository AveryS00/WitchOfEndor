package http;

public class RegisterRemoteSiteResponse {
	
	public int statusCode;
	public String error;
	
	public RegisterRemoteSiteResponse(int statusCode, String error) {
		this.statusCode = statusCode;
		this.error = error;
	}

}
