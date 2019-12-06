package http;

public class DeletePlaylistResponse {
	public String response;
	public int httpCode;

	public DeletePlaylistResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
