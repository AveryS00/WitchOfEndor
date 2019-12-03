package http;

public class CreatePlaylistRequest {
	public final String playlistName;
	
	public CreatePlaylistRequest (String playlistName) {
		this.playlistName = playlistName;
	}
	
	public String toString() {
		return "CreatePlaylist(" + playlistName + ")";
	}
}
