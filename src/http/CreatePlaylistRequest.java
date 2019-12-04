package http;

public class CreatePlaylistRequest {
	String playlistName;
	
	public CreatePlaylistRequest (String playlistName) {
		this.playlistName = playlistName;
	}
	
	public CreatePlaylistRequest () {
	}
	
	public String toString () {
		return "CreatePlaylist(" + playlistName + ")";
	}
	
	public String getPlaylistName () {
		return playlistName;
	}
	
	public void setPlaylistName (String playlistName) {
		this.playlistName = playlistName;
	}
}
