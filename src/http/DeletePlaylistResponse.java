package http;

public class DeletePlaylistResponse {
	public final String response;
	public final int httpCode;

	public DeletePlaylistResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
