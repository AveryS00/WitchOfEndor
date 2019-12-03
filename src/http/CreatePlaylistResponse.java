package http;

public class CreatePlaylistResponse {
	public final String response;
	public final int httpCode;

	public CreatePlaylistResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
