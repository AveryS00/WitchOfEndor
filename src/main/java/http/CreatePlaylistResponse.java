package http;

public class CreatePlaylistResponse {
	public String response;
	public int httpCode;

	public CreatePlaylistResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
