package http;

public class DeletePlaylistRequest {
	public final String playlistName;
	
	public DeletePlaylistRequest (String playlistName) {
		this.playlistName = playlistName;
	}
	
	public String toString () {
		return "DeletePlaylist(" + playlistName + ")";
	}
}
